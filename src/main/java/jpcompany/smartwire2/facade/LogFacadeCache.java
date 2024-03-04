package jpcompany.smartwire2.facade;

import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.MachineService;
import jpcompany.smartwire2.service.ProcessService;
import jpcompany.smartwire2.service.cache.LogCache;
import jpcompany.smartwire2.service.cache.ProcessUnit;
import jpcompany.smartwire2.service.vo.LogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Component
@RequiredArgsConstructor
@Transactional
public class LogFacadeCache implements LogFacade {
    private final ProcessService processService;
    private final LogService logService;
    private final LogCache logCache;
    private final MachineService machineService;

    public void handleRealTimeLog(LogSaveRequest logSaveRequest, Long memberId) {
        Long machineId = logSaveRequest.getMachineId();

        if (logSaveRequest.isStart()) {
            flushCache(machineId, memberId);
        }

        logCache.save(logSaveRequest, memberId);

        if (logSaveRequest.isReset()) {
            flushCache(machineId, memberId);
        }

        // TODO - 웹소켓 처리
        // TODO - FCM 푸시 전송
    }

    public List<LogVo> getRecentLogs(Long memberId) {
        Machines machines = machineService.findMachines(memberId);
        return logCache.findRecentLogVosBy(machines.getMachineIds());
    }

    private void flushCache(Long machineId, Long memberId) {
        ProcessUnit processUnit = logCache.flush(machineId, memberId);
        if (processUnit == null) {
            return;
        }
        Long processId = processService.save(processUnit.getProcessSaveDto());
        logService.save(processUnit.getLogSaveDtos(), processUnit.getMachineId(), processId);
    }
}
