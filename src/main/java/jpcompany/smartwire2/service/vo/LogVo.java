package jpcompany.smartwire2.service.vo;

import jpcompany.smartwire2.domain.Log;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LogVo {
    private final String fileName;
    private final Float thickness;
    private final String logName;
    private final LocalDateTime logDateTime;
    private final Long machineId;

    public static LogVo create(Log log) {
        return create(log.getFileName(), log.getThickness(), log.getLogName(), log.getLogDateTime(), log.getMachineId());
    }

    public static LogVo create(String fileName, Float thickness, String logName, LocalDateTime logDateTime, Long machineId) {
        return LogVo.builder()
                .fileName(fileName)
                .thickness(thickness)
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public static LogVo createEmpty(Long machineId) {
        return LogVo.builder()
                .machineId(machineId)
                .build();
    }
}
