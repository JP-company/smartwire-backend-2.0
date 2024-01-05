package jpcompany.smartwire2.repository.jdbctemplate.dto;

import jpcompany.smartwire2.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberJoinTransfer {
    private Long id;
    private String loginEmail;
    private String loginPassword;
    private String companyName;
    private Member.Role role;
    private LocalDateTime createdDateTime;
}