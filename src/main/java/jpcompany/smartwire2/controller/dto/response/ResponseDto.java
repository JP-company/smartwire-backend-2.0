package jpcompany.smartwire2.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private boolean success;
    private String message;
    private Object body;
}