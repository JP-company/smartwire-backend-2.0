package jpcompany.smartwire2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.controller.dto.request.validator.JoinValidator;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.service.MemberService;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final JoinValidator joinValidator;
    private final MemberService memberService;

    @Operation(summary = "회원 가입 페이지 요청", description = "화원 가입을 요청합니다.")
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<ResponseDto> join(@Validated @RequestBody MemberJoinDto memberJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorCode errorCode = ErrorCode.INVALID_INPUT;
            return ResponseEntity.status(errorCode.getHttpStatus())
                    .body(new ResponseDto(false, errorCode.getReason(), null));
        }

        MemberJoinCommand memberJoinCommand =
                MemberJoinDto.toMemberJoinCommand(memberJoinDto);
        memberService.join(memberJoinCommand);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, null));
    }

    @GetMapping("/")
    @ResponseBody
    public String get(@AuthenticationPrincipal Member member) {
        return member.toString();
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<ResponseDto> getMemberInfo(
            @AuthenticationPrincipal Member member
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(true, null, member));
    }

    @GetMapping("/email_verify/{authToken}")
    public String emailVerify(@PathVariable String authToken, Model model) {
        memberService.authenticateEmail(authToken); // 인증 예외 발생 - 1. 토큰 검증 실패, 2. DB 업데이트 실패
        model.addAttribute("verified", "인증에 성공하였습니다.");
        return "email/result";
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(joinValidator);
    }
}