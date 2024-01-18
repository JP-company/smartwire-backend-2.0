package jpcompany.smartwire2.controller.dto.request;

import jakarta.validation.constraints.*;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import lombok.Getter;

@Getter
public class MemberJoinDto {
    @NotBlank(message = "{NotBlank.loginEmail}")
    private String loginEmail;

    @NotBlank(message = "{NotBlank.loginPassword}")
    private String loginPassword;

    @NotBlank(message = "{NotBlank.loginPasswordVerify}")
    private String loginPasswordVerify;

    @NotBlank(message = "{NotBlank.companyName}")
    private String companyName;

    public static MemberJoinCommand toMemberJoinCommand(MemberJoinDto memberJoinDto) {
        return MemberJoinCommand.builder()
                .loginEmail(memberJoinDto.getLoginEmail())
                .loginPassword(memberJoinDto.getLoginPassword())
                .companyName(memberJoinDto.getCompanyName())
                .build();
    }
}