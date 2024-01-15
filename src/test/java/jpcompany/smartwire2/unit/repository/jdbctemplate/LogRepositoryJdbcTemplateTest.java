package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.repository.jdbctemplate.LogRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.ProcessRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import org.assertj.core.api.Assertions;
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


    @Test
    @DisplayName("작업 중인 상태에서 새 로그 저장 시 process_id 값 할당")
    void save() {
        // given
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

        String logName = "작업중 정지";
        LocalDateTime logDateTime = LocalDateTime.now();

        LogSaveTransfer logSaveTransfer = LogSaveTransfer.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
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
        assertThat(recentLog.getProcessId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("작업이 아예 없는 상태에서 새 로그 저장 시 process_id 값 null")
    void test2() {
        // given
        Long machineId = 1L;
        String logName = "작업중 정지";
        LocalDateTime logDateTime = LocalDateTime.now();

        LogSaveTransfer logSaveTransfer = LogSaveTransfer.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
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
        assertThat(recentLog.getProcessId()).isEqualTo(0);
    }

    @Test
    @DisplayName("작업이 끝난 상태에서 새 로그 저장 시 process_id 값 null")
    void test3() {
        // given
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
        processRepositoryJdbcTemplate.updateFinishedDateTime(machineId, LocalDateTime.now());

        String logName = "작업중 정지";
        LocalDateTime logDateTime = LocalDateTime.now();

        LogSaveTransfer logSaveTransfer = LogSaveTransfer.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machineId(machineId)
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
        assertThat(recentLog.getProcessId()).isEqualTo(0);
    }
}
