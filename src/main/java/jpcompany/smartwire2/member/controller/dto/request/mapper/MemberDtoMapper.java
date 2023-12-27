package jpcompany.smartwire2.member.controller.dto.request.mapper;

import jpcompany.smartwire2.member.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;
import org.springframework.stereotype.Component;

@Component
public class MemberDtoMapper {

    public MemberJoinCommand toCommand(MemberJoinDto memberJoinDto) {
        return MemberJoinCommand.builder()
                .loginEmail(memberJoinDto.getLoginEmail())
                .loginPassword(memberJoinDto.getLoginPassword())
                .companyName(memberJoinDto.getCompanyName())
                .build();
    }
}
