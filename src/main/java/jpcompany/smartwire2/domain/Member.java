package jpcompany.smartwire2.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Member {
    private Long id;
    private String loginEmail;
    private String loginPassword;
    private String companyName;
    private String role;
    private LocalDateTime createdDateTime;
}
