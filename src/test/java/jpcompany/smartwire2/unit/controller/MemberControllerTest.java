package jpcompany.smartwire2.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.controller.MemberController;
import jpcompany.smartwire2.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.controller.dto.request.validator.JoinValidator;
import jpcompany.smartwire2.service.MemberService;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MemberController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @SpyBean
    private JoinValidator joinValidator;
    @SpyBean
    private JwtTokenService jwtTokenService;

    private final MemberJoinDto memberJoinDto = new MemberJoinDto();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        memberJoinDto.setLoginEmail("wjsdj2008@naver.com");
        memberJoinDto.setLoginPassword("Qweasdzxc1!");
        memberJoinDto.setLoginPasswordVerify("Qweasdzxc1!");
        memberJoinDto.setCompanyName("SIT");
    }

    @Test
    @DisplayName("회원가입 폼 정상 입력 200 OK")
    void join() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("서비스 예외 발생 처리 400 BAD REQUEST")
    void exceptionHandle() throws Exception {
        // when
        doThrow(new IllegalArgumentException("213")).when(memberService).join(any(MemberJoinCommand.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("빈 이메일 입력값 400 BAD REQUEST")
    @ValueSource(strings = {""," "})
    void invalidEmailForm(String email) throws Exception {
        // given
        memberJoinDto.setLoginEmail(email);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @DisplayName("빈 비밀번호 입력값 400 BAD REQUEST")
    @ValueSource(strings = {"", " "})
    void invalidPasswordForm(String password) throws Exception {
        // given
        memberJoinDto.setLoginPassword(password);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("비밀번호 확인 불일치 400 BAD REQUEST")
    @ValueSource(strings = {"123", "Qweasdzxc1!!", "", " "})
    void incorrectPasswordVerify(String password) throws Exception {
        // given
        memberJoinDto.setLoginPasswordVerify(password);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("빈 회사이름 입력값 400 BAD REQUEST")
    @ValueSource(strings = {" ", ""})
    void invalidCompanyNameForm(String companyName) throws Exception {
        // given
        memberJoinDto.setCompanyName(companyName);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }
}