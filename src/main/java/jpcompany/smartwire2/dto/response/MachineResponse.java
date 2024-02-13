package jpcompany.smartwire2.dto.response;

import jpcompany.smartwire2.domain.Machine;
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
    private MemberResponse member;

    public static MachineResponse create(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .machineName(machine.getMachineName())
                .machineModel(machine.getMachineModel())
                .dateManufactured(machine.getDateManufactured())
                .sequence(machine.getSequence())
                .selected(machine.getMachineUUID() != null)
                .member(MemberResponse.create(machine.getMember()))
                .build();
    }

    public static List<MachineResponse> toMachinesResponse(List<Machine> machines) {
        return machines.stream()
                .map(MachineResponse::create)
                .sorted(Comparator.comparingInt(MachineResponse::getSequence))
                .toList();
    }
}