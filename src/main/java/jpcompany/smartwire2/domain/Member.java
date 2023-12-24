package jpcompany.smartwire2.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder(toBuilder = true)
public class Member {
    private Long id;
    private String loginEmail;
    private String loginPassword;
    private String companyName;
    private String role;
    private LocalDateTime createdDateTime;
}
