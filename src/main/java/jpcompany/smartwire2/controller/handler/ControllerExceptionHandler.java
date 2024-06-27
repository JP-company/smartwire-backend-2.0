package jpcompany.smartwire2.controller.handler;

import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .success(false)
                                .message(errorCode.getReason())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .success(false)
                                .message(errorCode.getReason())
                                .build()
                );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .success(false)
                                .message(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        ErrorCode undefinedError = ErrorCode.UNDEFINED_ERROR;

        List<String> errors = new ArrayList<>();
        errors.add(e.toString());

        Throwable cause = e.getCause();
        while (cause != null) {
            errors.add("caused by: " + cause);
            cause = cause.getCause();
        }

        return ResponseEntity
                .status(undefinedError.getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .success(false)
                                .message(undefinedError.getReason())
                                .body(errors)
                                .build()
                );
    }
}