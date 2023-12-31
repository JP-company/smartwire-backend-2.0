package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.common.error.dto.ErrorCodeDto;
import jpcompany.smartwire2.repository.jdbctemplate.constant.ErrorCodeConstant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class ErrorCodeRepositoryJdbcTemplate {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public ErrorCodeRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(ErrorCodeConstant.TABLE_NAME)
                .usingGeneratedKeyColumns(ErrorCodeConstant.ID);
    }

    public Integer save(ErrorCodeDto errorCodeDto) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(errorCodeDto);
        Number number = jdbcInsert.executeAndReturnKey(param);
        return number.intValue();
    }

    public Optional<ErrorCodeDto> findByNameAndLocale(String name, String locale) {
        String sql = "SELECT reason, http_status " +
                     "FROM errors " +
                     "WHERE name = :name and locale = :locale";

        try {
            Map<String, Object> param = Map.of(ErrorCodeConstant.NAME, name, ErrorCodeConstant.LOCALE, locale);
            ErrorCodeDto errorCodeDto = template.queryForObject(sql, param, ErrorCodeDtoRowMapper());  // 없으면 예외터짐
            return Optional.ofNullable(errorCodeDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<ErrorCodeDto> ErrorCodeDtoRowMapper() {
        return ((rs, rowNum) -> {
            ErrorCodeDto errorCodeDto = new ErrorCodeDto();
            errorCodeDto.setReason(rs.getString(ErrorCodeConstant.REASON));
            errorCodeDto.setHttpStatus(HttpStatus.valueOf(rs.getInt(ErrorCodeConstant.HTTP_STATUS)));
            return errorCodeDto;
        });
    }
}