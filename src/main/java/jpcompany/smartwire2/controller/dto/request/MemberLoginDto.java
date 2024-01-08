package jpcompany.smartwire2.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberLoginDto {
    private String loginEmail;
    private String loginPassword;
}
