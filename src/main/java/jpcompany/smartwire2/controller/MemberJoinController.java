package jpcompany.smartwire2.controller;

import jpcompany.smartwire2.dto.MemberJoinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class MemberJoinController {

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute(ConstantAttribute.MEMBER_JOIN_DTO, new MemberJoinDto());
        return "join/page";
    }

    @PostMapping("/join")
    public String join(@RequestParam String loginEmail,
                       @RequestParam String loginPassword,
                       @RequestParam String loginPasswordVerify,
                       @RequestParam String companyName) {
        MemberJoinDto memberJoinDto = new MemberJoinDto(loginEmail, loginPassword, loginPasswordVerify, companyName);

        return "join/email_verify";
    }
}