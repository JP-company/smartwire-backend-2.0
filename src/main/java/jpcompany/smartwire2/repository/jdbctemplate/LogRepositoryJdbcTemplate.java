package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.repository.jdbctemplate.constant.LogConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LogRepositoryJdbcTemplate {

    private final NamedParameterJdbcTemplate template;


    public Long save(LogSaveTransfer logSaveTransfer) {
        String sql =
                """
                INSERT INTO logs 
                VALUES (DEFAULT, :log_name, :log_date_time, :machine_id, 
                    (
                        SELECT id
                        FROM processes
                        WHERE id = (
                            SELECT MAX(id)
                            FROM processes
                            WHERE machine_id = :machine_id
                        ) AND finished_date_time IS NULL
                    )
                );
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue(LogConstantDB.LOG_NAME, logSaveTransfer.getLogName())
                .addValue(LogConstantDB.LOG_DATE_TIME, logSaveTransfer.getLogDateTime())
                .addValue(LogConstantDB.MACHINE_ID, logSaveTransfer.getMachineId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
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
                        .processId(rs.getLong(LogConstantDB.PROCESS_ID))
                        .build();
    }
}
