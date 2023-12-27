package jpcompany.smartwire2.member.controller.rest.v1;

import io.swagger.v3.oas.annotations.Operation;
import jpcompany.smartwire2.member.application.in.ModificationMemberService;
import jpcompany.smartwire2.member.controller.dto.request.mapper.MemberDtoMapper;
import jpcompany.smartwire2.member.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.member.controller.dto.request.validator.JoinValidator;
import jpcompany.smartwire2.member.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.member.domain.dto.MemberJoinCommand;
import jpcompany.smartwire2.common.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final JoinValidator joinValidator;
    private final ModificationMemberService modificationMemberService;
    private final MemberDtoMapper memberDtoMapper;

    @Operation(summary = "회원 가입 페이지 요청", description = "화원 가입을 요청합니다.")
    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(
            @Validated @RequestBody MemberJoinDto memberJoinDto,
            BindingResult bindingResult
    ) {
        // Empty, 비밀 번호 확인 검증
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(
                    ResponseDto.of(false, ErrorCode.INVALID_INPUT.getReason()),
                    ErrorCode.INVALID_INPUT.getHttpStatus()
            );
        }

        MemberJoinCommand memberJoinCommand = memberDtoMapper.toCommand(memberJoinDto);
        modificationMemberService.join(memberJoinCommand);

        return new ResponseEntity<>(
                ResponseDto.of(true, null),
                HttpStatus.OK
        );
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(joinValidator);
    }
}