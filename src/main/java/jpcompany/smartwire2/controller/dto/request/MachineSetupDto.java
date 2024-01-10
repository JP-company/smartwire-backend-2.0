package jpcompany.smartwire2.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jpcompany.smartwire2.service.dto.MachineSetupCommand;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MachineSetupDto {
    private Long id;
    @NotBlank
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    @NotBlank
    private int sequence;

    public static MachineSetupCommand toMachineSetupCommand(MachineSetupDto machineSetupDto) {
        return MachineSetupCommand.builder()
                .id(machineSetupDto.id)
                .machineName(machineSetupDto.machineName)
                .machineModel(machineSetupDto.machineModel)
                .dateManufactured(machineSetupDto.dateManufactured)
                .sequence(machineSetupDto.sequence)
                .build();
    }
}