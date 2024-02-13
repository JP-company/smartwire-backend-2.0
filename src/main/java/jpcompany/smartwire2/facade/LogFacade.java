package jpcompany.smartwire2.facade;

import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.ProcessService;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class LogFacade {

    private final LogService logService;
    private final ProcessService processService;

    public void handleRealTimeLog(LogSaveRequest logSaveRequest) {
        ProcessSaveDto processSaveDto = logSaveRequest.toProcessSaveDto();
        LogSaveDto logSaveDto = logSaveRequest.toLogSaveDto();

        if (processSaveDto.isNotEmpty()) {
            processService.saveProcess(processSaveDto);
        }
        logService.saveLog(logSaveDto);
        if (logSaveDto.isFinished()) {
            processService.finishProcess(processSaveDto.getMachineId(), processSaveDto.getLogDateTime());
        }

        // TODO - 웹소켓 처리
        // TODO - FCM 푸시 전송
    }
}
