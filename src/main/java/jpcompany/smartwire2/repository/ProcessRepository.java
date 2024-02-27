package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.service.dto.ProcessSaveDto;

public interface ProcessRepository {
    Long save(ProcessSaveDto processSaveDto);
}
