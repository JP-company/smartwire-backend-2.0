package jpcompany.smartwire2.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    UNDEFINED_ERROR("정의되지 않은 에러", INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED("미인증 유저", UNAUTHORIZED),
    ACCESS_DENIED("접근 권한 없음", FORBIDDEN),
    INVALID_INPUT("유효하지 않은 입력값", BAD_REQUEST),
    INVALID_TOKEN("유효하지 않은 토큰", BAD_REQUEST),
    EXIST_MEMBER("이미 존재하는 계정", INTERNAL_SERVER_ERROR),
    INVALID_MEMBER("유효하지 않은 계정", BAD_REQUEST),
    INVALID_MACHINE("유효하지 않은 기계 정보", BAD_REQUEST),
    INVALID_REQUEST("잘못된 요청", BAD_REQUEST),
    UPDATE_FAILED_MACHINE_INFO("기계 정보 업데이트 실패", INTERNAL_SERVER_ERROR),
    UPDATE_FAILED_PROCESS_FINISHED_TIME("작업 종료 시간 설정 실패", INTERNAL_SERVER_ERROR),
    DUPLICATED_MACHINE_NAME("기계 이름 중복", BAD_REQUEST),
    DUPLICATED_MACHINE_SEQUENCE("기계 순서 중복", BAD_REQUEST),
    FILE_IO_EXCEPTION("파일 시스템 접근 실패", INTERNAL_SERVER_ERROR),
    FILE_IO_WRITE_EXCEPTION("파일 시스템 쓰기 실패", INTERNAL_SERVER_ERROR),
    FILE_IO_READ_EXCEPTION("파일 시스템 읽기 실패", INTERNAL_SERVER_ERROR),
    FILE_IO_DELETE_EXCEPTION("파일 시스템 삭제 실패", INTERNAL_SERVER_ERROR),
    JSON_PARSE_FAIL("Json 파싱 실패", INTERNAL_SERVER_ERROR),
    ;

    private final String reason;
    private final HttpStatus httpStatus;

    ErrorCode(String reason, HttpStatus httpStatus) {
        this.reason = reason;
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusValue() {
        return httpStatus.value();
    }
}