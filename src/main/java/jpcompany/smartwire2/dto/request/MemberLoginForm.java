package jpcompany.smartwire2.dto.request;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginForm {
    private String loginEmail;
    private String loginPassword;
}
