package jpcompany.smartwire2.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinCommand {
    private String loginEmail;
    private String loginPassword;
    private String companyName;
}
