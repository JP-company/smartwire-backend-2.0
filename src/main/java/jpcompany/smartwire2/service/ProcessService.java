package jpcompany.smartwire2.service;

import jpcompany.smartwire2.repository.ProcessRepository;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcessService {

    private final ProcessRepository processRepository;

    public Long save(ProcessSaveDto processSaveDto) {
        if (processSaveDto.isEmpty()) return null;
        return processRepository.save(processSaveDto);
    }
}
