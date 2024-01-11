package jpcompany.smartwire2.service.dto;


import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
public class ProcessSaveCommand {
    private String fileName;
    private Float thickness;
    private LocalDateTime logDateTime;
    private Long machineId;

    public boolean isNotEmpty() {
        return fileName != null;
    }

    public ProcessSaveTransfer toProcessSaveTransfer() {
        return ProcessSaveTransfer.builder()
                .fileName(fileName)
                .thickness(thickness)
                .startedDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }
}
