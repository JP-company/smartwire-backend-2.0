package jpcompany.smartwire2.member.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MemberJoinCommand {
    private String loginEmail;
    private String loginPassword;
    private String companyName;
}
