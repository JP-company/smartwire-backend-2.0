package jpcompany.smartwire2.dto.request;

import jakarta.validation.constraints.*;
import jpcompany.smartwire2.service.dto.MemberJoinDto;
import lombok.Getter;

@Getter
public class MemberJoinForm {
    @NotBlank
    private String loginEmail;

    @NotBlank
    private String loginPassword;

    @NotBlank
    private String loginPasswordVerify;

    @NotBlank
    private String companyName;

    public MemberJoinDto toMemberJoinDto() {
        return MemberJoinDto.create(
                loginEmail,
                loginPassword,
                companyName
        );
    }
}