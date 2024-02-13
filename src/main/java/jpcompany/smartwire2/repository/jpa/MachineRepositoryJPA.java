package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MachineRepositoryJPA implements MachineRepository {

    private final EntityManager em;
    @Override
    public Long save(Machine machine, Long memberId) {
        Member findMember = em.find(Member.class, memberId);
        machine.setMember(findMember);
        em.persist(machine);
        return machine.getId();
    }

    @Override
    public List<Machine> findAllByMemberId(Member member) {
        String jpql =
                """
                SELECT m
                FROM Machine m
                WHERE m.member = :member
                """;
        return em.createQuery(jpql, Machine.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public void update(Machine machine) {
        Machine findMachine = em.find(Machine.class, machine.getId());
        findMachine.edit(machine);
    }

    @Override
    public void updateMachineUUID(Member member, Long machineId, String machineUUID) {
        Machine machine = em.find(Machine.class, machineId);
        if (member.getId() == machine.getMember().getId()) {
            machine.setMachineUUID(machineUUID);
            return;
        }
        throw new CustomException(ErrorCode.UNDEFINED_ERROR);
    }

    @Override
    public Machine findByMemberAndMachineIdAndMachineUUID(Member member, Long machineId, String machineUUID) {
        Machine findMachine = em.find(Machine.class, machineId);
        if (member.getId() == findMachine.getMember().getId() && findMachine.getMachineUUID().equals(machineUUID)) {
            findMachine.setMember(member);
            return findMachine;
        }
        throw new CustomException(ErrorCode.UNDEFINED_ERROR);
    }
}
