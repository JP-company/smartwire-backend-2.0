package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
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
                .map(machineForm -> machineForm.toMachine(memberId))
                .toList();

        Machines machines = Machines.create(validatedMachinesForm, memberId);

        machines.addedMachines().forEach(machineRepository::save);
        machines.existedMachines().forEach(machineRepository::update);
    }

    public Machines findMachines(Long memberId) {
        List<Machine> machines = machineRepository.findAllByMemberId(memberId);
        return Machines.create(machines, memberId);
    }

    public String connectWithNewMachine(Long memberId, Long machineId) {
        String machineUUID = UUID.randomUUID().toString();
        machineRepository.updateMachineUUID(memberId, machineId, machineUUID);
        return machineUUID;
    }

    public Machine checkMachineConnection(Long memberId, Long machineId, String machineUUID) {
        return machineRepository.findByMemberAndMachineIdAndMachineUUID(memberId, machineId, machineUUID);
    }
}
