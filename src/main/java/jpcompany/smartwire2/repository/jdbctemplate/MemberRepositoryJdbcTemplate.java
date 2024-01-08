package jpcompany.smartwire2.repository.jdbctemplate;

import jpcompany.smartwire2.common.jwt.dto.MemberTokenDto;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstantDB;
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
                .withTableName(MemberConstantDB.TABLE_NAME)
                .usingGeneratedKeyColumns(MemberConstantDB.ID);
    }

    public Long save(MemberJoinTransfer member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.longValue();
    }

    public void updateRoleByMemberTokenDto(Long memberId, Member.Role role) {
        String sql = "UPDATE members " +
                     "SET role=:role " +
                     "WHERE id=:id";
        Map<String, Object> param = Map.of(
                MemberConstantDB.ID, memberId,
                MemberConstantDB.ROLE, role.name()
        );
        template.update(sql, param);
    }

    public Optional<Member> findByLoginEmail(String encodedLoginEmail) {
        String sql = "SELECT * " +
                     "FROM members " +
                     "WHERE login_email = :login_email";
        try {
            Map<String, Object> param = Map.of(MemberConstantDB.LOGIN_EMAIL, encodedLoginEmail);
            Member member = template.queryForObject(sql, param, MemberRowMapper());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT * " +
                "FROM members " +
                "WHERE id = :id";
        try {
            Map<String, Object> param = Map.of(
                    MemberConstantDB.ID, id
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
                        .id(rs.getLong(MemberConstantDB.ID))
                        .loginEmail(rs.getString(MemberConstantDB.LOGIN_EMAIL))
                        .loginPassword(rs.getString(MemberConstantDB.LOGIN_PASSWORD))
                        .companyName(rs.getString(MemberConstantDB.COMPANY_NAME))
                        .role(Member.Role.valueOf(rs.getString(MemberConstantDB.ROLE)))
                        .createdDateTime(rs.getObject(MemberConstantDB.CREATED_DATE_TIME, LocalDateTime.class))
                        .build();
    }
}
