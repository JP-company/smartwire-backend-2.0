package jpcompany.smartwire2.domain;

import jpcompany.smartwire2.domain.validator.MachinesValidator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Machines {
    private final List<Machine> machines = new ArrayList<>();

    public static Machines create(List<Machine> machinesForm, Long memberId) {
        return new Machines(machinesForm);
    }

    private Machines(List<Machine> machinesForm) {
        new MachinesValidator().validate(machinesForm);
        machines.addAll(machinesForm);
    }

    public List<Machine> addedMachines() {
        return machines.stream()
                .filter(machine -> machine.getId() == null)
                .toList();
    }

    public List<Machine> existedMachines() {
        return machines.stream()
                .filter(machine -> machine.getId() != null)
                .toList();
    }

    public List<Long> getMachineIds() {
        return machines.stream()
                .map(Machine::getId)
                .toList();
    }
}