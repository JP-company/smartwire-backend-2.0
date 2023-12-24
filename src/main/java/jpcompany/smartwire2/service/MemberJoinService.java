package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.MemberJoinDto;
import jpcompany.smartwire2.repository.MemberJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberJoinService {

    private final MemberJoinRepository memberJoinRepository;

    public Member join(MemberJoinDto memberJoinDto) {
        Member member = Member.builder()
                .loginEmail(memberJoinDto.getLoginEmail())
                .loginPassword(memberJoinDto.getLoginPassword())
                .companyName(memberJoinDto.getCompanyName())
                .role("UNAUTHORIZED")
                .createdDateTime(LocalDateTime.now().withNano(0))
                .build();
        return memberJoinRepository.save(member);
    }
}
