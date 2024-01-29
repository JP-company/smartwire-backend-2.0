package jpcompany.smartwire2.controller;

import jpcompany.smartwire2.controller.dto.request.MachineSetupDto;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.service.MachineService;
import jpcompany.smartwire2.service.dto.MachineSetupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/machines")
public class MachineController {

    private final MachineService machineService;

    @PostMapping("/setup")
    public ResponseEntity<ResponseDto> setupMachines(
            @AuthenticationPrincipal Member member,
            @Validated @RequestBody List<MachineSetupDto> usersMachinesForm
    ) {
        List<MachineSetupCommand> machinesSetupForm = usersMachinesForm.stream()
                .map(MachineSetupDto::toMachineSetupCommand)
                .toList();
        machineService.setup(machinesSetupForm, member.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, null));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> getMachines(
            @AuthenticationPrincipal Member member
    ) {
        Machines machines = machineService.findMachines(member.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, machines));
    }
}