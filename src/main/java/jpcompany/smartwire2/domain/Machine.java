package jpcompany.smartwire2.domain;

import jakarta.persistence.*;
import jpcompany.smartwire2.domain.validator.MachineValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Machine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String machineName;

    @Column(length = 30)
    private String machineModel;

    private LocalDate dateManufactured;

    private Integer sequence;

    @Column(length = 60)
    private String machineUUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Machine() {}

    public static Machine create(
            Long id,
            String machineName,
            String machineModel,
            LocalDate dateManufactured,
            Integer sequence
    ) {
        machineName = machineName.trim();
        if (machineModel != null) {
            machineModel = machineModel.trim();
        }
        new MachineValidator().validate(
                machineName,
                machineModel,
                dateManufactured
        );
        return Machine.builder()
                .id(id)
                .machineName(machineName)
                .machineModel(machineModel)
                .dateManufactured(dateManufactured)
                .sequence(sequence)
                .build();
    }

    public void edit(Machine machine) {
        this.machineName = machine.getMachineName();
        this.machineModel = machine.getMachineModel();
        this.dateManufactured = machine.getDateManufactured();
        this.sequence = machine.getSequence();
    }

    public void setMachineUUID(String machineUUID) {
        this.machineUUID = machineUUID;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}
