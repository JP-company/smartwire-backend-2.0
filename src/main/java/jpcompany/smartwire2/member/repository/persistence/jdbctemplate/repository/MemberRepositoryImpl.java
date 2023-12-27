package jpcompany.smartwire2.member.repository.persistence.jdbctemplate.repository;

import jpcompany.smartwire2.member.application.out.MemberRepository;
import jpcompany.smartwire2.member.domain.Member;
import jpcompany.smartwire2.member.repository.persistence.jdbctemplate.constant.MemberConstant;
import jpcompany.smartwire2.member.repository.persistence.jdbctemplate.dto.MemberSaveDto;
import jpcompany.smartwire2.member.repository.persistence.jdbctemplate.mapper.MemberMapper;
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
public class MemberRepositoryImpl implements MemberRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final MemberMapper memberMapper;

    public MemberRepositoryImpl(DataSource dataSource, MemberMapper memberMapper) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MemberConstant.TABLE_NAME)
                .usingGeneratedKeyColumns(MemberConstant.ID);
        this.memberMapper = memberMapper;
    }

    @Override
    public void save(Member member) {
        MemberSaveDto memberSaveDto = memberMapper.toMemberSaveDto(member);
        SqlParameterSource param = new BeanPropertySqlParameterSource(memberSaveDto);
        jdbcInsert.execute(param);
    }

    @Override
    public Optional<Member> findByLoginEmail(String email) {
        String sql = "SELECT * " +
                     "FROM members " +
                     "WHERE login_email = :login_email";
        try {
            Map<String, Object> param = Map.of(MemberConstant.LOGIN_EMAIL, email);
            MemberSaveDto memberSaveDto = template.queryForObject(sql, param, MemberSaveDtoRowMapper());
            return Optional.ofNullable(memberMapper.toMemberDomain(memberSaveDto));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<MemberSaveDto> MemberSaveDtoRowMapper() {
        return (rs, rowNum) ->
                MemberSaveDto.builder()
                .id(rs.getLong(MemberConstant.ID))
                .loginEmail(rs.getString(MemberConstant.LOGIN_EMAIL))
                .loginPassword(rs.getString(MemberConstant.LOGIN_PASSWORD))
                .companyName(rs.getString(MemberConstant.COMPANY_NAME))
                .role(rs.getString(MemberConstant.ROLE))
                .createdDateTime(rs.getObject(MemberConstant.CREATED_DATE_TIME, LocalDateTime.class))
                .build();
    }
}
