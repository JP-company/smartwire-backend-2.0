package jpcompany.smartwire2.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MemberJoinDto {
    private String loginEmail;
    private String loginPassword;
    private String companyName;

    public static MemberJoinDto create (
            String loginEmail,
            String loginPassword,
            String companyName
    ) {
        return MemberJoinDto.builder()
                .loginEmail(loginEmail)
                .loginPassword(loginPassword)
                .companyName(companyName)
                .build();
    }
}
