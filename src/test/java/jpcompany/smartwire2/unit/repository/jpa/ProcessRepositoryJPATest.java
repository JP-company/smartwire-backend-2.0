package jpcompany.smartwire2.unit.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.repository.MachineRepository;
import jpcompany.smartwire2.repository.ProcessRepository;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import jpcompany.smartwire2.service.vo.LogVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProcessRepositoryJPATest {

    @Autowired private ProcessRepository processRepository;
    @Autowired private LogRepository logRepository;
    @Autowired private MachineRepository machineRepository;
    @Autowired private EntityManager em;
    private Long machineId;
    private final String FILE_NAME = "test.NC";
    private final Float THICKNESS = 30.0f;
    private final LocalDateTime STARTED_DATE_TIME = LocalDateTime.of(2024, 1, 1, 0,0,0);

    @BeforeEach
    void beforeEach() {
        Machine machine = createMachine();
        machineId = machineRepository.save(machine);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("작업 저장")
    void test1() {
        // given
        ProcessSaveDto processSaveDto = createProcess(machineId);
        em.flush();
        em.clear();

        // when
        Long processId = processRepository.save(processSaveDto);

        // then
        assertThat(processId).isEqualTo(1L);
    }

    @Test
    @DisplayName("작업 저장 자체만으로는 조회할 수 없음, 로그로 조회해야함")
    void test2() {
        // given
        ProcessSaveDto processSaveDto = createProcess(machineId);
        processRepository.save(processSaveDto);

        // when
        LogVo logVo = logRepository.findRecentLogByMachineId(machineId);

        // then
        assertThat(logVo.getFileName()).isNull();
        assertThat(logVo.getThickness()).isNull();
    }

    private ProcessSaveDto createProcess(Long machineId) {
        return ProcessSaveDto.create(
                FILE_NAME,
                THICKNESS,
                STARTED_DATE_TIME,
                machineId
        );
    }

    private Machine createMachine() {
        final String machineName = "1호기";
        final String machineModel = "scp-097";
        final LocalDate dateManufactured = LocalDate.of(2024, 1, 1);
        final int sequence = 0;
        final Long memberId = 1L;

        return Machine.create(
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
    }
}