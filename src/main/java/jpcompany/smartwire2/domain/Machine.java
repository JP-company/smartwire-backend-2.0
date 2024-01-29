package jpcompany.smartwire2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore private String machineUUID;
    private List<Process> processes;

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
