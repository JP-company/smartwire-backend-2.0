package jpcompany.smartwire2.domain.validator;

import java.time.LocalDate;

public class MachineValidator {

    public void validate(String machineName, String machineModel, LocalDate dateManufactured) {
        validateMachineName(machineName);
        if (machineModel != null) {
            validateMachineModel(machineModel);
        }
        validateDateManufactured(dateManufactured);
    }

    private void validateMachineName(String machineName) {
        String MachineNameRegex = "[a-zA-Z\\d!?@$%^&\\-*+=가-힣]{2,20}";
        if (!machineName.matches(MachineNameRegex)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateMachineModel(String machineModel) {
        String MachineModelRegex = "[a-zA-Z\\d!?@$%^&\\-*+=가-힣]{2,20}";
        if (!machineModel.matches(MachineModelRegex)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDateManufactured(LocalDate dateManufactured) {
        if (dateManufactured == null) {
            return;
        }
        if (LocalDate.now().isBefore(dateManufactured)) {
            throw new IllegalArgumentException();
        }
    }
}
