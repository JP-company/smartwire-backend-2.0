package jpcompany.smartwire2.service.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ProcessUnit {

    private final Long memberId;
    private final ProcessSaveDto processSaveDto;
    private final List<LogSaveDto> logs = new ArrayList<>();

    public static ProcessUnit create(ProcessSaveDto processSaveDto, Long memberId) {
        return new ProcessUnit(processSaveDto, memberId);
    }

    private ProcessUnit(ProcessSaveDto processSaveDto, Long memberId) {
        this.memberId = memberId;
        this.processSaveDto = processSaveDto;
    }

    public void save(List<LogSaveDto> logSaveDtos) {
        logs.addAll(logSaveDtos);
    }

    public void save(LogSaveDto logSaveDto, Long memberId) {
        validateRequest(memberId);
        logs.add(logSaveDto);
        if (logSaveDto.isReset()) {
            processSaveDto.finishedAt(logSaveDto.getLogDateTime());
        }
    }

    public void validateRequest(Long memberId) {
        if (!Objects.equals(this.memberId, memberId)) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

    @JsonIgnore
    public String getFileName() {
        return processSaveDto.getFileName();
    }
    @JsonIgnore
    public Float getThickness() {
        return processSaveDto.getThickness();
    }
    @JsonIgnore
    public Long getMachineId() {
        return processSaveDto.getMachineId();
    }
    @JsonIgnore
    public List<LogSaveDto> getLogSaveDtos() {
        return this.logs;
    }
}
