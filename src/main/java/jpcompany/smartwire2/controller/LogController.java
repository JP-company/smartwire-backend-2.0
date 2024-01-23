package jpcompany.smartwire2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jpcompany.smartwire2.facade.dto.LogReceiveDto;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.domain.MachineStatus;
import jpcompany.smartwire2.facade.LogFacade;
import jpcompany.smartwire2.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogFacade logFacade;
    private final LogService logService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> receiveRealTimeLog(
            @Valid @RequestBody LogReceiveDto logReceiveDto
    ) {
        logFacade.handleRealTimeLog(logReceiveDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, null));
    }
    
    @GetMapping("")
    public ResponseEntity<ResponseDto> getRecentLog(
            HttpServletRequest httpServletRequest
//            @RequestParam String requestedMachineIds
    ) {
        String requestedMachineIds = httpServletRequest.getParameter("requestedMachineIds");
        List<Long> machineIds = Arrays.stream(requestedMachineIds.split(","))
                .map(Long::parseLong)
                .toList();

        List<MachineStatus> recentMachinesStatus = logService.getRecentMachinesStatus(machineIds);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, recentMachinesStatus));
    }
}