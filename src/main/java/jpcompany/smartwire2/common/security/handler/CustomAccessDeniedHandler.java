package jpcompany.smartwire2.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.controller.dto.response.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ErrorCode accessDenied = ErrorCode.ACCESS_DENIED;
        response.setStatus(accessDenied.getHttpStatusValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ResponseDto responseDto = new ResponseDto(false, accessDenied.getReason(), null);
        String responseBody = new ObjectMapper().writeValueAsString(responseDto);

        PrintWriter writer = response.getWriter();
        writer.write(responseBody);
        writer.flush();
    }
}