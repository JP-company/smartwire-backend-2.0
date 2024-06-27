package jpcompany.smartwire2.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public static LogSaveDto create(
            String logName,
            LocalDateTime logDateTime
    ) {
        return LogSaveDto.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .build();
    }

    @JsonIgnore
    public boolean isReset() {
        return logName.equals("reset_리셋");
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
