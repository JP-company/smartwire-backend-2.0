package jpcompany.smartwire2.controller;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.request.MachineForm;
import jpcompany.smartwire2.dto.response.MachineConnectionResponse;
import jpcompany.smartwire2.dto.response.MachineWithMemberResponse;
import jpcompany.smartwire2.dto.response.ResponseDto;
import jpcompany.smartwire2.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/machines")
public class MachineController {

    private final MachineService machineService;

    @PostMapping("/setup")
    public ResponseEntity<ResponseDto> setupMachines(
            @AuthenticationPrincipal Member member,
            @Validated @RequestBody List<MachineForm> machinesForm
    ) {
        machineService.setup(machinesForm, member.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .build()
                );
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> getMachines(
            @AuthenticationPrincipal Member member
    ) {
        Machines machines = machineService.findMachines(member.getId());
        List<MachineWithMemberResponse> machinesResponse = MachineWithMemberResponse.createList(machines);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(machinesResponse)
                                .build()
                );
    }

    @PostMapping("/{machineId}/connect")
    public ResponseEntity<ResponseDto> connectWithNewMachine(
            @AuthenticationPrincipal Member member,
            @PathVariable Long machineId
    ) {
        String machineUUID = machineService.connectWithNewMachine(member.getId(), machineId);
        MachineConnectionResponse machineConnectionResponse =
                MachineConnectionResponse.builder()
                        .machineUUID(machineUUID)
                        .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(machineConnectionResponse)
                                .build()
                );
    }

    @GetMapping("/{machineId}/{machineUUID}/connect")
    public ResponseEntity<ResponseDto> connectMachine(
            @AuthenticationPrincipal Member member,
            @PathVariable Long machineId,
            @PathVariable String machineUUID
    ) {
        Machine machine = machineService.checkMachineConnection(member.getId(), machineId, machineUUID);
        MachineWithMemberResponse machineWithMemberResponse = MachineWithMemberResponse.create(machine, member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(machineWithMemberResponse)
                                .build()
                );
    }

}