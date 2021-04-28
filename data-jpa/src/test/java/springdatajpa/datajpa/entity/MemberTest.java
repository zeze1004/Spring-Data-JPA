package springdatajpa.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class MemberTest {
    @PersistenceContext
    EntityManager em;
    @Test
    @Transactional
    @Rollback(false)
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("zeze1", 10, teamA);
        Member member2 = new Member("zeze2", 20, teamA);
        Member member3 = new Member("zeze3", 30, teamB);
        Member member4 = new Member("zeze4", 40, teamB);

        // em.persist하면 영속성 컨텍스트에 쿼리를 저장
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // flush를 통해 강제로 db에 쿼리 날림
        em.flush();
        // 영속성 컨텍스트의 캐시 삭제해서 깔끔하게 테스트 결과 확인할 수 있음
        em.clear();

        // 테스트
        List<Member> members = em.createQuery("select m from Member m",
                Member.class)
                .getResultList();
        for (Member member : members) {
            System.out.println("member=" + member);
            System.out.println("-> member.team=" + member.getTeam());
        }
    }

}
