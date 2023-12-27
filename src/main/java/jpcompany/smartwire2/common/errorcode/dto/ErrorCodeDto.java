package jpcompany.smartwire2.common.errorcode.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorCodeDto {
    private String reason;
    private HttpStatus httpStatus;
}

