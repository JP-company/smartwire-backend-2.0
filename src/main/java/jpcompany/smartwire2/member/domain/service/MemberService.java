package jpcompany.smartwire2.member.domain.service;

import jpcompany.smartwire2.member.application.out.MemberRepository;
import jpcompany.smartwire2.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }
}
