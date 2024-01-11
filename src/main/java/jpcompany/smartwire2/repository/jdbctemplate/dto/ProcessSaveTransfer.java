package jpcompany.smartwire2.repository.jdbctemplate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProcessSaveTransfer {
    private Long id;
    private String fileName;
    private Float thickness;
    private LocalDateTime startedDateTime;
    private Long machineId;
}
