package jpcompany.smartwire2.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT("유효하지 않은 입력값", HttpStatus.BAD_REQUEST),
    EXIST_MEMBER("이미 존재하는 계정", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private String reason;
    private HttpStatus httpStatus;

    ErrorCode(String reason, HttpStatus httpStatus) {
        this.reason = reason;
        this.httpStatus = httpStatus;
    }
}