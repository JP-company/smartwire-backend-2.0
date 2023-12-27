package jpcompany.smartwire2.common.errorcode.controller.view.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ErrorCodeController {
    @GetMapping("/error")
    public String errorPage() {
        return "admin/error/page";
    }

    @PostMapping("/error/create")
    public String addError(Model model) {
        return "admin/error/page";
    }
}
