package jpcompany.smartwire2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinCommand {
    private String loginEmail;
    private String loginPassword;
    private String companyName;
}
