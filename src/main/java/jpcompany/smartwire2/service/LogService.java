package jpcompany.smartwire2.service;

import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogService {

    private final LogRepository logRepository;

    public void saveLog(LogSaveDto logSaveDto) {
        logRepository.save(logSaveDto);
    }
}
