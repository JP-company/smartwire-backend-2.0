package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.constant.MemberConstant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberJoinRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberJoinRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MemberConstant.TABLE_NAME)
                .usingGeneratedKeyColumns(MemberConstant.ID);
    }

    public Member save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return member.toBuilder()
                .id(key.longValue())
                .build();
    }

    public Optional<Member> findByLoginEmail(String email) {
        String sql = "SELECT * " +
                     "FROM members " +
                     "WHERE login_email = :login_email";
        try {
            Map<String, Object> param = Map.of(MemberConstant.LOGIN_EMAIL, email);
            Member member = template.queryForObject(sql, param, MemberRowMapper());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> MemberRowMapper() {
        return (rs, rowNum) ->
                Member.builder()
                .id(rs.getLong(MemberConstant.ID))
                .loginEmail(rs.getString(MemberConstant.LOGIN_EMAIL))
                .loginPassword(rs.getString(MemberConstant.LOGIN_PASSWORD))
                .companyName(rs.getString(MemberConstant.COMPANY_NAME))
                .role(rs.getString(MemberConstant.ROLE))
                .createdDateTime(rs.getObject(MemberConstant.CREATED_DATE_TIME, LocalDateTime.class))
                .build();
    }
}
