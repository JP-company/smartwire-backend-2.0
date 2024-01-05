package jpcompany.smartwire2.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDto {
    private String loginEmail;
    private String loginPassword;
}
