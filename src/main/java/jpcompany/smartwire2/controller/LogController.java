package jpcompany.smartwire2.controller;

import jakarta.validation.Valid;
import jpcompany.smartwire2.dto.response.ResponseDto;
import jpcompany.smartwire2.facade.LogFacade;
import jpcompany.smartwire2.dto.request.LogSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogFacade logFacade;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> receiveRealTimeLog(
            @Valid @RequestBody LogSaveRequest logSaveRequest
    ) {
        logFacade.handleRealTimeLog(logSaveRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .build()
                );
    }
}