package jpcompany.smartwire2.dto;

import jpcompany.smartwire2.dto.validator.MemberJoinValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberJoinDto {

    private String loginEmail;
    private String loginPassword;
    private String loginPasswordVerify;
    private String companyName;

    public MemberJoinDto(String loginEmail, String loginPassword, String loginPasswordVerify, String companyName) {
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.loginPasswordVerify = loginPasswordVerify;
        this.companyName = companyName;
    }
}

