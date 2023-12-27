package jpcompany.smartwire2.member.controller.dto.request.validator;

import jpcompany.smartwire2.member.controller.dto.request.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:errors.properties")
public class JoinValidator implements Validator {

    private final Environment environment;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinDto memberJoinDto = (MemberJoinDto) target;
        if (!Objects.equals(memberJoinDto.getLoginPassword(), memberJoinDto.getLoginPasswordVerify())) {
            errors.rejectValue("loginPasswordVerify", "InCorrectPassword",
                    environment.getProperty("InCorrectPassword.loginPasswordVerify"));
        }
    }
}