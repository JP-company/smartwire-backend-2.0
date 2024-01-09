package jpcompany.smartwire2.unit.repository.jdbctemplate;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.repository.jdbctemplate.dto.MemberJoinTransfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class  MemberRepositoryJdbcTemplateTest {

    @Autowired
    private MemberRepositoryJdbcTemplate memberRepositoryJdbcTemplate;

    @Test
    @DisplayName("정상 회원값 저장")
    @Transactional
    void save() {
        // given
        Member member = Member.initMember(
                "wjsdj2008@n123ate.com",
                "Qwertyuiop1!",
                "JP-company"
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
                "wjsdj2008@gmail.com",
                "Qwertyuiop1!",
                "JP-comapny"
        );
        MemberJoinTransfer memberJoinTransfer = MemberJoinTransfer.builder()
                .loginEmail("FBGpx7QTvQ+2vuzMtAvUYT2/UegU7KbhUTlWjnF1xGw=")
                .loginPassword(member.getLoginPassword())
                .companyName(member.getCompanyName())
                .role(member.getRole())
                .createdDateTime(member.getCreatedDateTime())
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> memberRepositoryJdbcTemplate.save(memberJoinTransfer))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("맴버 권한 업데이트")
    @Transactional
    void test3() {
        // when
        memberRepositoryJdbcTemplate.updateRoleByMemberTokenDto(200L, Member.Role.MEMBER);

        // then
        Member member = memberRepositoryJdbcTemplate.findById(200L)
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 계정 정보"));

        Assertions.assertThat(member.getRole()).isEqualTo(Member.Role.MEMBER);
    }

    @Test
    @DisplayName("없는 맴버 권한 업데이트 시 예외 발생")
    void test5() {
        // when, then
        Assertions.assertThatThrownBy(() -> memberRepositoryJdbcTemplate.updateRoleByMemberTokenDto(1234L, Member.Role.MEMBER))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}