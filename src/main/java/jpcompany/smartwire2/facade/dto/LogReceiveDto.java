package jpcompany.smartwire2.facade.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpcompany.smartwire2.service.dto.LogSaveCommand;
import jpcompany.smartwire2.service.dto.ProcessSaveCommand;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LogReceiveDto {
    private String fileName;
    private Float thickness;

    @NotBlank
    private String logName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull
    private LocalDateTime logDateTime;
    private Long machineId;

    public LogSaveCommand toLogSaveCommand() {
        return LogSaveCommand.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }

    public ProcessSaveCommand toProcessSaveCommand() {
        return ProcessSaveCommand.builder()
                .fileName(fileName)
                .thickness(thickness)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .build();
    }
}
