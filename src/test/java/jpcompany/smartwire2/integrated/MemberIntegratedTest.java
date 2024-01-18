package jpcompany.smartwire2.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpcompany.smartwire2.common.error.ErrorCode;
import lombok.Getter;
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
public class MemberIntegratedTest {

    @Autowired
    private MockMvc mockMvc;
    private final MemberJoinTestDto memberJoinDto = new MemberJoinTestDto();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private static class MemberJoinTestDto {
        private String loginEmail = "wjsdj2008@naver.com";
        private String loginPassword = "Qweasdzxc1!";
        private String loginPasswordVerify = "Qweasdzxc1!";
        private String companyName = "회사이름";
    }

    @Test
    @DisplayName("회원가입_폼_정상_입력_200_OK")
    void joinFormReceive() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                        .post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("잘못된_이메일_형식_입력_400_BAD_REQUEST")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.",""})
    void invalidEmailForm(String email) throws Exception {
        memberJoinDto.loginEmail = email;
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @DisplayName("잘못된_비밀번호_형식_입력_400_BAD_REQUEST")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
    void invalidPasswordForm(String password) throws Exception {
        memberJoinDto.loginPassword = password;
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("비밀번호_확인_불일치_400_BAD_REQUEST")
    @ValueSource(strings = {"123", "", " "})
    void incorrectPasswordVerify(String passwordVerify) throws Exception {
        memberJoinDto.loginPasswordVerify = passwordVerify;
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("잘못된_회사_이름_형식_입력_400_BAD_REQUEST")
    @ValueSource(strings = {" ", "", "회사이름회사이름회사이름회사이름회사이름회"})
    void invalidCompanyNameForm(String companyName) throws Exception {
        memberJoinDto.companyName = companyName;
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberJoinDto))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
                .andExpect(status().isBadRequest());
    }
}