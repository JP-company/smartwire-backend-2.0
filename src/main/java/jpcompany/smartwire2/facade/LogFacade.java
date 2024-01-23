package jpcompany.smartwire2.facade;

import jpcompany.smartwire2.facade.dto.LogReceiveDto;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.ProcessService;
import jpcompany.smartwire2.service.dto.LogSaveCommand;
import jpcompany.smartwire2.service.dto.ProcessSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFacade {

    private final LogService logService;
    private final ProcessService processService;

    public void handleRealTimeLog(LogReceiveDto logReceiveDto) {
        LogSaveCommand logSaveCommand = logReceiveDto.toLogSaveCommand();
        ProcessSaveCommand processSaveCommand = logReceiveDto.toProcessSaveCommand();

        if (processSaveCommand.isNotEmpty()) {
            processService.saveProcess(processSaveCommand);
        }
        logService.saveLog(logSaveCommand);
        if (logSaveCommand.isFinished()) {
            processService.finishProcess(processSaveCommand);
        }

        // TODO - 웹소켓 처리
        // TODO - FCM 푸시 전송
    }
}
