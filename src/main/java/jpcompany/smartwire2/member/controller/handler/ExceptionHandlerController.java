package jpcompany.smartwire2.member.controller.handler;

import jpcompany.smartwire2.member.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.common.errorcode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                ResponseDto.of(false, e.getMessage()),
                ErrorCode.INVALID_INPUT.getHttpStatus()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDto> handleIllegalStateException(IllegalStateException e) {
        return new ResponseEntity<>(
                ResponseDto.of(false, e.getMessage()),
                ErrorCode.EXIST_MEMBER.getHttpStatus()
        );
    }
}
