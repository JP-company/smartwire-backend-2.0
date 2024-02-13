package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Member;

import java.util.List;

public interface MachineRepository {
    Long save(Machine machine, Long memberId);
    List<Machine> findAllByMemberId(Member member);
    void update(Machine machine);
    void updateMachineUUID(Member member, Long machineId, String machineUUID);

    Machine findByMemberAndMachineIdAndMachineUUID(Member member, Long machineId, String machineUUID);
}
