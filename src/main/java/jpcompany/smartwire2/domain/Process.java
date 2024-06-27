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
public class Process {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id", unique = true)
    private Long id;

    @Column(length = 60)
    private String fileName;

    private Float thickness;

    private LocalDateTime startedDateTime;

    private LocalDateTime finishedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    protected Process() {}

    public static Process create(
            String fileName,
            Float thickness,
            LocalDateTime startedDateTime,
            Machine machine
    ) {
       return Process.builder()
               .fileName(fileName)
               .thickness(thickness)
               .startedDateTime(startedDateTime)
               .machine(machine)
               .build();
    }

    public void finished(LocalDateTime time) {
        this.finishedDateTime = time;
    }
}