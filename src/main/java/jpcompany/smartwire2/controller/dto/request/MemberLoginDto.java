package jpcompany.smartwire2.controller.dto.request;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {
    private String loginEmail;
    private String loginPassword;
}
