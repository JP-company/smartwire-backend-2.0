package jpcompany.smartwire2.dto.response;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MachineResponse {
    private Long id;
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    private Integer sequence;
    private boolean selected;

    public static MachineResponse create(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .machineName(machine.getMachineName())
                .machineModel(machine.getMachineModel())
                .dateManufactured(machine.getDateManufactured())
                .sequence(machine.getSequence())
                .selected(machine.getMachineUUID() != null)
                .build();
    }

    public static List<MachineResponse> createList(Machines machines) {
        return machines.getMachines().stream()
                .map(MachineResponse::create)
                .sorted(Comparator.comparingInt(MachineResponse::getSequence))
                .toList();
    }
}