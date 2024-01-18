package jpcompany.smartwire2.service;

import jpcompany.smartwire2.common.email.EmailService;
import jpcompany.smartwire2.common.email.TemplateEngineService;
import jpcompany.smartwire2.common.encryptor.OneWayEncryptor;
import jpcompany.smartwire2.common.encryptor.TwoWayEncryptor;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MemberJoinTransfer;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static jpcompany.smartwire2.common.error.ErrorCode.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryJdbcTemplate memberRepository;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;
    private final TemplateEngineService templateEngineService;
    private final OneWayEncryptor oneWayEncryptor;
    private final TwoWayEncryptor twoWayEncryptor;

    public void join(MemberJoinCommand memberJoinCommand) {
        Member member = Member.initMember(
                memberJoinCommand.getLoginEmail(),
                memberJoinCommand.getLoginPassword(),
                memberJoinCommand.getCompanyName()
        );
        MemberJoinTransfer encryptedMember = encryptMember(member);
        Long memberId = memberRepository.save(encryptedMember);

        String emailAuthToken = jwtTokenService.createEmailAuthToken(memberId);
        String emailContent = templateEngineService.setAuthEmailContext(emailAuthToken);
        emailService.sendEmail(memberJoinCommand.getLoginEmail(), "스마트와이어 회원가입 인증 메일", emailContent);
    }

    private MemberJoinTransfer encryptMember(Member member) {
        String encryptedLoginEmail = twoWayEncryptor.encrypt(member.getLoginEmail());
        String encryptedPassword = oneWayEncryptor.encrypt(member.getLoginPassword());
        String encryptedCompanyName = twoWayEncryptor.encrypt(member.getCompanyName());

        return MemberJoinTransfer.builder()
                .loginEmail(encryptedLoginEmail)
                .loginPassword(encryptedPassword)
                .companyName(encryptedCompanyName)
                .role(member.getRole())
                .createdDateTime(member.getCreatedDateTime())
                .build();
    }

    public Member findMember(String loginEmail) {
        String encryptedLoginEmail = twoWayEncryptor.encrypt(loginEmail);
        Member member = memberRepository.findByLoginEmail(encryptedLoginEmail)
                .orElseThrow(() -> new CustomException(INVALID_MEMBER));

        String decryptedLoginEmail = twoWayEncryptor.decrypt(member.getLoginEmail());
        String decryptedCompanyName = twoWayEncryptor.decrypt(member.getCompanyName());
        return member.toBuilder()
                .loginEmail(decryptedLoginEmail)
                .companyName(decryptedCompanyName)
                .build();
    }

    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(INVALID_MEMBER));

        String decryptedLoginEmail = twoWayEncryptor.decrypt(member.getLoginEmail());
        String decryptedCompanyName = twoWayEncryptor.decrypt(member.getCompanyName());
        return member.toBuilder()
                .loginEmail(decryptedLoginEmail)
                .companyName(decryptedCompanyName)
                .build();
    }

    public void authenticateEmail(String emailAuthToken) {
        Long memberId = jwtTokenService.extractMemberIdFromEmailAuthToken(emailAuthToken);
        memberRepository.updateRoleByMemberTokenDto(memberId, Member.Role.MEMBER);
    }
}