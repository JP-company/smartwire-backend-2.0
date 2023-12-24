package jpcompany.smartwire2.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinResponseDto {
    private final boolean success;
    private final String message;
    private final Object body;

    public static JoinResponseDto of(boolean success, String message, Object body) {
        return new JoinResponseDto(success, message, body);
    }
}
