package jpcompany.smartwire2.service;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Machines;
import jpcompany.smartwire2.repository.jdbctemplate.MachineRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MachineSetupTransfer;
import jpcompany.smartwire2.service.dto.MachineSetupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MachineService {

    private final MachineRepositoryJdbcTemplate machineRepositoryJdbcTemplate;

    public void save(List<MachineSetupCommand> machinesSetupForm, Long memberId) {

        List<Machine> machinesForm = machinesSetupForm.stream()
                .map(MachineSetupCommand::toMachine)
                .toList();
        Machines machines = new Machines(machinesForm);

        machines.getMachinesForm().stream()
                .map(machine -> MachineSetupTransfer.of(machine, memberId))
                .forEach(machineSetupTransfer -> {
                    if (machineSetupTransfer.getId() != null) {
                        machineRepositoryJdbcTemplate.update(machineSetupTransfer);
                    } else {
                        machineRepositoryJdbcTemplate.save(machineSetupTransfer);
                    }
                });
    }
}
