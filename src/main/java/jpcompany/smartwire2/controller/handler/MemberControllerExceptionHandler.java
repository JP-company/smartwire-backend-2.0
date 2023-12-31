package jpcompany.smartwire2.controller.handler;

import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MemberControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ResponseDto(false, errorCode.getReason(), null));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicatedValue(DuplicateKeyException e) {
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = ErrorCode.EXIST_MEMBER;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ResponseDto(false, errorCode.getReason(), null));
    }
}