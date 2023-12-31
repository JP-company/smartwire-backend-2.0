package jpcompany.smartwire2.common.error;

import jakarta.annotation.PostConstruct;
import jpcompany.smartwire2.repository.jdbctemplate.ErrorCodeRepositoryJdbcTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Getter
public enum ErrorCode {
    INVALID_INPUT,
    EXIST_MEMBER
    ;

    private String reason;
    private HttpStatus httpStatus;

    @RequiredArgsConstructor
    @Component
    public static class ErrorMessageInjector {

        private final ErrorCodeRepositoryJdbcTemplate errorCodeRepositoryJdbcTemplate;
        private final String locale = "ko";

        @PostConstruct
        public void postConstruct() {
            Arrays.stream(ErrorCode.values())
                    .forEach(errorCode -> // EMPTY, INVALID_PASSWORD , ...
                            errorCodeRepositoryJdbcTemplate.findByNameAndLocale(errorCode.name(), locale)
                                    .ifPresent(errorCodeDto -> {
                                        errorCode.httpStatus = errorCodeDto.getHttpStatus();
                                        errorCode.reason = errorCodeDto.getReason();
                                    })
                    );
        }
    }
}