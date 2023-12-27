package jpcompany.smartwire2.member.application.in;

import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;

public interface ModificationMemberService {
    void join(MemberJoinCommand memberJoinCommand);
}
