package springdatajpa.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import springdatajpa.datajpa.entity.Member;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember =
                memberRepository.findById(savedMember.getId()).get();
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername())
        ;
        Assertions.assertThat(findMember).isEqualTo(member); // JPA 엔티티 동일성 보장
    }

    @Test
    public void page() throws Exception {

        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        // when
        int age = 10;
        // page는 0부터 시작
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        // 🔼반환 타입이 Page이므로 total count 쿼리도 필요함을 알고 total count 쿼리도 함께 날려서 코드를 안써도 됌

        // then
        List<Member> content = page.getContent();           // 조회된 데이터
        assertThat(content.size()).isEqualTo(3);            // 조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5);   // 전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0);          // 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2);      // 전체 페이지 번호
        assertThat(page.isFirst()).isTrue();                // 첫번째 항목인가?
        assertThat(page.hasNext()).isTrue();                // 다음 페이지가 있는가?
    }

    @Test
    public void callCustom() {
        // 사용자 정의 메서드 호출 코드
        List<Member> result = memberRepository.findMemberCustom();
    }
}
