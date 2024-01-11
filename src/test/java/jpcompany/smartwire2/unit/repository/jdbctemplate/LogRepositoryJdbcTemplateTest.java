package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.repository.jdbctemplate.LogRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.ProcessRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LogRepositoryJdbcTemplateTest {

    @Autowired
    private LogRepositoryJdbcTemplate logRepositoryJdbcTemplate;
    @Autowired
    private ProcessRepositoryJdbcTemplate processRepositoryJdbcTemplate;

    @BeforeEach
    void beforeEach() {
        String fileName = "123.NC";
        Float thickness = 27.5f;
        Long machineId = 1L;
        LocalDateTime startedDateTime = LocalDateTime.now().minusHours(5);

        ProcessSaveTransfer processSaveTransfer = ProcessSaveTransfer.builder()
                .fileName(fileName)
                .thickness(thickness)
                .machineId(machineId)
                .startedDateTime(startedDateTime)
                .build();
        processRepositoryJdbcTemplate.save(processSaveTransfer);
    }

    @Test
    @DisplayName("새 로그 저장, 최신 로그 조회 후 결과 확인")
    void save() {
        // given
        String logName = "작업 재시작";
        LocalDateTime logDateTime = LocalDateTime.now();
        Long machineId = 1L;
        Long processId = 1L;

        LogSaveTransfer logSaveTransfer = LogSaveTransfer.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
                .processId(processId)
                .build();

        // before
        Assertions.assertThatThrownBy(
                () -> logRepositoryJdbcTemplate.findRecentLogByMachineId(machineId)
                        .orElseThrow(IllegalAccessError::new)
        ).isInstanceOf(IllegalAccessError.class);

        // when
        Long logId = logRepositoryJdbcTemplate.save(logSaveTransfer);

        // then
        assertThat(logId).isGreaterThan(0);
        Log recentLog = logRepositoryJdbcTemplate.findRecentLogByMachineId(machineId)
                .orElseThrow(IllegalAccessError::new);
        assertThat(recentLog.getLogName()).isEqualTo(logName);
        assertThat(recentLog.getLogDateTime()).isEqualTo(logDateTime);
    }

}
