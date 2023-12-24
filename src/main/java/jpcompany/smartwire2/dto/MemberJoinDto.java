package jpcompany.smartwire2.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberJoinDto {
    @NotBlank(message = "{NotBlank.loginEmail}")
    @Email(message = "{Email.loginEmail}")
    @Size(max = 40, message = "{Size.email}")
    private String loginEmail;

    @NotBlank(message = "{NotBlank.loginPassword}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?@$%^&*+=])[a-zA-Z\\d!?@$%^&*+=]{10,20}$", message = "{Pattern.loginPassword}")
    private String loginPassword;

    @NotBlank(message = "{NotBlank.loginPasswordVerify}")
    private String loginPasswordVerify;

    @NotBlank(message = "{NotBlank.companyName}")
    @Size(min = 1, max = 20, message = "{Size.companyName}")
    private String companyName;
}