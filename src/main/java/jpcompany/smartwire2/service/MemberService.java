package jpcompany.smartwire2.service;

import jpcompany.smartwire2.common.encryptor.OneWayEncryptor;
import jpcompany.smartwire2.common.encryptor.TwoWayEncryptor;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MemberJoinTransfer;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryJdbcTemplate memberRepository;
    private final EmailService emailService;
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
        emailService.sendEmail(memberId, memberJoinCommand.getLoginEmail());
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
                .orElseThrow(() -> new UsernameNotFoundException("없는 이메일 계정"));

        String decryptedLoginEmail = twoWayEncryptor.decrypt(member.getLoginEmail());
        String decryptedCompanyName = twoWayEncryptor.decrypt(member.getCompanyName());
        return member.toBuilder()
                .loginEmail(decryptedLoginEmail)
                .companyName(decryptedCompanyName)
                .build();
    }

    public Member findMember(Long memberId, String encryptedLoginEmail) {
        Member member = memberRepository.findByIdAndLoginEmail(memberId, encryptedLoginEmail)
                .orElseThrow(() -> new IllegalAccessError("유효하지 않은 계정 정보"));

        String decryptedLoginEmail = twoWayEncryptor.decrypt(member.getLoginEmail());
        String decryptedCompanyName = twoWayEncryptor.decrypt(member.getCompanyName());
        return member.toBuilder()
                .loginEmail(decryptedLoginEmail)
                .companyName(decryptedCompanyName)
                .build();
    }

    public void authenticateMember(Long memberId, Member.Role role) {
        memberRepository.updateRoleById(memberId, role);
    }
}