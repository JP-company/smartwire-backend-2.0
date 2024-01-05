package jpcompany.smartwire2.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpcompany.smartwire2.controller.dto.request.MemberJoinDto;
import jpcompany.smartwire2.common.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberIntegratedTest {

    @Autowired
    private MockMvc mockMvc;
    private final MemberJoinDto memberJoinDto = new MemberJoinDto();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:schema.sql")
                    .setScriptEncoding("UTF-8")
                    .continueOnError(true)
                    .build();
        }
    }

    @BeforeEach
    void beforeEach() {
        memberJoinDto.setLoginEmail("wjsdj2008@naver.com");
        memberJoinDto.setLoginPassword("Qweasdzxc1!");
        memberJoinDto.setLoginPasswordVerify("Qweasdzxc1!");
        memberJoinDto.setCompanyName("SIT");
    }

    @Test
    @DisplayName("회원가입 폼 정상 입력")
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
    @DisplayName("잘못된 이메일 형식 입력")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.",""})
    void invalidEmailForm(String email) throws Exception {
        memberJoinDto.setLoginEmail(email);
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
    @DisplayName("잘못된 비밀번호 형식 입력")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
    void invalidPasswordForm(String password) throws Exception {
        memberJoinDto.setLoginPassword(password);
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
    @DisplayName("비밀번호 확인 불일치")
    @ValueSource(strings = {"123", "", " "})
    void incorrectPasswordVerify(String password) throws Exception {
        memberJoinDto.setLoginPasswordVerify(password);
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
    @DisplayName("잘못된 회사 이름 형식 입력")
    @ValueSource(strings = {" ", "", "회사이름회사이름회사이름회사이름회사이름회"})
    void invalidCompanyNameForm(String companyName) throws Exception {
        memberJoinDto.setCompanyName(companyName);
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