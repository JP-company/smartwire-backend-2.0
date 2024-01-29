package jpcompany.smartwire2.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Process {
    private Long id;
    private String fileName;
    private Float thickness;
    private LocalDateTime startedDateTime;
    private LocalDateTime finishedDateTime;
    private List<Log> logs;
}