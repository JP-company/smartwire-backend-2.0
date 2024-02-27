package jpcompany.smartwire2.dto.response;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MachineWithMemberResponse {
    private Long id;
    private String machineName;
    private String machineModel;
    private LocalDate dateManufactured;
    private Integer sequence;
    private boolean selected;
    private MemberResponse memberResponse;

    public static MachineWithMemberResponse create(Machine machine, Member member) {
        return MachineWithMemberResponse.builder()
                .id(machine.getId())
                .machineName(machine.getMachineName())
                .machineModel(machine.getMachineModel())
                .dateManufactured(machine.getDateManufactured())
                .sequence(machine.getSequence())
                .selected(machine.getMachineUUID() != null)
                .memberResponse(MemberResponse.create(member))
                .build();
    }

    public static MachineWithMemberResponse create(Machine machine) {
        return MachineWithMemberResponse.builder()
                .id(machine.getId())
                .machineName(machine.getMachineName())
                .machineModel(machine.getMachineModel())
                .dateManufactured(machine.getDateManufactured())
                .sequence(machine.getSequence())
                .selected(machine.getMachineUUID() != null)
                .build();
    }

    public static List<MachineWithMemberResponse> createList(Machines machines) {
        return machines.getMachines().stream()
                .map(MachineWithMemberResponse::create)
                .sorted(Comparator.comparingInt(MachineWithMemberResponse::getSequence))
                .toList();
    }
}