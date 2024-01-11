package jpcompany.smartwire2.domain;

import jpcompany.smartwire2.domain.validator.MachineValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
public class Machine {
    private Long id;
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    private Integer sequence;
    private Boolean selected;
    private List<Process> processes;
    private List<Log> logs;

    public static Machine initMachine(
            Long id,
            String machineName,
            String machineModel,
            LocalDate dateManufactured,
            Integer sequence
    ) {
        String machineNameTrim = machineName.trim();
        String machineModelTrim = machineModel.trim();
        new MachineValidator().validate(
                machineNameTrim,
                machineModelTrim,
                dateManufactured
        );
        return Machine.builder()
                .id(id)
                .machineName(machineNameTrim)
                .machineModel(machineModelTrim)
                .dateManufactured(dateManufactured)
                .sequence(sequence)
                .build();
    }
}
