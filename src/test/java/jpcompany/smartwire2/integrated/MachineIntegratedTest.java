package jpcompany.smartwire2.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import lombok.Builder;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MachineIntegratedTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Getter
    @Builder
    private static class MachineSetupTestDto {
        private Long id;
        private String machineName;
        private String machineModel;
        private LocalDate dateManufactured;
        private int sequence;
    }

    @Test
    @DisplayName("정상 기계 설정 시 200 OK")
    @Transactional
    void test() throws Exception {
        // given
        MachineSetupTestDto machineSetupTestDto1 = MachineSetupTestDto.builder()
                .id(1L)
                .machineName("1호기")
                .machineModel("SCP-096")
                .dateManufactured(LocalDate.now())
                .sequence(0)
                .build();

        MachineSetupTestDto machineSetupTestDto2 = MachineSetupTestDto.builder()
                .machineName("2호기")
                .machineModel("SCP-123")
                .dateManufactured(LocalDate.now())
                .sequence(1)
                .build();
        List<MachineSetupTestDto> machines = new ArrayList<>();
        machines.add(machineSetupTestDto1);
        machines.add(machineSetupTestDto2);

        String loginAuthToken = jwtTokenService.createLoginAuthToken(1L);

        // when
        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/member/machines/setup")
                                .header(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(machines))
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        resultActions
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("없는 계정으로 기계 설정 시 403 Forbidden")
    void test1() throws Exception {
        // given
        MachineSetupTestDto machineSetupTestDto1 = MachineSetupTestDto.builder()
                .id(1L)
                .machineName("1호기")
                .machineModel("SCP-096")
                .dateManufactured(LocalDate.now())
                .sequence(0)
                .build();
        List<MachineSetupTestDto> machines = new ArrayList<>();
        machines.add(machineSetupTestDto1);
        String loginAuthToken = jwtTokenService.createLoginAuthToken(2L);

        // when
        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/member/machines/setup")
                                .header(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(machines))
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.ACCESS_DENIED.getReason()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("없는 기계 id에 기계 설정 변경 시 500 Internal Server Error")
    void test2() throws Exception {
        // given
        MachineSetupTestDto machineSetupTestDto1 = MachineSetupTestDto.builder()
                .id(2L)
                .machineName("1호기")
                .machineModel("SCP-096")
                .dateManufactured(LocalDate.now())
                .sequence(0)
                .build();
        List<MachineSetupTestDto> machines = new ArrayList<>();
        machines.add(machineSetupTestDto1);
        String loginAuthToken = jwtTokenService.createLoginAuthToken(1L);

        // when
        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/member/machines/setup")
                                .header(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(machines))
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("message").value(ErrorCode.UPDATE_FAILED_MACHINE_INFO.getReason()));
    }

    @Test
    @DisplayName("정상 계정으로 기계 조회 시 200 OK")
    void test3() throws Exception{
        // given
        String loginAuthToken = jwtTokenService.createLoginAuthToken(1L);

        // when
        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/member/machines")
                                .header(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthToken)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions
                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("body").)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("없는 계정으로 기계 조회 시 403 Forbidden")
    void test4() throws Exception{
        // given
        String loginAuthToken = jwtTokenService.createLoginAuthToken(2L);

        // when
        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/member/machines")
                                .header(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthToken)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value(ErrorCode.ACCESS_DENIED.getReason()))
                .andExpect(status().isForbidden());
    }
}
