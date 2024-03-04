package jpcompany.smartwire2.facade;

import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.service.vo.LogVo;

import java.util.List;

public interface LogFacade {
    void handleRealTimeLog(LogSaveRequest logSaveRequest, Long memberId);
    List<LogVo> getRecentLogs(Long memberId);
}
