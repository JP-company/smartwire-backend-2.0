package jpcompany.smartwire2.domain;

import jakarta.persistence.*;
import jpcompany.smartwire2.domain.validator.MemberValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true)
    private Long id;

    @Column(length = 60, unique = true)
    private String loginEmail;

    @Column(length = 60)
    private String loginPassword;

    @Column(length = 60)
    private String companyName;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdDateTime;

    public Member(Long id) {
        this.id = id;
    }

    protected Member() {}
    public enum Role {
        ADMIN, MEMBER, EMAIL_UNAUTHORIZED

    }

    public static Member create(String loginEmail, String loginPassword, String companyName) {
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

    public void changeRole(Role role) {
        this.role = role;
    }
}
