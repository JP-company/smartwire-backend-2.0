package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Process;
import jpcompany.smartwire2.repository.ProcessRepository;
import jpcompany.smartwire2.service.dto.ProcessSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ProcessRepositoryJPA implements ProcessRepository {

    private final EntityManager em;

    @Override
    public Long save(ProcessSaveDto processSaveDto) {
        Machine machine = em.find(Machine.class, processSaveDto.getMachineId());
        Process process = processSaveDto.toProcess(machine);
        em.persist(process);
        return process.getId();
    }

    @Override
    public void updateFinishedDateTime(Long machineId, LocalDateTime logDateTime) {
        String jpql =
                """
                SELECT p
                FROM Process p
                ORDER BY p.id DESC
                """;
        Process process = em.createQuery(jpql, Process.class)
                .getSingleResult();
        process.finished(logDateTime);
    }
}
