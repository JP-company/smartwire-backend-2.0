package jpcompany.smartwire2.controller;

import jpcompany.smartwire2.dto.MemberJoinDto;
import jpcompany.smartwire2.vo.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

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
                       @RequestParam String companyName,
                       Model model) {
        MemberJoinDto memberJoinDto = new MemberJoinDto(loginEmail, loginPassword, loginPasswordVerify, companyName);
        Set<ErrorCode> errorCodes = memberJoinDto.getErrorCodes();

        if (!errorCodes.isEmpty()) {
            model.addAttribute(ConstantAttribute.ERROR_CODES, errorCodes);
            model.addAttribute(ConstantAttribute.MEMBER_JOIN_DTO, memberJoinDto);
            return "join/page";
        }
        return "join/email_verify";
    }
}