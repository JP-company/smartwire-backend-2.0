package jpcompany.smartwire2.domain.validator;

import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;

import java.util.List;

public class MachinesValidator {

    public void validate(List<Machine> machinesForm) {
        if (isMachineNameDuplicated(machinesForm)) {
            throw new CustomException(ErrorCode.DUPLICATED_MACHINE_NAME);
        }
        if (isSequenceDuplicated(machinesForm)) {
            throw new CustomException(ErrorCode.DUPLICATED_MACHINE_SEQUENCE);
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
