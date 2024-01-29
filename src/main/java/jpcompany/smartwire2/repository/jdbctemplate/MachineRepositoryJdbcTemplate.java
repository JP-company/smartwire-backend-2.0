package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.error.ErrorCode;
import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MachineConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MachineSetupTransfer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

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

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue(MachineConstantDB.ID, machineSetupTransfer.getId())
                .addValue(MachineConstantDB.MACHINE_NAME, machineSetupTransfer.getMachineName())
                .addValue(MachineConstantDB.MACHINE_MODEL, machineSetupTransfer.getMachineModel())
                .addValue(MachineConstantDB.DATE_MANUFACTURED, machineSetupTransfer.getDateManufactured())
                .addValue(MachineConstantDB.SEQUENCE, machineSetupTransfer.getSequence())
                .addValue(MachineConstantDB.MEMBER_ID, machineSetupTransfer.getMemberId());

        int update = template.update(sql, param);
        if (update < 1) {
            throw new CustomException(ErrorCode.UPDATE_FAILED_MACHINE_INFO);
        }
    }

    public void delete(Long machineId) {
        String sql =
                """
                DELETE FROM machines
                WHERE id=:id
                """;

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue(MachineConstantDB.ID, machineId);
        template.update(sql, param);
    }

    public List<Machine> findAllByMemberId(Long memberId) {
        String sql =
                """
                SELECT *
                FROM machines
                WHERE member_id=:member_id
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue(MachineConstantDB.MEMBER_ID, memberId);
        return template.query(sql, param, machineRowMapper());
    }

    private RowMapper<Machine> machineRowMapper() {
        return (rs, rowNum) ->
                Machine.builder()
                        .id(rs.getLong(MachineConstantDB.ID))
                        .machineName(rs.getString(MachineConstantDB.MACHINE_NAME))
                        .machineModel(rs.getString(MachineConstantDB.MACHINE_MODEL))
                        .dateManufactured(rs.getObject(MachineConstantDB.DATE_MANUFACTURED, LocalDate.class))
                        .sequence(rs.getInt(MachineConstantDB.SEQUENCE))
                        .machineUUID(rs.getString(MachineConstantDB.MACHINE_UUID))
                        .build();
    }
}
