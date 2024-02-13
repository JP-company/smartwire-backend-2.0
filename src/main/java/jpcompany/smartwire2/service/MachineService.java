package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.request.MachineForm;
import jpcompany.smartwire2.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MachineService {

    private final MachineRepository machineRepository;

    public void setup(List<MachineForm> machinesForm, Long memberId) {
        List<Machine> validatedMachinesForm = machinesForm.stream()
                .map(MachineForm::toMachine)
                .toList();
        Machines machines = Machines.create(validatedMachinesForm);

        machines.addedMachines().forEach(machine -> machineRepository.save(machine, memberId));

        machines.existedMachines().forEach(machineRepository::update);
    }

    public List<Machine> findMachines(Member member) {
        return machineRepository.findAllByMemberId(member);
    }

    public String connectWithNewMachine(Member member, Long machineId) {
        String machineUUID = UUID.randomUUID().toString();
        machineRepository.updateMachineUUID(member, machineId, machineUUID);
        return machineUUID;
    }

    public Machine checkMachineConnection(Member member, Long machineId, String machineUUID) {
        return machineRepository.findByMemberAndMachineIdAndMachineUUID(member, machineId, machineUUID);
    }
}
