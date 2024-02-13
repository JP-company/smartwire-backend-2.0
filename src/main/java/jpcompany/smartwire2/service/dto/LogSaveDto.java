package jpcompany.smartwire2.service.dto;

import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LogSaveDto {
    private String logName;
    private LocalDateTime logDateTime;
    private Long machineId;

    public static LogSaveDto create(
            String logName,
            LocalDateTime logDateTime,
            Long machineId
    ) {
        return LogSaveDto.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public boolean isFinished() {
        return logName.equals("stop_reset");
    }

    public Log toLog(Machine machine, Process process) {
        return Log.create(
                logName,
                logDateTime,
                machine,
                process
        );
    }
}
