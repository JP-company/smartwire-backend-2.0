package jpcompany.smartwire2.dto;

import jpcompany.smartwire2.dto.validator.MemberJoinValidator;
import jpcompany.smartwire2.vo.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Getter
@ToString
public class MemberJoinDto {
    private final Set<ErrorCode> errorCodes;

    private String loginEmail;
    private String loginPassword;
    private String loginPasswordVerify;
    private String companyName;

    public MemberJoinDto() {
        errorCodes = new HashSet<>();
    }

    public MemberJoinDto(String loginEmail, String loginPassword, String loginPasswordVerify, String companyName) {
        MemberJoinValidator memberJoinValidator = new MemberJoinValidator();
        errorCodes = memberJoinValidator.validate(loginEmail, loginPassword, loginPasswordVerify, companyName);

        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.loginPasswordVerify = loginPasswordVerify;
        this.companyName = companyName;
    }
}

