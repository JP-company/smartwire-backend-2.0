package jpcompany.smartwire2.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberTokenDto {
    private Long id;
    private String loginEmail;
}
