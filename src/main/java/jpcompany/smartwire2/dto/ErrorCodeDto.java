package jpcompany.smartwire2.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErrorCodeDto {
    private String locale;
    private String name;
    private int code;
    private String reason;
}
