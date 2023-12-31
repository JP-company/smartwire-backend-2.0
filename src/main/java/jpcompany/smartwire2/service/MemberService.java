package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryJdbcTemplate memberRepository;
    private final EmailService emailService;

    public void join(MemberJoinCommand memberJoinCommand) {
        Member member = Member.createMember(
                memberJoinCommand.getLoginEmail(),
                memberJoinCommand.getLoginPassword(),
                memberJoinCommand.getCompanyName()
        );
        Long memberId = memberRepository.save(member);
        emailService.sendEmail(memberId, member.loginEmail());
    }

    public void authenticateMember(Long memberId, Member.Role role) {
        memberRepository.updateRoleById(memberId, role);
    }
}