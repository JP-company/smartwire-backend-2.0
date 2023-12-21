package jpcompany.smartwire2.vo;

import jakarta.annotation.PostConstruct;
import jpcompany.smartwire2.repository.ErrorCodeDataRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Getter
public enum ErrorCode {

    EMPTY, // 하나하나가 모두 객체 EMPTY() 형태로 생성자를 호출하게 되어 있다.
    // 맴버 변수로 code, reason을 갖는다.
    INVALID_PASSWORD,
    INVALID_COMPANY_NAME,
    INVALID_EMAIL;

    private String reason;

    @RequiredArgsConstructor
    @Component
    public static class ErrorMessageInjector {

        private final ErrorCodeDataRepository repository;
        private final String locale = "ko";

        @PostConstruct
        public void postConstruct() {
            Arrays.stream(ErrorCode.values())
                    .forEach(errorCode -> // EMPTY, INVALID_PASSWORD , ...
                            repository.findByNameAndLocale(errorCode.name(), locale)
                                    .ifPresent(errorCodeDto -> {
                                        errorCode.reason = errorCodeDto.getReason();
                                    })
                    );
        }
    }
}