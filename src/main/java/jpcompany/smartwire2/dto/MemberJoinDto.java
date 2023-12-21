package jpcompany.smartwire2.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberJoinDto {
    @NotBlank(message = "{NotBlank.memberJoinDto.loginEmail}")
    @Email(message = "{Email.memberJoinDto.loginEmail}")
    private String loginEmail;

    @NotBlank(message = "{NotBlank.memberJoinDto.loginPassword}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?@#$%^&*+=/])[a-zA-Z\\d!?@#$%^&*+=/]{10,20}$",
            message = "{Pattern.memberJoinDto.loginPassword}")
    private String loginPassword;

    @NotBlank(message = "{NotBlank.memberJoinDto.loginPasswordVerify}")
    private String loginPasswordVerify;

    @NotBlank(message = "{NotBlank.memberJoinDto.companyName}")
    @Size(min = 1, max = 20, message = "{Size.memberJoinDto.companyName}")
    private String companyName;
}