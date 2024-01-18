package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.MachineStatus;
import jpcompany.smartwire2.repository.jdbctemplate.constant.LogConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.ProcessConstantDB;
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

    public Optional<MachineStatus> findMachineStatusByMachineId(Long machineId) {
        String sql =
                """
                WITH recent_log AS (
                    SELECT machine_id, log_name, log_date_time, process_id
                    FROM logs
                    WHERE id = (
                        SELECT max(id)
                        FROM logs
                        WHERE machine_id = :machine_id
                    )
                ),
                recent_process AS (
                    SELECT file_name
                    FROM processes
                    WHERE id IN (
                        SELECT process_id
                        FROM recent_log
                        WHERE process_id IS NOT NULL
                    )
                    AND finished_date_time IS NULL
                )
                SELECT machine_id, log_name, log_date_time, file_name
                FROM recent_log LEFT JOIN recent_process
                ON TRUE;
                """;
        try {
            MapSqlParameterSource param = new MapSqlParameterSource()
                    .addValue(LogConstantDB.MACHINE_ID, machineId);
            MachineStatus machineStatus = template.queryForObject(sql, param, machineStatusRowMapper());
            return Optional.ofNullable(machineStatus);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<MachineStatus> machineStatusRowMapper() {
        return (rs, rowNum) ->
            MachineStatus.builder()
                    .machineId(rs.getLong(LogConstantDB.MACHINE_ID))
                    .logName(rs.getString(LogConstantDB.LOG_NAME))
                    .logDateTime(rs.getObject(LogConstantDB.LOG_DATE_TIME, LocalDateTime.class))
                    .fileName(rs.getString(ProcessConstantDB.FILE_NAME))
                    .build();
    }
}
