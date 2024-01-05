package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstant;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MemberJoinTransfer;
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

    public Long save(MemberJoinTransfer member) {
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

    public Optional<Member> findByLoginEmail(String encodedLoginEmail) {
        String sql = "SELECT * " +
                     "FROM members " +
                     "WHERE login_email = :login_email";
        try {
            Map<String, Object> param = Map.of(MemberConstant.LOGIN_EMAIL, encodedLoginEmail);
            Member member = template.queryForObject(sql, param, MemberRowMapper());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByIdAndLoginEmail(Long id, String loginEmail) {
        String sql = "SELECT * " +
                "FROM members " +
                "WHERE id = :id and login_email = :login_email";
        try {
            Map<String, Object> param = Map.of(
                    MemberConstant.LOGIN_EMAIL, loginEmail,
                    MemberConstant.ID, id
            );
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
                        .role(Member.Role.valueOf(rs.getString(MemberConstant.ROLE)))
                        .createdDateTime(rs.getObject(MemberConstant.CREATED_DATE_TIME, LocalDateTime.class))
                        .build();
    }
}
