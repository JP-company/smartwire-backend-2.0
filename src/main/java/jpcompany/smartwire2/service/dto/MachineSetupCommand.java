package jpcompany.smartwire2.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jpcompany.smartwire2.domain.Machine;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MachineSetupCommand {
    private Long id;
    @NotEmpty
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    @NotEmpty
    private int sequence;

    public static Machine toMachine(MachineSetupCommand machineSetupCommand) {
        return Machine.initMachine(
                machineSetupCommand.getId(),
                machineSetupCommand.getMachineName(),
                machineSetupCommand.getMachineModel(),
                machineSetupCommand.getDateManufactured(),
                machineSetupCommand.getSequence()
        );
    }
}
