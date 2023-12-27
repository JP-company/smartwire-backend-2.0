package jpcompany.smartwire2.member.repository.persistence.jdbctemplate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class MemberSaveDto {
    private Long id;
    private String loginEmail;
    private String loginPassword;
    private String companyName;
    private String role;
    private LocalDateTime createdDateTime;
}