package jpcompany.smartwire2.domain.validator;

import jpcompany.smartwire2.domain.Machine;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

public class MachinesValidator {

    public void validate(List<Machine> machinesForm) {
        if (isMachineNameDuplicated(machinesForm)) {
            throw new DuplicateFormatFlagsException("기계 이름은 중복될 수 없습니다.");
        }
        if (isSequenceDuplicated(machinesForm)) {
            throw new DuplicateFormatFlagsException("기계 나열 순서는 중복될 수 없습니다.");
        }
    }

    private boolean isSequenceDuplicated(List<Machine> machinesForm) {
        return machinesForm.stream()
                .mapToInt(Machine::getSequence)
                .distinct()
                .count() != machinesForm.size();
    }

    private boolean isMachineNameDuplicated(List<Machine> machinesForm) {
        return machinesForm.stream()
                .map(machine -> machine.getMachineName().trim())
                .distinct()
                .count() != machinesForm.size();
    }
}
