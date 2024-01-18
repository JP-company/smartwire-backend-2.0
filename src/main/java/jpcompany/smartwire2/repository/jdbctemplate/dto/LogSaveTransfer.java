package jpcompany.smartwire2.repository.jdbctemplate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LogSaveTransfer {
    private String logName;
    private LocalDateTime logDateTime;
    private Long machineId;
}
