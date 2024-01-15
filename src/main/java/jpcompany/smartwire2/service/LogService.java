package jpcompany.smartwire2.service;

import jpcompany.smartwire2.repository.jdbctemplate.LogRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import jpcompany.smartwire2.service.dto.LogSaveCommand;
import jpcompany.smartwire2.service.dto.ProcessSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepositoryJdbcTemplate logRepositoryJdbcTemplate;
    private final ProcessService processService;


    public void saveLog(LogSaveCommand logSaveCommand, ProcessSaveCommand processSaveCommand) {
        if (processSaveCommand.isNotEmpty()) {
            processService.saveProcess(processSaveCommand);
        }

        LogSaveTransfer logSaveTransfer = logSaveCommand.toLogSaveTransfer();
        logRepositoryJdbcTemplate.save(logSaveTransfer);

        if (logSaveCommand.isFinished()) {
            processService.finishProcess(processSaveCommand);
        }
    }
}
