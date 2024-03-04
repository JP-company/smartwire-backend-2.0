package jpcompany.smartwire2.unit.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.repository.MachineRepository;
import jpcompany.smartwire2.service.dto.LogSaveDto;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class LogRepositoryJPATest {

    @Autowired private LogRepository logRepository;
    @Autowired private MachineRepository machineRepository;
    @Autowired private EntityManager em;

    private Long machineId = 1L;
    private String logName = "start_작업 시작";
    private LocalDateTime logDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);


    @BeforeEach
    void beforeEach() {
        Machine machine = createMachine(1L);
        machineId = machineRepository.save(machine);
        em.clear();
    }

    @Test
    @DisplayName("로그 저장, 최근 로그 조회")
    void test1() {
        // given
        LogSaveDto logSaveDto = LogSaveDto.create(
                logName,
                logDateTime
        );

        // when
        logRepository.save(logSaveDto, machineId, null);

        // then
        LogVo findRecentLogVo = logRepository.findRecentLogByMachineId(machineId);
        assertThat(findRecentLogVo.getLogName()).isEqualTo(logName);
        assertThat(findRecentLogVo.getLogDateTime()).isEqualTo(logDateTime);
    }

    @Test
    @DisplayName("없는 기계에 로그 저장 시도 시 예외 발생")
    void test2() {
        // given
        LogSaveDto logSaveDto = LogSaveDto.create(
                logName,
                logDateTime
        );

        // when, then
        assertThatThrownBy(() -> logRepository.save(logSaveDto, 1000L, null))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_MACHINE.getReason());
    }


    private Machine createMachine(Long memberId) {
        final String machineName = "1호기";
        final String machineModel = "scp-097";
        final LocalDate dateManufactured = LocalDate.of(2024, 1, 1);
        final int sequence = 0;

        return Machine.create(
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
    }
}
