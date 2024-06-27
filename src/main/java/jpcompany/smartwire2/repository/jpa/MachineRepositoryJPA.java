package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MachineRepositoryJPA implements MachineRepository {

    private final EntityManager em;

    @Override
    public Long save(Machine machine) {
        em.persist(machine);
        return machine.getId();
    }

    @Override
    public List<Machine> findAllByMemberId(Long memberId) {
        String jpql =
                """
                SELECT m
                FROM Machine m
                WHERE m.memberId = :memberId
                """;
        return em.createQuery(jpql, Machine.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Machine findByMemberAndMachineIdAndMachineUUID(Long memberId, Long machineId, String machineUUID) {
        Machine findMachine = em.find(Machine.class, machineId);

        if (findMachine == null) {
            throw new CustomException(ErrorCode.INVALID_MACHINE);
        }
        if (Objects.equals(memberId, findMachine.getMemberId()) && findMachine.getMachineUUID().equals(machineUUID)) {
            return findMachine;
        }
        throw new CustomException(ErrorCode.INVALID_MACHINE);
    }

    @Override
    public void update(Machine machine) {
        Machine findMachine = em.find(Machine.class, machine.getId());
        findMachine.edit(machine);
    }

    @Override
    public void updateMachineUUID(Long memberId, Long machineId, String machineUUID) {
        Machine findMachine = em.find(Machine.class, machineId);

        if (findMachine == null) {
            throw new CustomException(ErrorCode.INVALID_MACHINE);
        }
        if (Objects.equals(memberId, findMachine.getMemberId())) {
            findMachine.setMachineUUID(machineUUID);
            return;
        }
        throw new CustomException(ErrorCode.INVALID_MACHINE);
    }
}
