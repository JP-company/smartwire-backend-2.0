package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.jdbctemplate.MachineRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MachineSetupTransfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MachineRepositoryJdbcTemplateTest {

    @Autowired
    private MachineRepositoryJdbcTemplate machineRepositoryJdbcTemplate;

    @Test
    @DisplayName("기계 정보 저장, 조회")
    void test() {
        // given
        String machineName = "2호기";
        String machineModel = null;
        LocalDate localDate = null;
        Integer sequence = 1;
        Long memberId = 1L;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(memberId)
                .build();

        // when
        Long machineId = machineRepositoryJdbcTemplate.save(machineSetupTransfer);

        // then
        Machine machine = machineRepositoryJdbcTemplate.findById(machineId)
                .orElseThrow(() -> new IllegalAccessError(""));

        assertThat(machine.getId()).isEqualTo(machineId);
        assertThat(machine.getMachineName()).isEqualTo(machineName);
        assertThat(machine.getMachineModel()).isEqualTo(machineModel);
        assertThat(machine.getSequence()).isEqualTo(sequence);
        assertThat(machine.getDateManufactured()).isEqualTo(localDate);
    }

    @Test
    @DisplayName("기계 정보 업데이트, 조회")
    void test2() {
        // given
        Long id = 1L;
        String machineName = "1번 와이어";
        String machineModel = "SCP-096";
        Integer sequence = 1;
        LocalDate localDate = null;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .id(id)
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(1L)
                .build();

        // when
        machineRepositoryJdbcTemplate.update(machineSetupTransfer);

        // then
        Machine machine = machineRepositoryJdbcTemplate.findById(id)
                .orElseThrow(() -> new IllegalAccessError(""));

        assertThat(machine.getId()).isEqualTo(id);
        assertThat(machine.getMachineName()).isEqualTo(machineName);
        assertThat(machine.getMachineModel()).isEqualTo(machineModel);
        assertThat(machine.getSequence()).isEqualTo(sequence);
        assertThat(machine.getDateManufactured()).isEqualTo(localDate);
    }

}