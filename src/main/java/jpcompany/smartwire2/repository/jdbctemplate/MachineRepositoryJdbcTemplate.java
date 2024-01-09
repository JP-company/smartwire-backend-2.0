package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MachineConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MachineSetupTransfer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MachineRepositoryJdbcTemplate {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public MachineRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MachineConstantDB.TABLE_NAME)
                .usingColumns(
                        MachineConstantDB.MACHINE_NAME,
                        MachineConstantDB.MACHINE_MODEL,
                        MachineConstantDB.DATE_MANUFACTURED,
                        MachineConstantDB.SEQUENCE,
                        MachineConstantDB.MEMBER_ID
                        )
                .usingGeneratedKeyColumns(MachineConstantDB.ID);
    }

    public Long save(MachineSetupTransfer machineSetupTransfer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(machineSetupTransfer);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }

    public void update(MachineSetupTransfer machineSetupTransfer) {
        String sql =
                """
                UPDATE machines
                SET machine_name=:machine_name,
                machine_model=:machine_model,
                date_manufactured=:date_manufactured,
                sequence=:sequence
                WHERE id=:id and member_id=:member_id
                """;

        Map<String, Object> param = new HashMap<>();
        param.put(MachineConstantDB.ID, machineSetupTransfer.getId());
        param.put(MachineConstantDB.MACHINE_NAME, machineSetupTransfer.getMachineName());
        param.put(MachineConstantDB.MACHINE_MODEL, machineSetupTransfer.getMachineModel());
        param.put(MachineConstantDB.DATE_MANUFACTURED, machineSetupTransfer.getDateManufactured());
        param.put(MachineConstantDB.SEQUENCE, machineSetupTransfer.getSequence());
        param.put(MachineConstantDB.MEMBER_ID, machineSetupTransfer.getMemberId());

        int update = template.update(sql, param);
        if (update < 1) {
            throw new UsernameNotFoundException("유효하지 않은 계정 정보");
        }
    }

    public Optional<Machine> findById(Long machineId) {
        String sql =
                """
                SELECT *
                FROM machines
                WHERE id=:id
                """;
        try {
            Map<String, Object> param = Map.of(MachineConstantDB.ID, machineId);
            Machine machine = template.queryForObject(sql, param, MachineRowMapper());
            return Optional.ofNullable(machine);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Machine> MachineRowMapper() {
        return (rs, rowNum) ->
                Machine.builder()
                        .id(rs.getLong(MachineConstantDB.ID))
                        .machineName(rs.getString(MachineConstantDB.MACHINE_NAME))
                        .machineModel(rs.getString(MachineConstantDB.MACHINE_MODEL))
                        .dateManufactured(rs.getObject(MachineConstantDB.DATE_MANUFACTURED, LocalDate.class))
                        .sequence(rs.getInt(MachineConstantDB.SEQUENCE))
                        .selected(rs.getBoolean(MachineConstantDB.SELECTED))
                        .build();
    }
}
