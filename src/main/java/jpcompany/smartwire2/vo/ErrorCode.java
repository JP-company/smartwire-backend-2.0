package jpcompany.smartwire2.vo;

import jakarta.annotation.PostConstruct;
import jpcompany.smartwire2.repository.ErrorCodeDataRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Getter
public enum ErrorCode {
    INVALID_JOIN_FORM;

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