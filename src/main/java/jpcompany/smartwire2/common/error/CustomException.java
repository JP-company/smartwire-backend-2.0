package jpcompany.smartwire2.common.error;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getReason(), cause);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getReason());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
