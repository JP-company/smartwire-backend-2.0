package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.jdbctemplate.ProcessRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProcessRepositoryJdbcTemplateTest {

    @Autowired
    private ProcessRepositoryJdbcTemplate processRepositoryJdbcTemplate;

    @Test
    @DisplayName("새 작업 저장, 최신 작업 조회 후 결과 확인")
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

        // before
        assertThatThrownBy(() -> processRepositoryJdbcTemplate.findRecentProcessByMachineId(machineId).orElseThrow(IllegalAccessError::new))
                .isInstanceOf(IllegalAccessError.class);

        // when
        Long processId = processRepositoryJdbcTemplate.save(processSaveTransfer);

        // then
        assertThat(processId).isGreaterThan(0);
        Process process = processRepositoryJdbcTemplate.findRecentProcessByMachineId(machineId)
                .orElseThrow(IllegalAccessError::new);
        assertThat(process.getFileName()).isEqualTo(fileName);
        assertThat(process.getThickness()).isEqualTo(thickness);
        assertThat(process.getStartedDateTime()).isEqualTo(startedDateTime);
        assertThat(process.getFinishedDateTime()).isNull();
    }

    @Test
    @DisplayName("작업이 완료 되면 완료 시간 업데이트, 최신 작업 조회 후 결과 확인")
    void updateFinishedDateTime() {
        // given
        String fileName = "123.NC";
        Float thickness = 27.5f;
        Long machineId = 1L;
        LocalDateTime startedDateTime = LocalDateTime.now().minusHours(5);
        LocalDateTime finishedDateTime = LocalDateTime.now();

        ProcessSaveTransfer processSaveTransfer = ProcessSaveTransfer.builder()
                .fileName(fileName)
                .thickness(thickness)
                .machineId(machineId)
                .startedDateTime(startedDateTime)
                .build();
        processRepositoryJdbcTemplate.save(processSaveTransfer);

        // before
        Process processBeforeUpdate = processRepositoryJdbcTemplate.findRecentProcessByMachineId(machineId)
                .orElseThrow(IllegalAccessError::new);
        assertThat(processBeforeUpdate.getFinishedDateTime()).isNull();

        // when
        processRepositoryJdbcTemplate.updateFinishedDateTime(machineId, finishedDateTime);

        // then
        Process process = processRepositoryJdbcTemplate.findRecentProcessByMachineId(machineId)
                .orElseThrow(IllegalAccessError::new);
        assertThat(process.getFinishedDateTime()).isEqualTo(finishedDateTime);
    }
}