package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.jdbctemplate.constant.ProcessConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ProcessRepositoryJdbcTemplate {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public ProcessRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(ProcessConstantDB.TABLE_NAME)
                .usingGeneratedKeyColumns(ProcessConstantDB.ID);
    }

    public Long save(ProcessSaveTransfer processSaveTransfer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(processSaveTransfer);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }

    public void updateFinishedDateTime(Long machineId, LocalDateTime finishedDateTime) {
        String sql =
                """
                UPDATE processes
                SET finished_date_time = :finished_date_time
                WHERE id = (
                    SELECT max(id)
                    FROM processes
                    WHERE machine_id = :machine_id
                )
                AND finished_date_time IS NULL
                """;

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue(ProcessConstantDB.MACHINE_ID, machineId)
                .addValue(ProcessConstantDB.FINISHED_DATE_TIME, finishedDateTime);
        int update = template.update(sql, param);
        if (update < 1) {
            throw new IllegalArgumentException("작업 종료 시간 설정 실패");
        }
    }

    public Optional<Process> findRecentProcessByMachineId(Long machineId) {
        String sql =
                """
                SELECT *
                FROM processes
                WHERE id = (
                    SELECT max(id)
                    FROM processes
                    WHERE machine_id = :machine_id
                )
                """;

        try {
            MapSqlParameterSource param = new MapSqlParameterSource()
                    .addValue(ProcessConstantDB.MACHINE_ID, machineId);
            Process process = template.queryForObject(sql, param, processRowMapper());
            return Optional.ofNullable(process);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Process> processRowMapper() {
        return (rs, rowNum) ->
                Process.builder()
                        .id(rs.getLong(ProcessConstantDB.ID))
                        .fileName(rs.getString(ProcessConstantDB.FILE_NAME))
                        .thickness(rs.getFloat(ProcessConstantDB.THICKNESS))
                        .startedDateTime(rs.getObject(ProcessConstantDB.STARTED_DATE_TIME, LocalDateTime.class))
                        .finishedDateTime(rs.getObject(ProcessConstantDB.FINISHED_DATE_TIME, LocalDateTime.class))
                        .build();
    }
}
