package jpcompany.smartwire2.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.repository.MachineRepository;
import jpcompany.smartwire2.repository.filesystem.LogCacheFile;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import jpcompany.smartwire2.service.vo.LogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static jpcompany.smartwire2.common.error.ErrorCode.INVALID_REQUEST;
import static jpcompany.smartwire2.common.error.ErrorCode.JSON_PARSE_FAIL;
import static jpcompany.smartwire2.repository.filesystem.LogCacheFile.CACHE_PATH;

@Component
@RequiredArgsConstructor
public class LogCache {
    private final MachineRepository machineRepository;
    private final LogRepository logRepository;
    private final LogCacheFile logCacheFile;
    private final ObjectMapper objectMapper;
    private final Map<Long, ProcessUnit> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws JsonProcessingException {
        File[] files = new File(CACHE_PATH).listFiles();
        if (files == null) return;
        List<String> filePaths = Arrays.stream(files).map(File::getPath).toList();

        for (String filePath : filePaths) {
            List<String> lines = logCacheFile.read(filePath);
            ProcessUnit processUnit = objectMapper.readValue(lines.remove(0), ProcessUnit.class);
            cache.put(processUnit.getMachineId(), processUnit);

            List<LogSaveDto> logSaveDtos = lines.stream()
                    .map(this::parseFrom)
                    .toList();
            processUnit.save(logSaveDtos);
        }
    }

    private LogSaveDto parseFrom(String content) {
        try {
            return objectMapper.readValue(content, LogSaveDto.class);
        } catch (JsonProcessingException e) {
            throw new CustomException(JSON_PARSE_FAIL);
        }
    }

    public void save(LogSaveRequest logSaveRequest, Long memberId) {
        try {
            ProcessUnit processUnit = cache.get(logSaveRequest.getMachineId());
            if (processUnit == null) {
                processUnit = init(logSaveRequest.toProcessSaveDto(), memberId);
            }
            LogSaveDto logSaveDto = logSaveRequest.toLogSaveDto();
            processUnit.save(logSaveDto, memberId);

            String logSaveDtoJson = objectMapper.writeValueAsString(logSaveDto);
            logCacheFile.write(CACHE_PATH + processUnit.getMachineId() + ".txt", "\n" + logSaveDtoJson, true);
        } catch (JsonProcessingException e) {
            throw new CustomException(JSON_PARSE_FAIL, e);
        }
    }

    private ProcessUnit init(ProcessSaveDto processSaveDto, Long memberId) throws JsonProcessingException {
        validateRequest(memberId);
        ProcessUnit processUnit = ProcessUnit.create(processSaveDto, memberId);
        cache.put(processSaveDto.getMachineId(), processUnit);

        String processUnitJson = objectMapper.writeValueAsString(processUnit);
        logCacheFile.write(CACHE_PATH + processUnit.getMachineId() + ".txt", processUnitJson);
        return processUnit;
    }

    private void validateRequest(Long memberId) {
        List<Machine> machines = machineRepository.findAllByMemberId(memberId);
        if (machines.stream().noneMatch(machine -> Objects.equals(machine.getMemberId(), memberId))) {
            throw new CustomException(INVALID_REQUEST);
        }
    }

    public ProcessUnit flush(Long machineId, Long memberId) {
        ProcessUnit processUnit = cache.get(machineId);
        if (processUnit == null) {
            return null;
        }
        processUnit.validateRequest(memberId);
        logCacheFile.delete(CACHE_PATH + machineId + ".txt");
        return cache.remove(machineId);
    }

    public List<LogVo> findRecentLogVosBy(List<Long> machineIds) {
        List<LogVo> logVos = new ArrayList<>();
        for (Long machineId : machineIds) {
            LogVo logVo = findRecentLogVoBy(machineId)
                    .orElseGet(() -> logRepository.findRecentLogByMachineId(machineId));
            logVos.add(logVo);
        }
        return logVos;
    }

    private Optional<LogVo> findRecentLogVoBy(Long machineId) {
        ProcessUnit processUnit = cache.get(machineId);
        if (processUnit == null) return Optional.empty();

        List<LogSaveDto> logSaveDtos = processUnit.getLogSaveDtos();
        LogSaveDto logSaveDto = logSaveDtos.get(logSaveDtos.size() - 1);

        return Optional.of(
                LogVo.create(
                        processUnit.getFileName(),
                        processUnit.getThickness(),
                        logSaveDto.getLogName(),
                        logSaveDto.getLogDateTime(),
                        processUnit.getMachineId()
                )
        );
    }
}
