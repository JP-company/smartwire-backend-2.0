package jpcompany.smartwire2.unit.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.MachineRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MachineRepositoryJPATest {
    @Autowired MachineRepository machineRepository;
    @Autowired EntityManager em;

    private final String machineName = "1호기";
    private final String machineModel = "scp-097";
    private final LocalDate dateManufactured = LocalDate.of(2024, 1, 1);
    private final int sequence = 0;
    private final Long memberId = 1L;
    private final String TEST_UUID = "testUUID";

    @Test
    @DisplayName("기계 저장, 조회")
    void test1() {
        // given
        Machine machine = createMachine();
        machineRepository.save(machine);
        em.flush();
        em.clear();

        // when
        List<Machine> findMachines = machineRepository.findAllByMemberId(memberId);

        // then
        assertThat(findMachines.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기계 정보 업데이트")
    void test2() {
        // given
        Machine machine = createMachine();
        machineRepository.save(machine);
        em.flush();
        em.clear();

        // before
        List<Machine> findMachinesBeforeUpdate = machineRepository.findAllByMemberId(memberId);
        Machine findMachineBefore = findMachinesBeforeUpdate.get(0);
        assertThat(findMachineBefore.getMachineName()).isEqualTo(this.machineName);
        assertThat(findMachineBefore.getMachineModel()).isEqualTo(this.machineModel);
        assertThat(findMachineBefore.getDateManufactured()).isEqualTo(this.dateManufactured);
        assertThat(findMachineBefore.getSequence()).isEqualTo(this.sequence);

        // when
        String machineName = "2호기";
        String machineModel = "abc-123";
        LocalDate dateManufactured = LocalDate.of(2024, 2, 2);
        int sequence = 1;

        Machine editedMachine = Machine.create(
                findMachineBefore.getId(),
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
        machineRepository.update(editedMachine);

        // then
        List<Machine> findMachinesAfter = machineRepository.findAllByMemberId(memberId);
        assertThat(findMachinesAfter.size()).isEqualTo(1);

        Machine findMachineAfter = findMachinesAfter.get(0);
        assertThat(findMachineAfter.getMachineName()).isEqualTo(machineName);
        assertThat(findMachineAfter.getMachineModel()).isEqualTo(machineModel);
        assertThat(findMachineAfter.getDateManufactured()).isEqualTo(dateManufactured);
        assertThat(findMachineAfter.getSequence()).isEqualTo(sequence);
    }

    @Test
    @DisplayName("기계 machineUUID 업데이트, 조회")
    void test3() {
        // given
        Machine machine = createMachine();
        Long machineId = machineRepository.save(machine);
        em.flush();
        em.clear();

        // when
        String machineUUID = UUID.randomUUID().toString();
        machineRepository.updateMachineUUID(memberId, machineId, machineUUID);

        // then
        Machine findMachine = machineRepository.findByMemberAndMachineIdAndMachineUUID(memberId, machineId, machineUUID);
        assertThat(findMachine.getMachineUUID()).isEqualTo(machineUUID);
    }

    @Test
    @DisplayName("없는 machineId 로 기계 조회 시 예외 발생")
    void test4() {
        // when, then
        assertThatThrownBy(
                () -> machineRepository.findByMemberAndMachineIdAndMachineUUID(
                        memberId,
                        1L,
                        TEST_UUID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }

    @Test
    @DisplayName("다른 계정 기계의 machineUUID 로 기계 조회 시 예외 발생")
    void test6() {
        // given
        Machine machine = createMachine();
        Long machineId = machineRepository.save(machine);
        em.flush();
        em.clear();

        // when, then
        assertThatThrownBy(
                () -> machineRepository.findByMemberAndMachineIdAndMachineUUID(
                        2L,
                        machineId,
                        TEST_UUID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }

    @Test
    @DisplayName("DB의 machineUUID 와 다른 machineUUID 로 기계 조회 시 예외 발생")
    void test7() {
        // given
        Machine machine = createMachine();
        Long machineId = machineRepository.save(machine);
        em.flush();
        em.clear();

        String machineUUID = UUID.randomUUID().toString();
        machineRepository.updateMachineUUID(memberId, machineId, machineUUID);

        // when, then
        assertThatThrownBy(
                () -> machineRepository.findByMemberAndMachineIdAndMachineUUID(
                        memberId,
                        machineId,
                        TEST_UUID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }

    @Test
    @DisplayName("없는 machineId 로 machineUUID 업데이트 시 예외 발생")
    void test8() {
        // when, then
        assertThatThrownBy(() -> machineRepository.updateMachineUUID(memberId, 1L, TEST_UUID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }

    @Test
    @DisplayName("다른 계정 기계의 machineUUID 업데이트 시 예외 발생")
    void test9() {
        // given
        Machine machine = createMachine();
        Long machineId = machineRepository.save(machine);

        // when, then
        assertThatThrownBy(() -> machineRepository.updateMachineUUID(2L, machineId, TEST_UUID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }

    private Machine createMachine() {
        return Machine.create(
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
    }
}