package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.service.dto.ProcessSaveDto;

import java.time.LocalDateTime;

public interface ProcessRepository {
    Long save(ProcessSaveDto processSaveDto);

    void updateFinishedDateTime(Long machineId, LocalDateTime logDateTime);
}
