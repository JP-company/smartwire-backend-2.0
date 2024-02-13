package jpcompany.smartwire2.domain;

import jpcompany.smartwire2.domain.validator.MachinesValidator;

import java.util.List;

public class Machines {
    private final List<Machine> machines;

    public static Machines create(List<Machine> machinesForm) {
        return new Machines(machinesForm);
    }

    private Machines(List<Machine> machinesForm) {
        new MachinesValidator().validate(machinesForm);
        machines = machinesForm;
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
}