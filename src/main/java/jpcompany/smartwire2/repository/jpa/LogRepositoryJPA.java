package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import jpcompany.smartwire2.service.vo.LogVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LogRepositoryJPA implements LogRepository {

    private final EntityManager em;

    @Override
    public Long save(LogSaveDto logSaveDto, Long machineId, Long processId) {
        Machine machine = em.find(Machine.class, machineId);
        if (machine == null) {
            log.error("없는 기계에 로그 저장을 시도함");
            throw new CustomException(ErrorCode.INVALID_MACHINE);
        }

        Process process = processId != null ?
                em.find(Process.class, processId) :
                null;

        Log log = logSaveDto.toLog(machine, process);
        em.persist(log);
        return log.getId();
    }

    @Override
    public void save(List<LogSaveDto> logSaveDtos, Long machineId, Long processId) {
        // TODO - 기계 로그 벌크 INSERT
        for (LogSaveDto logSaveDto : logSaveDtos) {
            save(logSaveDto, machineId, processId);
        }
    }

    @Override
    public LogVo findRecentLogByMachineId(Long machineId) {
        String jpql =
                """
                SELECT l
                FROM Log l
                WHERE l.machine.id = :machineId
                ORDER BY l.logDateTime DESC
                """;
        List<Log> result = em.createQuery(jpql, Log.class)
                .setParameter("machineId", machineId)
                .setMaxResults(1)
                .getResultList();

        return result.isEmpty() ?
                LogVo.createEmpty(machineId):
                LogVo.create(result.get(0));
    }
}
