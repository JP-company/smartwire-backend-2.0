package jpcompany.smartwire2.service;

import jpcompany.smartwire2.common.email.EmailService;
import jpcompany.smartwire2.common.email.TemplateEngineService;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MemberRepository;
import jpcompany.smartwire2.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jpcompany.smartwire2.common.error.ErrorCode.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;
    private final TemplateEngineService templateEngineService;

    public void join(MemberJoinDto memberJoinDto) {
        Member member = Member.create(
                memberJoinDto.getLoginEmail(),
                memberJoinDto.getLoginPassword(),
                memberJoinDto.getCompanyName()
        );
        Long memberId = memberRepository.save(member);

        String emailAuthToken = jwtTokenService.createEmailAuthToken(memberId);
        String emailContent = templateEngineService.setAuthEmailContext(emailAuthToken);
        emailService.sendEmail(memberJoinDto.getLoginEmail(), "스마트와이어 회원가입 인증 메일", emailContent);
    }

    public Member findMember(String loginEmail) {
        return memberRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new CustomException(INVALID_MEMBER));
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(INVALID_MEMBER));
    }

    public void authenticateEmail(String emailAuthToken) {
        Long memberId = jwtTokenService.extractMemberIdFromEmailAuthToken(emailAuthToken);
        memberRepository.updateRole(memberId, Member.Role.MEMBER);
    }
}