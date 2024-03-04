package jpcompany.smartwire2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.dto.request.MemberJoinForm;
import jpcompany.smartwire2.dto.request.validator.JoinValidator;
import jpcompany.smartwire2.dto.response.MemberResponse;
import jpcompany.smartwire2.dto.response.MemberWithMachinesResponse;
import jpcompany.smartwire2.dto.response.ResponseDto;
import jpcompany.smartwire2.service.MachineService;
import jpcompany.smartwire2.service.MemberService;
import jpcompany.smartwire2.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1")
public class MemberController {

    private final JoinValidator joinValidator;
    private final MemberService memberService;
    private final MachineService machineService;

    @Operation(summary = "회원 가입 요청", description = "화원 가입을 요청받는 API 입니다.")
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<ResponseDto> join(
            @Validated @RequestBody MemberJoinForm memberJoinForm
    ) {
        MemberJoinDto memberJoinDto = memberJoinForm.toMemberJoinDto();
        memberService.join(memberJoinDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .build()
                );
    }

    @GetMapping("/member")
    public ResponseEntity<ResponseDto> memberInfo(
            @AuthenticationPrincipal Member member
    ) {
        MemberResponse memberResponse = MemberResponse.create(member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(memberResponse)
                                .build()
                );
    }

    @GetMapping("/member-with-machines")
    public ResponseEntity<ResponseDto> memberWithMachines(
            @AuthenticationPrincipal Member member
    ) {
        Machines machines = machineService.findMachines(member.getId());
        MemberWithMachinesResponse memberWithMachinesResponse = MemberWithMachinesResponse.create(member, machines);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .success(true)
                                .body(memberWithMachinesResponse)
                                .build()
                );

    }

    @GetMapping("/email_verify/{authToken}")
    public String emailVerify(@PathVariable String authToken, Model model) {
        memberService.authenticateEmail(authToken);
        model.addAttribute("verified", "인증에 성공하였습니다.");
        return "email/result";
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(joinValidator);
    }
}