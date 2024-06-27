package jpcompany.smartwire2.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.dto.response.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CustomUnAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCode unauthenticated = ErrorCode.UNAUTHENTICATED;
        response.setStatus(unauthenticated.getHttpStatusValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ResponseDto responseDto = ResponseDto.builder()
                                        .success(false)
                                        .message(unauthenticated.getReason())
                                        .build();
        String json = new ObjectMapper().writeValueAsString(responseDto);

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
