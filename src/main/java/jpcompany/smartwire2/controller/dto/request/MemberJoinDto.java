package jpcompany.smartwire2.controller.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberJoinDto {
//    @Email(message = "{Email.loginEmail}")
//    @Size(max = 40, message = "{Size.email}")
    @NotBlank(message = "{NotBlank.loginEmail}")
    private String loginEmail;

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?@$%^&*+=])[a-zA-Z\\d!?@$%^&*+=]{10,20}$", message = "{Pattern.loginPassword}")
    @NotBlank(message = "{NotBlank.loginPassword}")
    private String loginPassword;

    @NotBlank(message = "{NotBlank.loginPasswordVerify}")
    private String loginPasswordVerify;

//    @Size(min = 1, max = 20, message = "{Size.companyName}")
    @NotBlank(message = "{NotBlank.companyName}")
    private String companyName;
}