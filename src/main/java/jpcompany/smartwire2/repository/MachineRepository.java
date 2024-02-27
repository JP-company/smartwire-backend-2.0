package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.domain.Machine;

import java.util.List;

public interface MachineRepository {
    Long save(Machine machine);
    List<Machine> findAllByMemberId(Long memberId);
    void update(Machine machine);
    void updateMachineUUID(Long memberId, Long machineId, String machineUUID);

    Machine findByMemberAndMachineIdAndMachineUUID(Long memberId, Long machineId, String machineUUID);
}
