package jpcompany.smartwire2.service.dto;


import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProcessSaveDto {
    private String fileName;
    private Float thickness;
    private LocalDateTime logDateTime;
    private Long machineId;

    public boolean isNotEmpty() {
        return fileName != null;
    }

    public static ProcessSaveDto create(
            String fileName,
            Float thickness,
            LocalDateTime logDateTime,
            Long machineId
    ) {
        return ProcessSaveDto.builder()
                .fileName(fileName)
                .thickness(thickness)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public Process toProcess(Machine machine) {
        return Process.create(
                fileName,
                thickness,
                logDateTime,
                machine
        );
    }
}
