package jpcompany.smartwire2.common.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class TemplateEngineService {

    private final SpringTemplateEngine templateEngine;

    public String setAuthEmailContext(String emailAuthToken) {
        Context context = new Context();
        context.setVariable("emailAuthToken", emailAuthToken);
        return templateEngine.process("email/verification", context);
    }
}
