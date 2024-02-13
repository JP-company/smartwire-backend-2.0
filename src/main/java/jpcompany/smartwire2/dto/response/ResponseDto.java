package jpcompany.smartwire2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
    private boolean success;
    private String message;
    private Object body;
}