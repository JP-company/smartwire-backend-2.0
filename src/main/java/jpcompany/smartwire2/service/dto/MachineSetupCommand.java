package jpcompany.smartwire2.service.dto;

import jakarta.validation.constraints.NotBlank;
import jpcompany.smartwire2.domain.Machine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class MachineSetupCommand {
    private Long id;
    @NotBlank
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    @NotBlank
    private int sequence;

    public static Machine toMachine(MachineSetupCommand machineSetupCommand) {
        return Machine.initMachine(
                machineSetupCommand.id,
                machineSetupCommand.machineName,
                machineSetupCommand.machineModel,
                machineSetupCommand.dateManufactured,
                machineSetupCommand.sequence
        );
    }
}

