package jpcompany.smartwire2.controller;

import jakarta.validation.Valid;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.request.LogSaveRequest;
import jpcompany.smartwire2.dto.response.ResponseDto;
import jpcompany.smartwire2.facade.LogFacade;
import jpcompany.smartwire2.service.vo.LogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogFacade logFacade;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> receiveRealTimeLog(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody LogSaveRequest logSaveRequest
    ) {
        logFacade.handleRealTimeLog(logSaveRequest, member.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .build()
                );
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> getRecentLogs(
            @AuthenticationPrincipal Member member
    ) {
        List<LogVo> recentLogs = logFacade.getRecentLogs(member.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(recentLogs)
                                .build()
                );
    }
}