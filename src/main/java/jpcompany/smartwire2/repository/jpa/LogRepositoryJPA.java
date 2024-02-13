package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.domain.Log;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.LogRepository;
import jpcompany.smartwire2.service.dto.LogSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogRepositoryJPA implements LogRepository {

    private final EntityManager em;

    @Override
    public Long save(LogSaveDto logSaveDto) {
        Machine machine = em.find(Machine.class, logSaveDto.getMachineId());
        String jpql =
                """
                SELECT p
                FROM Process p
                WHERE p.machine = machine
                ORDER BY p.id DESC
                """;
        Process process = em.createQuery(jpql, Process.class)
                .getSingleResult();

        Log log = logSaveDto.toLog(machine, process);
        em.persist(log);
        return log.getId();
    }
}
