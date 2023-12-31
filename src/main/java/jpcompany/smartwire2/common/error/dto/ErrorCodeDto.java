package jpcompany.smartwire2.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorCodeDto {
    private String locale;
    private String name;
    private String reason;
    private HttpStatus httpStatus;
}
