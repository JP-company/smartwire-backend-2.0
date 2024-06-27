package jpcompany.smartwire2.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProcessSaveDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fileName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float thickness;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime startedDateTime;

    @JsonIgnore
    private LocalDateTime finishedDateTime;

    private Long machineId;

    public static ProcessSaveDto create(Long machineId) {
        return ProcessSaveDto.builder()
                .machineId(machineId)
                .build();
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
                .startedDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public Process toProcess(Machine machine) {
        return Process.create(
                fileName,
                thickness,
                startedDateTime,
                machine
        );
    }

    public void finishedAt(LocalDateTime finishedDateTime) {
        this.finishedDateTime = finishedDateTime;
    }
    @JsonIgnore
    public boolean isEmpty() {
        return fileName == null;
    }
}
