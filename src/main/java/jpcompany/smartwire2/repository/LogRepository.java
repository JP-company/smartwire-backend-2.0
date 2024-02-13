package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.service.dto.LogSaveDto;

public interface LogRepository {
    Long save(LogSaveDto logSaveDto);
}
