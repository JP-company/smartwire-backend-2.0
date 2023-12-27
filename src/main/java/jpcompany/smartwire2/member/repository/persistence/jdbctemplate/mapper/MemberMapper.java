package jpcompany.smartwire2.member.repository.persistence.jdbctemplate.mapper;

import jpcompany.smartwire2.member.domain.Member;
import jpcompany.smartwire2.member.domain.common.Role;
import jpcompany.smartwire2.member.repository.persistence.jdbctemplate.dto.MemberSaveDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMemberDomain(MemberSaveDto memberSaveDto) {
        return Member.builder()
                .loginEmail(memberSaveDto.getLoginEmail())
                .loginPassword(memberSaveDto.getLoginPassword())
                .companyName(memberSaveDto.getCompanyName())
                .role(Role.valueOf(memberSaveDto.getRole()))
                .createdDateTime(memberSaveDto.getCreatedDateTime())
                .build();
    }

    public MemberSaveDto toMemberSaveDto(Member member) {
        return MemberSaveDto.builder()
                .loginEmail(member.getLoginEmail())
                .loginPassword(member.getLoginPassword())
                .companyName(member.getCompanyName())
                .role(member.getRole().name())
                .createdDateTime(member.getCreatedDateTime())
                .build();
    }
}