package jpcompany.smartwire2.member.domain;

import jpcompany.smartwire2.member.domain.common.Role;
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
    private Role role;
    private LocalDateTime createdDateTime;
}