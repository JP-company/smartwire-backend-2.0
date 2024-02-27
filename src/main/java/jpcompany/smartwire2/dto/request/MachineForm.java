package jpcompany.smartwire2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jpcompany.smartwire2.domain.Machine;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MachineForm {
    private Long id;

    @NotBlank
    private String machineName;

    private String machineModel;

    private LocalDate dateManufactured;

    @NotBlank
    private int sequence;

    public Machine toMachine(Long memberId) {
        return Machine.create(
                id,
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
    }
}