package jpcompany.smartwire2.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MemberJoinController.class)
public class MemberJoinControllerTest {

    @Autowired
    private MockMvc mvc;

    private final MultiValueMap<String, String> joinForm = new LinkedMultiValueMap<>();

    @BeforeEach
    void beforeEach() {
        joinForm.add("loginEmail", "wjsdj2008@gmail.com");
        joinForm.add("loginPassword", "Arkskekfk1!");
        joinForm.add("loginPasswordVerify", "Arkskekfk1!");
        joinForm.add("companyName", "SIT");
    }

    @Test
    @DisplayName("회원가입 폼 정상 입력")
    void joinFormReceive() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/join")
                        .params(joinForm)
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("join/email_verify"));
    }

    @ParameterizedTest
    @DisplayName("잘못된 이메일 형식 입력")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@gmailcom", "wjsdj2008@1", "wjsdj2008@", "@wksd.com", "wjsdj2008@.",""})
    void invalidEmailForm(String email) {
        joinForm.set("loginEmail", email);

        Assertions.assertThatThrownBy(() ->
                        mvc.perform(MockMvcRequestBuilders
                                .post("/join")
                                .params(joinForm)
                                .accept(MediaType.TEXT_HTML)))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[EMAIL_ERROR]");
    }


    @ParameterizedTest
    @DisplayName("잘못된 비밀번호 형식 입력")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", ""})
    void invalidPasswordForm(String password) {
        joinForm.set("loginPassword", password);

        Assertions.assertThatThrownBy(() ->
                        mvc.perform(MockMvcRequestBuilders
                                .post("/join")
                                .params(joinForm)
                                .accept(MediaType.TEXT_HTML)))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[PASSWORD_ERROR]");
    }

    @ParameterizedTest
    @DisplayName("잘못된 회사 이름 형식 입력")
    @ValueSource(strings = {"", "회사이름회사이름회사이름회사이름회사이름회"})
    void invalidCompanyNameForm(String companyName) {
        joinForm.set("companyName", companyName);

        Assertions.assertThatThrownBy(() ->
                        mvc.perform(MockMvcRequestBuilders
                                .post("/join")
                                .params(joinForm)
                                .accept(MediaType.TEXT_HTML)))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[COMPANY_NAME_ERROR]");
    }
}