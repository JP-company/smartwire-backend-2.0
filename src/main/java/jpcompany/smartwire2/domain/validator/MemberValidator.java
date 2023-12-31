package jpcompany.smartwire2.domain.validator;

import jpcompany.smartwire2.common.error.ErrorCode;

public class MemberValidator {

    public void validate(String loginEmail, String loginPassword, String companyName) {
        validateLoginEmail(loginEmail);
        validateLoginPassword(loginPassword);
        validateCompanyName(companyName);
    }

    private void validateLoginEmail(String loginEmail) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,40}$";
        if (!loginEmail.matches(emailRegex)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getReason());
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
