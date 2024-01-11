package jpcompany.smartwire2.service;

import jpcompany.smartwire2.repository.jdbctemplate.ProcessRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import jpcompany.smartwire2.service.dto.ProcessSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepositoryJdbcTemplate processRepositoryJdbcTemplate;

    public void saveProcess(ProcessSaveCommand processSaveCommand) {
        ProcessSaveTransfer processSaveTransfer = processSaveCommand.toProcessSaveTransfer();
        processRepositoryJdbcTemplate.save(processSaveTransfer);
    }

    public void finishProcess(ProcessSaveCommand processSaveCommand) {
        processRepositoryJdbcTemplate.updateFinishedDateTime(
                processSaveCommand.getMachineId(),
                processSaveCommand.getLogDateTime()
        );
    }
}
