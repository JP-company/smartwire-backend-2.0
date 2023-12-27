package jpcompany.smartwire2.member.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto {
    private final boolean success;
    private final String message;

    public static ResponseDto of(boolean success, String message) {
        return new ResponseDto(success, message);
    }
}
