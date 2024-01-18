package jpcompany.smartwire2.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MachineStatus {
    private Long machineId;
    private String logName;
    private LocalDateTime logDateTime;
    private String fileName;
}
