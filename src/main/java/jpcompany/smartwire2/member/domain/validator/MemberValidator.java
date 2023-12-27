package jpcompany.smartwire2.member.domain.validator;

import jpcompany.smartwire2.member.application.out.MemberRepository;
import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;
import jpcompany.smartwire2.common.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public void validate(MemberJoinCommand memberJoinCommand) {
        validateLoginEmail(memberJoinCommand.getLoginEmail());
        validateLoginPassword(memberJoinCommand.getLoginPassword());
        validateCompanyName(memberJoinCommand.getCompanyName());
    }

    private void validateLoginEmail(String loginEmail) {
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (!loginEmail.matches(emailRegex)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getReason());
        }
        if (memberRepository.findByLoginEmail(loginEmail).isPresent()) {
            throw new IllegalStateException(ErrorCode.EXIST_MEMBER.getReason());
        }
    }

    private void validateLoginPassword(String loginPassword) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?@$%^&*+=])[a-zA-Z\\d!?@$%^&*+=]{10,20}$";
        if (!loginPassword.matches(passwordRegex)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getReason());
        }
    }

    private void validateCompanyName(String companyName) {
        String companyNameRegex = ".{1,20}";
        if (!companyName.matches(companyNameRegex)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getReason());
        }
    }
}