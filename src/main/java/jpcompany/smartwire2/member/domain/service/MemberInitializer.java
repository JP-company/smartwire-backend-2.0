package jpcompany.smartwire2.member.domain.service;

import jpcompany.smartwire2.member.domain.Member;
import jpcompany.smartwire2.member.domain.common.Role;
import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberInitializer {
    public Member init(MemberJoinCommand memberJoinCommand) {
        return Member.builder()
                .loginEmail(memberJoinCommand.getLoginEmail())
                .loginPassword(memberJoinCommand.getLoginPassword())
                .companyName(memberJoinCommand.getCompanyName())
                .role(Role.EMAIL_UNAUTHENTICATED)
                .createdDateTime(LocalDateTime.now().withNano(0))
                .build();
    }
}