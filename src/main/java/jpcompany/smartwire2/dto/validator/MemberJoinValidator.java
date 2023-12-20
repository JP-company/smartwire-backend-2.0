package jpcompany.smartwire2.dto.validator;

import jpcompany.smartwire2.vo.ErrorCode;

import java.util.HashSet;
import java.util.Set;

public class MemberJoinValidator {
    public void validate(String loginEmail, String loginPassword, String loginPasswordVerify, String companyName) {
        validateLoginEmail(loginEmail);
        validateLoginPassword(loginPassword, loginPasswordVerify);
        validateCompanyName(companyName);
    }

    private void validateLoginEmail(String loginEmail) {
        validateEmptyEmail(loginEmail);
        validateEmailForm(loginEmail);
    }

    private void validateEmailForm(String loginEmail) {

        if (loginEmail.startsWith(PasswordValidationConstants.AT) || loginEmail.startsWith(PasswordValidationConstants.DOT) ||
                loginEmail.endsWith(PasswordValidationConstants.AT) || loginEmail.endsWith(PasswordValidationConstants.DOT)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_EMAIL.getReason());
        }
        if (!loginEmail.contains(PasswordValidationConstants.AT) ||
                loginEmail.indexOf(PasswordValidationConstants.AT) != loginEmail.lastIndexOf(PasswordValidationConstants.AT)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_EMAIL.getReason());
        }
        if (!loginEmail.split(PasswordValidationConstants.AT)[1].contains(PasswordValidationConstants.DOT)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_EMAIL.getReason());
        }
    }

    private void validateEmptyEmail(String loginEmail) {
        if (loginEmail.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_EMAIL.getReason());
//            [EMAIL_ERROR] 이메일을 입력해 주세요.
        }
    }

    private void validateLoginPassword(String loginPassword, String loginPasswordVerify) {
        validateEmptyPassword(loginPassword);
        validateContainsSpace(loginPassword);
        validateEachPasswordByPasswordPolicy(loginPassword);
        validatePasswordMatches(loginPassword, loginPasswordVerify);
    }

    private void validateEmptyPassword(String loginPassword) {
        if (loginPassword.length() < PasswordValidationConstants.PASSWORD_MINIMUM_LENGTH || loginPassword.length() > PasswordValidationConstants.PASSWORD_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getReason());
//            [PASSWORD_ERROR] 비밀번호는 10자 이상 20자 이하 이어야 합니다.
        }
    }

    private void validateContainsSpace(String loginPassword) {
        if (loginPassword.contains(PasswordValidationConstants.BLANK)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getReason());
//            [PASSWORD_ERROR] 비밀번호에 공백을 입력할 수 없습니다.
        }
    }

    private void validateEachPasswordByPasswordPolicy(String loginPassword) {
        Set<String> typeOfCharactersIncluded = classifyEachPassword(loginPassword);
        if (typeOfCharactersIncluded.size() < PasswordValidationConstants.Ascii.TOTAL_NUMBERS_OF_CHARACTER_TYPE) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getReason());
//            [PASSWORD_ERROR] 비밀번호는 영대소문자, 숫자, 지정된 특수문자를 각각 1개씩 필수적으로 포함헤야 합니다.
        }
    }

    private Set<String> classifyEachPassword(String loginPassword) {
        Set<String> typeOfCharactersIncluded = new HashSet<>();
        for (int i = 0; i < loginPassword.length(); i++) {
            int passwordCharacter = loginPassword.charAt(i);
            validateValidRange(passwordCharacter);

            String CharacterType = PasswordValidationConstants.Ascii.classifyWhichCharacter(passwordCharacter);
            typeOfCharactersIncluded.add(CharacterType);
        }
        return typeOfCharactersIncluded;
    }

    private void validateValidRange(int passwordCharacter) {
        if (PasswordValidationConstants.Ascii.isNotInRange(passwordCharacter)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getReason());
//            [PASSWORD_ERROR] 비밀번호는 영대소문자, 숫자, 지정된 특수문자만 입력 가능 합니다.
        }
    }

    private void validatePasswordMatches(String loginPassword, String loginPasswordVerify) {
        if (!loginPassword.equals(loginPasswordVerify)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getReason());
//            [PASSWORD_ERROR] 비밀번호가 일치하지 않습니다.
        }
    }

    private void validateCompanyName(String companyName) {
        validateEmptyCompanyName(companyName);
        validateMaximumLength(companyName);
    }

    private void validateEmptyCompanyName(String companyName) {
        if (companyName.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_COMPANY_NAME.getReason());
//            [COMPANY_NAME_ERROR] 회사 이름을 입력해 주세요.
        }
    }

    private void validateMaximumLength(String companyName) {
        if (companyName.length() > PasswordValidationConstants.COMPANY_NAME_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(ErrorCode.INVALID_COMPANY_NAME.getReason());
//            [COMPANY_NAME_ERROR] 회사 이름의 최대 길이는 20자 입니다.
        }
    }


}
