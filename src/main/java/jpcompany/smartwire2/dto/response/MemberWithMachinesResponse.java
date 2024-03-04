package jpcompany.smartwire2.dto.response;

import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MemberWithMachinesResponse {
    private Long id;
    private String loginEmail;
    private String companyName;
    private LocalDateTime createdDateTime;
    private List<MachineResponse> machines;

    public static MemberWithMachinesResponse create(Member member, Machines machines) {
        return MemberWithMachinesResponse.builder()
                .id(member.getId())
                .loginEmail(member.getLoginEmail())
                .companyName(member.getCompanyName())
                .createdDateTime(member.getCreatedDateTime())
                .machines(MachineResponse.createList(machines))
                .build();
    }
}
