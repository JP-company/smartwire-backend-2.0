package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.repository.jdbctemplate.constant.LogConstantDB;
import jpcompany.smartwire2.repository.jdbctemplate.dto.LogSaveTransfer;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LogRepositoryJdbcTemplate {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public LogRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(LogConstantDB.TABLE_NAME)
                .usingGeneratedKeyColumns(LogConstantDB.ID);
    }

    public Long save(LogSaveTransfer logSaveTransfer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(logSaveTransfer);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }
}
