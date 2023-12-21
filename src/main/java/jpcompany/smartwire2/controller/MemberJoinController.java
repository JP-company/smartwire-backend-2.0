package jpcompany.smartwire2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpcompany.smartwire2.controller.response.JoinResponseDto;
import jpcompany.smartwire2.controller.validator.JoinValidator;
import jpcompany.smartwire2.dto.MemberJoinDto;
import jpcompany.smartwire2.vo.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberJoinController {

    private final JoinValidator joinValidator;

    @Operation(summary = "회원 가입 페이지 요청", description = "화원 가입을 요청합니다.")
    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@Validated @RequestBody MemberJoinDto memberJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(badResponseWithErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(JoinResponseDto.of(true, null,null), HttpStatus.OK);
    }

    private JoinResponseDto badResponseWithErrors(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, String> body = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            body.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return JoinResponseDto.of(false, ErrorCode.INVALID_JOIN_FORM.getReason(), body);
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(joinValidator);
    }
}