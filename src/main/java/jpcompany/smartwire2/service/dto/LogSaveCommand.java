package jpcompany.smartwire2.service.dto;

import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
public class LogSaveCommand {
    private String logName;
    private LocalDateTime logDateTime;
    private Long machineId;

    public LogSaveTransfer toLogSaveTransfer() {
        return LogSaveTransfer.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public boolean isFinished() {
        return logName.equals("stop_reset");
    }
}
