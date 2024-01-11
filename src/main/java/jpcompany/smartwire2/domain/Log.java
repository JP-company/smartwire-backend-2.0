package jpcompany.smartwire2.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Log {
    private Long id;
    private String logName;
    private LocalDateTime logDateTime;
}
