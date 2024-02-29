package jpcompany.smartwire2.facade;

import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.MachineService;
import jpcompany.smartwire2.service.ProcessService;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import jpcompany.smartwire2.service.vo.LogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class LogFacadeDB implements LogFacade {

    private final ProcessService processService;
    private final LogService logService;
    private final MachineService machineService;
    private final LogRepository logRepository;

    @Override
    public void handleRealTimeLog(LogSaveRequest logSaveRequest, Long memberId) {
        ProcessSaveDto processSaveDto = logSaveRequest.toProcessSaveDto();
        LogSaveDto logSaveDto = logSaveRequest.toLogSaveDto();

        if (!processSaveDto.isEmpty()) {
            processService.save(processSaveDto);
        }

        logService.save(logSaveDto, memberId);

        if (logSaveDto.isReset()) {
            processService.finishProcess(processSaveDto.getMachineId(), logSaveDto.getLogDateTime());
        }

        // TODO - 웹소켓 처리
        // TODO - FCM 푸시 전송
    }

    @Override
    public List<LogVo> getRecentLogs(Long memberId) {
        List<Long> machineIds = machineService.findMachines(memberId).getMachineIds();
        List<LogVo> logVos = new ArrayList<>();
        for (Long machineId : machineIds) {
            logVos.add(logRepository.findRecentLogByMachineId(machineId));
        }
        return logVos;
    }
}
