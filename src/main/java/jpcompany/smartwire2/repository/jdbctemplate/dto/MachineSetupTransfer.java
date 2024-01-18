package jpcompany.smartwire2.repository.jdbctemplate.dto;

import jpcompany.smartwire2.domain.Machine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MachineSetupTransfer {
    private Long id;
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    private int sequence;
    private Long memberId;

    public static MachineSetupTransfer of(Machine machine, Long memberId) {
        return MachineSetupTransfer.builder()
                .id(machine.getId())
                .machineName(machine.getMachineName())
                .machineModel(machine.getMachineModel())
                .dateManufactured(machine.getDateManufactured())
                .sequence(machine.getSequence())
                .memberId(memberId)
                .build();
    }
}
