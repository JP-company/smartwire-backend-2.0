package jpcompany.smartwire2.service;

import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LogService {

    private final LogRepository logRepository;

    public void save(List<LogSaveDto> logSaveDtos, Long machineId, Long processId) {
        logRepository.save(logSaveDtos, machineId, processId);
    }
}
