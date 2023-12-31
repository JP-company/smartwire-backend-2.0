package jpcompany.smartwire2.domain;

import jpcompany.smartwire2.domain.validator.MemberValidator;
import lombok.*;

import java.time.LocalDateTime;

public record Member(
        Long id,
        String loginEmail,
        String loginPassword,
        String companyName,
        Role role,
        LocalDateTime createdDateTime
) {
    public enum Role {
        EMAIL_UNAUTHORIZED, MEMBER, ADMIN
    }

    public Member {
        new MemberValidator().validate(
                        loginEmail,
                        loginPassword,
                        companyName
                );
    }

    public static Member createMember(String loginEmail, String loginPassword, String companyName) {
        return new Member(
                null,
                loginEmail.trim(),
                loginPassword.trim(),
                companyName.trim(),
                Role.EMAIL_UNAUTHORIZED,
                LocalDateTime.now().withNano(0)
        );
    }
}
