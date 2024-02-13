//package jpcompany.smartwire2.integrated;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jpcompany.smartwire2.common.email.EmailService;
//import jpcompany.smartwire2.common.error.ErrorCode;
//import jpcompany.smartwire2.common.jwt.JwtTokenService;
//import lombok.Getter;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class MemberIntegratedTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private JwtTokenService jwtTokenService;
//    @MockBean
//    private EmailService emailService;
//    private final MemberJoinTestDto memberJoinDto = new MemberJoinTestDto();
//
//    @Getter
//    private static class MemberJoinTestDto {
//        private String loginEmail = "wjsdj2008@nate.com";
//        private String loginPassword = "Qweasdzxc1!";
//        private String loginPasswordVerify = "Qweasdzxc1!";
//        private String companyName = "회사이름";
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("회원가입 폼 정상 입력 시 200 OK")
//    void joinFormReceive() throws Exception {
//        // given
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                        .post("/api/v1/join")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(memberJoinDto))
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(status().isOk());
//    }
//
//    @ParameterizedTest
//    @DisplayName("잘못된 이메일 형식 입력 시 400 BAD REQUEST")
//    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.",""})
//    void invalidEmailForm(String email) throws Exception {
//        memberJoinDto.loginEmail = email;
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .post("/api/v1/join")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(memberJoinDto))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @ParameterizedTest
//    @DisplayName("잘못된 비밀번호 형식 입력 시 400 BAD REQUEST")
//    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
//    void invalidPasswordForm(String password) throws Exception {
//        memberJoinDto.loginPassword = password;
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .post("/api/v1/join")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(memberJoinDto))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @DisplayName("비밀번호 확인 불일치 시 400 BAD REQUEST")
//    @ValueSource(strings = {"123", "", " "})
//    void incorrectPasswordVerify(String passwordVerify) throws Exception {
//        memberJoinDto.loginPasswordVerify = passwordVerify;
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .post("/api/v1/join")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(memberJoinDto))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @DisplayName("잘못된 회사 이름 형식 입력 시 400 BAD REQUEST")
//    @ValueSource(strings = {" ", "", "회사이름회사이름회사이름회사이름회사이름회"})
//    void invalidCompanyNameForm(String companyName) throws Exception {
//        memberJoinDto.companyName = companyName;
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .post("/api/v1/join")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(memberJoinDto))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT.getReason()))
//                .andExpect(status().isBadRequest());
//    }
//}