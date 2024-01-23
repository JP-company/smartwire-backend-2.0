package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.MachineStatus;
import jpcompany.smartwire2.repository.jdbctemplate.LogRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import jpcompany.smartwire2.service.dto.LogSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepositoryJdbcTemplate logRepositoryJdbcTemplate;

    public void saveLog(LogSaveCommand logSaveCommand) {
        LogSaveTransfer logSaveTransfer = logSaveCommand.toLogSaveTransfer();
        logRepositoryJdbcTemplate.save(logSaveTransfer);
    }

    public List<MachineStatus> getRecentMachinesStatus(List<Long> machineIds) {
        return machineIds.stream()
                .map(machineId ->
                        logRepositoryJdbcTemplate.findMachineStatusByMachineId(machineId)
                                .orElse(null)
                ).toList();
    }
}
