package jpcompany.smartwire2.controller;

import jpcompany.smartwire2.controller.dto.request.LogReceiveDto;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.service.LogService;
import jpcompany.smartwire2.service.dto.LogSaveCommand;
import jpcompany.smartwire2.service.dto.ProcessSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
public class LogController {

    private final LogService logService;

    @PostMapping("/realtime")
    public ResponseEntity<ResponseDto> receiveRealTimeLog(
            @Validated @RequestBody LogReceiveDto logReceiveDto
    ) {
        LogSaveCommand logSaveCommand = logReceiveDto.toLogSaveCommand();
        ProcessSaveCommand processSaveCommand = logReceiveDto.toProcessSaveCommand();
        logService.saveLog(logSaveCommand, processSaveCommand);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, null));
    }
}
