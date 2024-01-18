package jpcompany.smartwire2.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import jpcompany.smartwire2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        Member member = (Member) authentication.getPrincipal();

        addTokenToHeader(response, member);
        setHeader(response);
        setBody(response);
    }

    private void setBody(HttpServletResponse response) throws IOException {
        ResponseDto responseDto = new ResponseDto(true, null, null);
        String responseBody = objectMapper.writeValueAsString(responseDto);

        PrintWriter writer = response.getWriter();
        writer.write(responseBody);
        writer.flush();
    }

    private void setHeader(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
    }

    private void addTokenToHeader(HttpServletResponse response, Member member) {
        String loginAuthJwtToken = jwtTokenService.createLoginAuthToken(member.getId());
        response.addHeader(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthJwtToken);
    }
}
