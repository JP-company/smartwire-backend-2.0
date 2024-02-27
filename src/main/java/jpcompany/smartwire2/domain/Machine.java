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
    @Column(name = "machine_id", unique = true)
    private Long id;

    @Column(length = 30, nullable = false)
    private String machineName;

    @Column(length = 30)
    private String machineModel;

    private LocalDate dateManufactured;

    private Integer sequence;

    @Column(name = "machine_uuid", length = 60)
    private String machineUUID;

    private Long memberId;

    protected Machine() {}

    public static Machine create(
            Long id,
            String machineName,
            String machineModel,
            LocalDate dateManufactured,
            Integer sequence,
            Long memberId
    ) {
        machineName = machineName.trim();
        machineModel = machineModel != null ? machineModel.trim() : null;
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
                .memberId(memberId)
                .build();
    }

    public static Machine create(
            String machineName,
            String machineModel,
            LocalDate dateManufactured,
            Integer sequence,
            Long memberId
    ) {
        return create(
                null,
                machineName,
                machineModel,
                dateManufactured,
                sequence,
                memberId
        );
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

}
