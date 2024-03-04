package jpcompany.smartwire2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MachineConnectionResponse {
    private String machineUUID;
    private Object fileStatus;
}
