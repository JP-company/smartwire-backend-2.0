package jpcompany.smartwire2.controller.dto.request.mapper;

import jpcompany.smartwire2.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;

public class MemberCommandMapper {
    public static MemberJoinCommand mapToMemberJoinCommand(MemberJoinDto memberJoinDto) {
        return MemberJoinCommand.builder()
                .loginEmail(memberJoinDto.getLoginEmail())
                .loginPassword(memberJoinDto.getLoginPassword())
                .companyName(memberJoinDto.getCompanyName())
                .build();
    }
}
