package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MemberJoinTransfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

class MemberRepositoryJdbcTemplateTest {

    private final DataSource dataSource = createDataSource();
    public DataSource createDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .setScriptEncoding("UTF-8")
                .continueOnError(true)
                .build();
    }

    private final MemberRepositoryJdbcTemplate memberRepositoryJdbcTemplate =
            new MemberRepositoryJdbcTemplate(dataSource);

    @Test
    @DisplayName("정상 회원값 저장")
    void save() {
        // given
        Member member = Member.initMember(
                "wjsdj2008@naver.com",
                "Qwertyuiop1!",
                "JP-comapny"
        );
        MemberJoinTransfer memberJoinTransfer = MemberJoinTransfer.builder()
                        .loginEmail(member.getLoginEmail())
                        .loginPassword(member.getLoginPassword())
                        .companyName(member.getCompanyName())
                        .role(member.getRole())
                        .createdDateTime(member.getCreatedDateTime())
                        .build();

        // when
        Long memberId = memberRepositoryJdbcTemplate.save(memberJoinTransfer);

        // then
        Assertions.assertThat(memberId).isGreaterThan(0);
    }

    @Test
    @DisplayName("중복 이메일 저장 시 예외 발생")
    void saveDuplicatedEmail() {
        // given
        Member member = Member.initMember(
                "wjsdj2008@naver.com",
                "Qwertyuiop1!",
                "JP-comapny"
        );
        MemberJoinTransfer memberJoinTransfer = MemberJoinTransfer.builder()
                .loginEmail(member.getLoginEmail())
                .loginPassword(member.getLoginPassword())
                .companyName(member.getCompanyName())
                .role(member.getRole())
                .createdDateTime(member.getCreatedDateTime())
                .build();
        memberRepositoryJdbcTemplate.save(memberJoinTransfer);

        // when, then
        Assertions.assertThatThrownBy(() -> memberRepositoryJdbcTemplate.save(memberJoinTransfer))
                .isInstanceOf(DuplicateKeyException.class);
    }
}