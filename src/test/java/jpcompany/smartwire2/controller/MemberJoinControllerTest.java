package jpcompany.smartwire2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jpcompany.smartwire2.dto.MemberJoinDto;
import jpcompany.smartwire2.vo.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberJoinControllerTest {

    @Autowired
    private MockMvc mvc;
    private final MemberJoinDto memberJoinDto = new MemberJoinDto();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void beforeEach() {
        memberJoinDto.setLoginEmail("wjsdj2008@naver.com");
        memberJoinDto.setLoginPassword("Arkskekfk1!");
        memberJoinDto.setLoginPasswordVerify("Arkskekfk1!");
        memberJoinDto.setCompanyName("SIT");
    }

    @Test
    @DisplayName("회원가입 폼 정상 입력")
    void joinFormReceive() throws Exception {
        mvc
                .perform(
                        MockMvcRequestBuilders
                        .post("/api/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("잘못된 이메일 형식 입력")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.",""})
    void invalidEmailForm(String email) throws Exception {
        memberJoinDto.setLoginEmail(email);
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_JOIN_FORM.getReason()))
                .andExpect(jsonPath("$.body.loginEmail").exists())
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @DisplayName("잘못된 비밀번호 형식 입력")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
    void invalidPasswordForm(String password) throws Exception {
        memberJoinDto.setLoginPassword(password);
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_JOIN_FORM.getReason()))
                .andExpect(jsonPath("$.body.loginPassword").exists())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("비밀번호 확인 불일치")
    @ValueSource(strings = {"123", "", " "})
    void incorrectPasswordVerify(String password) throws Exception {
        memberJoinDto.setLoginPasswordVerify(password);
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_JOIN_FORM.getReason()))
                .andExpect(jsonPath("$.body.loginPasswordVerify").exists())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("잘못된 회사 이름 형식 입력")
    @ValueSource(strings = {" ", "", "회사이름회사이름회사이름회사이름회사이름회"})
    void invalidCompanyNameForm(String companyName) throws Exception {
        memberJoinDto.setCompanyName(companyName);
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_JOIN_FORM.getReason()))
                .andExpect(jsonPath("$.body.companyName").exists())
                .andExpect(status().isBadRequest());
    }
}