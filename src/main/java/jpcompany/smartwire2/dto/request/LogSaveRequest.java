package jpcompany.smartwire2.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LogSaveRequest {
    private String fileName;
    private Float thickness;

    @NotBlank
    private String logName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull
    private LocalDateTime logDateTime;

    private Long machineId;

    public LogSaveDto toLogSaveDto() {
        return LogSaveDto.create(
                logName,
                logDateTime,
                machineId
        );
    }

    public ProcessSaveDto toProcessSaveDto() {
        return ProcessSaveDto.create(
                fileName,
                thickness,
                logDateTime,
                machineId
        );
    }
}
