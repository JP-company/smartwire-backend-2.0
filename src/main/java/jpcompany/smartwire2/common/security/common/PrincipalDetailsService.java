package jpcompany.smartwire2.common.security.common;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String loginEmail) {

        Member member = memberService.findMember(loginEmail);

        if (member.getRole().equals(Member.Role.EMAIL_UNAUTHORIZED)) {
            throw new IllegalStateException("이메일 미인증 계정");
        }
        return new PrincipalDetails(member);
    }
}