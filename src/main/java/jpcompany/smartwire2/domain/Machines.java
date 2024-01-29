package jpcompany.smartwire2.domain;

import jpcompany.smartwire2.domain.validator.MachinesValidator;
import lombok.Getter;

import java.util.List;

@Getter
public class Machines {
    private final List<Machine> machinesInfo;
    public Machines(List<Machine> machinesForm) {
        new MachinesValidator().validate(machinesForm);
        this.machinesInfo = machinesForm;
    }
}
