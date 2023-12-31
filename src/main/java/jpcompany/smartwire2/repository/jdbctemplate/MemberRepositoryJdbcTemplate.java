package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstant;
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
public class MemberRepositoryJdbcTemplate {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberRepositoryJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MemberConstant.TABLE_NAME)
                .usingGeneratedKeyColumns(MemberConstant.ID);
    }

    public Long save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }

    public void updateRoleById(Long memberId, Member.Role role) {
        String sql = "UPDATE members " +
                     "SET role=:role " +
                     "WHERE id=:id";
        Map<String, Object> param = Map.of(
                MemberConstant.ID, memberId,
                MemberConstant.ROLE, role.name()
        );
        template.update(sql, param);
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
                new Member(
                        rs.getLong(MemberConstant.ID),
                        rs.getString(MemberConstant.LOGIN_EMAIL),
                        rs.getString(MemberConstant.LOGIN_PASSWORD),
                        rs.getString(MemberConstant.COMPANY_NAME),
                        Member.Role.valueOf(rs.getString(MemberConstant.ROLE)),
                        rs.getObject(MemberConstant.CREATED_DATE_TIME, LocalDateTime.class)
                );
    }
}
