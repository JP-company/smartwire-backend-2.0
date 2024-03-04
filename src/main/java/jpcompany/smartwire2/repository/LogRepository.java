package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.vo.LogVo;

import java.util.List;

public interface LogRepository {
    Long save(LogSaveDto logSaveDto, Long machineId, Long processId);
    Long save(LogSaveDto logSaveDto, Long machineId);
    void save(List<LogSaveDto> logSaveDtos, Long machineId, Long processId);
    LogVo findRecentLogByMachineId(Long machineId);
}
