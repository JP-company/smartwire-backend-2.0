package jpcompany.smartwire2.dto.response;

import jpcompany.smartwire2.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private Long id;
    private String loginEmail;
    private String companyName;
    private LocalDateTime createdDateTime;

    public static MemberResponse create(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .loginEmail(member.getLoginEmail())
                .companyName(member.getCompanyName())
                .createdDateTime(member.getCreatedDateTime())
                .build();
    }
}
