package jpcompany.smartwire2.unit.repository.jpa;

import jpcompany.smartwire2.domain.Machine;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MachineRepository;
import jpcompany.smartwire2.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MachineRepositoryJPATest {

    @Autowired MachineRepository machineRepository;
    @Autowired MemberRepository memberRepository;

//    @Test
    @DisplayName("기계 목록 조회")
    @Rollback(false)
    public void test() {

        Member member = Member.create(
                "wjsdj2008@gmail.com",
                "Arkskekfk1!",
                "테스트"
        );
        Long memberId = memberRepository.save(member);

        // given
        Machine machine = Machine.create(
                null,
                "1호기",
                null,
                null,
                0
        );
        Machine machine2 = Machine.create(
                null,
                "2호기",
                null,
                null,
                1
        );
        Long save = machineRepository.save(machine, memberId);
        Long save2 = machineRepository.save(machine2, memberId);

        Member findMember = memberRepository.findById(1L).get();

        // given
        List<Machine> allByMemberId = machineRepository.findAllByMemberId(findMember);

        for (Machine machine1 : allByMemberId) {
            System.out.println("machine = " + machine1.getMachineName());
        }
        // when

        // then
    }
    
//    @Test
    @DisplayName("")
    public void test2() {
        Member member = memberRepository.findById(1L).get();

        // given
        List<Machine> allByMemberId = machineRepository.findAllByMemberId(member);

        for (Machine machine : allByMemberId) {
            System.out.println("machine.getMachineName() = " + machine.getMachineName());
        }
        // when
        
        // then
    }

}