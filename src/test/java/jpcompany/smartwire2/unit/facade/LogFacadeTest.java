package jpcompany.smartwire2.unit.facade;

import jpcompany.smartwire2.facade.LogFacade;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.ProcessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LogFacadeTest {
    @InjectMocks
    private LogFacade logFacade;
    @Mock
    private ProcessService processService;
    @Mock
    private LogService logService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void beforeEach() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

//    @Test
//    @DisplayName("작업 시작 로그 입력 시 작업 저장 메서드 실행")
//    void handleRealTimeLog() {
//        LogReceiveDto logDto = LogReceiveDto.builder()
//                .fileName("123.NC")
//                .thickness(30f)
//                .logName("start_작업시작")
//                .logDateTime(LocalDateTime.now())
//                .machineId(1L)
//                .build();
//
//        Mockito.doNothing().when(logService).saveLog(any(LogSaveCommand.class));
//        Mockito.doNothing().when(processService).saveProcess(any(ProcessSaveCommand.class));
//        Mockito.doNothing().when(processService).finishProcess(any(ProcessSaveCommand.class));
//
//        logFacade.handleRealTimeLog(logDto);
//
//        Mockito.verify(logService, Mockito.times(1)).saveLog(any(LogSaveCommand.class));
//        Mockito.verify(processService, Mockito.times(1)).saveProcess(any(ProcessSaveCommand.class));
//        Mockito.verify(processService, Mockito.times(0)).finishProcess(any(ProcessSaveCommand.class));
//    }
}