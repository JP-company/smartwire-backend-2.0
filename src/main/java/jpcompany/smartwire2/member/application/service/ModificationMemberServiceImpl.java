package jpcompany.smartwire2.member.application.service;

import jpcompany.smartwire2.member.application.in.ModificationMemberService;
import jpcompany.smartwire2.member.domain.Member;
import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;
import jpcompany.smartwire2.member.domain.service.MemberInitializer;
import jpcompany.smartwire2.member.domain.service.MemberService;
import jpcompany.smartwire2.member.domain.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModificationMemberServiceImpl implements ModificationMemberService {
    private final MemberValidator memberValidator;
    private final MemberService memberService;
    private final MemberInitializer memberInitializer;

    @Override
    public void join(MemberJoinCommand memberJoinCommand) {
        memberValidator.validate(memberJoinCommand);
        Member member = memberInitializer.init(memberJoinCommand);
        memberService.save(member);
    }
}