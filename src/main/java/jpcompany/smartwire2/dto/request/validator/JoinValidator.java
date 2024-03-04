package jpcompany.smartwire2.dto.request.validator;

import jpcompany.smartwire2.dto.request.MemberJoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinForm memberJoinForm = (MemberJoinForm) target;

        if (!Objects.equals(memberJoinForm.getLoginPassword(), memberJoinForm.getLoginPasswordVerify())) {
            errors.rejectValue("loginPasswordVerify", "InCorrectPassword");
        }
    }
}