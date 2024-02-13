package jpcompany.smartwire2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Log {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(length = 60)
    private String logName;

    private LocalDateTime logDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    protected Log() {}

    public static Log create(
            String logName,
            LocalDateTime logDateTime,
            Machine machine,
            Process process
    ) {
        return Log.builder()
                .logName(logName)
                .logDateTime(logDateTime)
                .machine(machine)
                .process(process)
                .build();
    }
}
