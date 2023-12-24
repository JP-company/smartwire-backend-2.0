package jpcompany.smartwire2.controller.validator;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.MemberJoinDto;
import jpcompany.smartwire2.repository.MemberJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:errors.properties")
public class JoinValidator implements Validator {

    private final Environment environment;
    private final MemberJoinRepository memberJoinRepository;

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

        if (memberJoinRepository.findByLoginEmail(memberJoinDto.getLoginEmail()).isPresent()) {
            errors.rejectValue("loginEmail","DuplicateEmail",
                    environment.getProperty("DuplicateEmail.loginEmail"));
        }
    }
}