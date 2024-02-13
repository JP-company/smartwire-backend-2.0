//package jpcompany.smartwire2.unit.service;
//
//import jpcompany.smartwire2.common.error.CustomException;
//import jpcompany.smartwire2.common.error.ErrorCode;
//import jpcompany.smartwire2.service.MachineService;
//import jpcompany.smartwire2.service.dto.MachineDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//class MachineServiceTest {
//
//    @Autowired
//    private MachineService machineService;
//    private final Long memberId = 1L;
//    private final List<MachineDto> machinesSetupForm = new ArrayList<>();
//
//    @BeforeEach
//    void beforeEach() {
//        MachineDto machineDto1 =
//                MachineDto.builder()
//                        .id(1L)
//                        .machineName("1호기")
//                        .machineModel("scp-001")
//                        .dateManufactured(LocalDate.now())
//                        .sequence(0)
//                        .build();
//
//        MachineDto machineDto2 =
//                MachineDto.builder()
//                        .machineName("2호기")
//                        .machineModel("scp-002")
//                        .sequence(1)
//                        .build();
//
//        MachineDto machineDto3 =
//                MachineDto.builder()
//                        .machineName("3호기")
//                        .machineModel("scp-003")
//                        .sequence(2)
//                        .build();
//
//        machinesSetupForm.add(machineDto1);
//        machinesSetupForm.add(machineDto2);
//        machinesSetupForm.add(machineDto3);
//    }
//
//    @Test
//    @DisplayName("기계 정보 업데이트 실패 시 예외 발생")
//    void test2() {
//        // given
//        MachineDto machineDto4 =
//                MachineDto.builder()
//                        .id(1000000L)
//                        .machineName("4호기")
//                        .machineModel("scp-004")
//                        .dateManufactured(null)
//                        .sequence(3)
//                        .build();
//        machinesSetupForm.add(machineDto4);
//
//        // when, then
//        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.UPDATE_FAILED_MACHINE_INFO.getReason());
//    }
//
//    @Test
//    @DisplayName("중복된 기계 이름 존재 시 예외 발생")
//    void test5() {
//        // given
//        MachineDto machineDto4 =
//                MachineDto.builder()
//                        .id(1L)
//                        .machineName("3호기")
//                        .machineModel("scp-004")
//                        .dateManufactured(null)
//                        .sequence(3)
//                        .build();
//        machinesSetupForm.add(machineDto4);
//
//        // when, then
//        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.DUPLICATED_MACHINE_NAME.getReason());
//    }
//
//    @Test
//    @DisplayName("중복된 기계 순서 존재 시 예외 발생")
//    void test6() {
//        // given
//        MachineDto machineDto4 =
//                MachineDto.builder()
//                        .id(1L)
//                        .machineName("4호기")
//                        .machineModel("scp-004")
//                        .dateManufactured(null)
//                        .sequence(2)
//                        .build();
//        machinesSetupForm.add(machineDto4);
//
//        // when, then
//        assertThatThrownBy(() -> machineService.setup(machinesSetupForm, memberId))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.DUPLICATED_MACHINE_SEQUENCE.getReason());
//    }
//}