package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.jdbctemplate.constant.LogConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MachineConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.ProcessConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
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
public class LogRepositoryJdbcTemplate {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public LogRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(LogConstantDB.TABLE_NAME)
                .usingGeneratedKeyColumns(LogConstantDB.ID);
    }

    public Long save(LogSaveTransfer logSaveTransfer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(logSaveTransfer);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }

    public Optional<Log> findRecentLogByMachineId(Long machineId) {
        String sql =
                """
                SELECT *
                FROM logs
                WHERE id = (
                    SELECT MAX(id)
                    FROM logs
                    WHERE machine_id = :machine_id
                )
                """;
        try {
            MapSqlParameterSource param = new MapSqlParameterSource()
                    .addValue(LogConstantDB.MACHINE_ID, machineId);
            Log log = template.queryForObject(sql, param, logRowMapper());
            return Optional.ofNullable(log);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Log> logRowMapper() {
        return (rs, rowNum) ->
                Log.builder()
                        .id(rs.getLong(LogConstantDB.ID))
                        .logName(rs.getString(LogConstantDB.LOG_NAME))
                        .logDateTime(rs.getObject(LogConstantDB.LOG_DATE_TIME, LocalDateTime.class))
                        .build();
    }
}
