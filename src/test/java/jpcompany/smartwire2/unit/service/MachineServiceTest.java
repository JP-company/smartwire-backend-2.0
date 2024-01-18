package jpcompany.smartwire2.unit.service;

import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.service.MachineService;
import jpcompany.smartwire2.service.dto.MachineSetupCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MachineServiceTest {

    @Autowired
    private MachineService machineService;
    private final Long memberId = 1L;
    private final List<MachineSetupCommand> machinesSetupForm = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        MachineSetupCommand machineSetupCommand1 =
                MachineSetupCommand.builder()
                        .id(1L)
                        .machineName("1호기")
                        .machineModel("scp-001")
                        .dateManufactured(LocalDate.now())
                        .sequence(0)
                        .build();

        MachineSetupCommand machineSetupCommand2 =
                MachineSetupCommand.builder()
                        .id(null)
                        .machineName("2호기")
                        .machineModel("scp-002")
                        .dateManufactured(null)
                        .sequence(1)
                        .build();

        MachineSetupCommand machineSetupCommand3 =
                MachineSetupCommand.builder()
                        .id(null)
                        .machineName("3호기")
                        .machineModel("scp-003")
                        .dateManufactured(null)
                        .sequence(2)
                        .build();

        machinesSetupForm.add(machineSetupCommand1);
        machinesSetupForm.add(machineSetupCommand2);
        machinesSetupForm.add(machineSetupCommand3);
    }

    @Test
    @DisplayName("기계 정보 세팅 - 이미 있는 기계는 업데이트, 새로운 기계는 추가")
    @Transactional
    void test1() {
        // when
        machineService.setup(machinesSetupForm, memberId);

        // then
        List<Machine> machines = machineService.findMachines(memberId);
        assertThat(machines.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("기계 정보 업데이트 실패 시 예외 발생")
    void test2() {
        // given
        MachineSetupCommand machineSetupCommand4 =
                MachineSetupCommand.builder()
                        .id(1000000L)
                        .machineName("4호기")
                        .machineModel("scp-004")
                        .dateManufactured(null)
                        .sequence(3)
                        .build();
        machinesSetupForm.add(machineSetupCommand4);

        // when, then
        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.UPDATE_FAILED_MACHINE_INFO.getReason());
    }

    @Test
    @DisplayName("중복된 기계 이름 존재 시 예외 발생")
    void test5() {
        // given
        MachineSetupCommand machineSetupCommand4 =
                MachineSetupCommand.builder()
                        .id(1L)
                        .machineName("3호기")
                        .machineModel("scp-004")
                        .dateManufactured(null)
                        .sequence(3)
                        .build();
        machinesSetupForm.add(machineSetupCommand4);

        // when, then
        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.DUPLICATED_MACHINE_NAME.getReason());
    }

    @Test
    @DisplayName("중복된 기계 순서 존재 시 예외 발생")
    void test6() {
        // given
        MachineSetupCommand machineSetupCommand4 =
                MachineSetupCommand.builder()
                        .id(1L)
                        .machineName("4호기")
                        .machineModel("scp-004")
                        .dateManufactured(null)
                        .sequence(2)
                        .build();
        machinesSetupForm.add(machineSetupCommand4);

        // when, then
        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.DUPLICATED_MACHINE_SEQUENCE.getReason());
    }
}