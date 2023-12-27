package jpcompany.smartwire2.common.errorcode;

import jakarta.annotation.PostConstruct;
import jpcompany.smartwire2.common.errorcode.repository.persisence.jdbctemplate.repository.ErrorCodeDataRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT,
    EXIST_MEMBER
    ;

    private String reason;
    private HttpStatus httpStatus;

    @RequiredArgsConstructor
    @Component
    public static class ErrorMessageInjector {

        private final ErrorCodeDataRepository errorCodeDataRepository;
        private String locale = "ko";

        @PostConstruct
        public void postConstruct() {
            Arrays.stream(ErrorCode.values())
                    .forEach(memberErrorCode ->
                            errorCodeDataRepository.findByNameAndLocale(memberErrorCode.name(), locale)
                                    .ifPresent(errorCodeDto -> {
                                        memberErrorCode.httpStatus = errorCodeDto.getHttpStatus();
                                        memberErrorCode.reason = errorCodeDto.getReason();
                                    })
                    );
        }
    }
}