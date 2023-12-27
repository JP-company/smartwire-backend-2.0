package jpcompany.smartwire2.member.controller.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberJoinDto {

    @NotBlank(message = "{NotBlank.loginEmail}")
    private String loginEmail;

    @NotBlank(message = "{NotBlank.loginPassword}")
    private String loginPassword;

    @NotBlank(message = "{NotBlank.loginPasswordVerify}")
    private String loginPasswordVerify;

    @NotBlank(message = "{NotBlank.companyName}")
    private String companyName;
}