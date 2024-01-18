package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.jdbctemplate.MachineRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MachineSetupTransfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MachineRepositoryJdbcTemplateTest {

    @Autowired
    private MachineRepositoryJdbcTemplate machineRepositoryJdbcTemplate;

    private final Long memberId = 1L;

    @Test
    @DisplayName("기계 정보 저장, 전체 조회")
    @Transactional
    void test() {
        // before
        List<Machine> machinesBeforeSave = machineRepositoryJdbcTemplate.findAllByMemberId(memberId);
        assertThat(machinesBeforeSave.size()).isEqualTo(1);

        // given
        String machineName = "2호기";
        String machineModel = null;
        LocalDate localDate = null;
        Integer sequence = 1;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(memberId)
                .build();

        // when
        machineRepositoryJdbcTemplate.save(machineSetupTransfer);

        // then
        List<Machine> machines = machineRepositoryJdbcTemplate.findAllByMemberId(memberId);
        assertThat(machines.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("기계 정보 업데이트, 전체 조회")
    @Transactional
    void test2() {
        // given
        Long id = 1L;
        String machineName = "1번 와이어";
        String machineModel = "SCP-096";
        LocalDate localDate = LocalDate.now();
        Integer sequence = 1;
        Long memberId = 1L;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .id(id)
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(memberId)
                .build();

        // before
        List<Machine> machinesBeforeUpdate = machineRepositoryJdbcTemplate.findAllByMemberId(this.memberId);
        Machine machineBeforeUpdate = machinesBeforeUpdate.stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new);

        assertThat(machineBeforeUpdate.getId()).isEqualTo(id);
        assertThat(machineBeforeUpdate.getMachineName()).isNotEqualTo(machineName);
        assertThat(machineBeforeUpdate.getMachineModel()).isNotEqualTo(machineModel);
        assertThat(machineBeforeUpdate.getDateManufactured()).isNotEqualTo(localDate);
        assertThat(machineBeforeUpdate.getSequence()).isNotEqualTo(sequence);

        // when
        machineRepositoryJdbcTemplate.update(machineSetupTransfer);

        // then
        List<Machine> machines = machineRepositoryJdbcTemplate.findAllByMemberId(this.memberId);
        Machine machine = machines.stream().findFirst().orElseThrow(IllegalAccessError::new);
        assertThat(machine.getId()).isEqualTo(id);
        assertThat(machine.getMachineName()).isEqualTo(machineName);
        assertThat(machine.getMachineModel()).isEqualTo(machineModel);
        assertThat(machine.getSequence()).isEqualTo(sequence);
        assertThat(machine.getDateManufactured()).isEqualTo(localDate);
    }

    @Test
    @DisplayName("없는 id로 기계 정보 업데이트 시 예외 발생")
    void test3() {
        // given
        Long id = 100000L;
        String machineName = "1번 와이어";
        String machineModel = "SCP-096";
        LocalDate localDate = LocalDate.now();
        Integer sequence = 1;
        Long memberId = 1L;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .id(id)
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(memberId)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> machineRepositoryJdbcTemplate.update(machineSetupTransfer))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.UPDATE_FAILED_MACHINE_INFO.getReason());
    }

    @Test
    @DisplayName("memberId가 다른 잘못된 기계 정보 업데이트 시 예외 발생")
    void test5() {
        // given
        Long id = 1L;
        String machineName = "1번 와이어";
        String machineModel = "SCP-096";
        LocalDate localDate = LocalDate.now();
        Integer sequence = 1;
        Long memberId = 100000L;

        MachineSetupTransfer machineSetupTransfer = MachineSetupTransfer.builder()
                .id(id)
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(localDate)
                .sequence(sequence)
                .memberId(memberId)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> machineRepositoryJdbcTemplate.update(machineSetupTransfer))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.UPDATE_FAILED_MACHINE_INFO.getReason());
    }


    @Test
    @DisplayName("기계 정보 삭제, 전체 조회")
    @Transactional
    void test4() {
        // before
        List<Machine> machinesBeforeDelete = machineRepositoryJdbcTemplate.findAllByMemberId(memberId);
        assertThat(machinesBeforeDelete.size()).isEqualTo(1);

        // when
        Long machineId = 1L;
        machineRepositoryJdbcTemplate.delete(machineId);

        // then
        List<Machine> machines = machineRepositoryJdbcTemplate.findAllByMemberId(memberId);
        assertThat(machines.size()).isEqualTo(0);
    }
}