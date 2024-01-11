package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.repository.jdbctemplate.constant.LogConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MachineConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.constant.ProcessConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.ProcessSaveTransfer;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class ProcessRepositoryJdbcTemplate {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public ProcessRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(LogConstantDB.TABLE_NAME)
                .usingGeneratedKeyColumns(LogConstantDB.ID);
    }

    public Long save(ProcessSaveTransfer processSaveTransfer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(processSaveTransfer);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }
}
