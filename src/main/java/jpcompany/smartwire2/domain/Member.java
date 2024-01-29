package jpcompany.smartwire2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpcompany.smartwire2.domain.validator.MemberValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder(toBuilder = true)
public class Member {
    private Long id;
    private String loginEmail;
    @JsonIgnore private String loginPassword;
    private String companyName;
    @JsonIgnore private Role role;
    private LocalDateTime createdDateTime;
    private Machines machines;

    public enum Role {
        ADMIN, MEMBER, EMAIL_UNAUTHORIZED
    }

    public static Member initMember(String loginEmail, String loginPassword, String companyName) {
        new MemberValidator().validate(
                loginEmail,
                loginPassword,
                companyName
        );
        return Member.builder()
                .loginEmail(loginEmail)
                .loginPassword(loginPassword)
                .companyName(companyName)
                .role(Role.EMAIL_UNAUTHORIZED)
                .createdDateTime(LocalDateTime.now().withNano(0))
                .build();
    }
}
