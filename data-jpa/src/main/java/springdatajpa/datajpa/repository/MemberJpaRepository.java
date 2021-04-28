package springdatajpa.datajpa.repository;

import org.springframework.stereotype.Repository;
import springdatajpa.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

// jpa를 통해 crud 만들 수 있음
@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    // member update는 왜 없나요?
    // jpa는 변경감지를 통해서 쿼리 날리기 전에 업데이트 해줘요^^

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    // 전체 조회, 특정 조건을 조회할려면 JPQL 이용해야함
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    
    // 
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // ofNullable: return값이 null일 수도 있음
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    // 하나 조회는 JPA가 제공
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
    // 나이가 더 많은 유저네임
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery(
                "select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    /*  페이징과 정렬렬
   *   검색 조건: 나이가 10살
        정렬 조건: 이름으로 내림차순
        페이징 조건: 첫 번째 페이지, 페이지당 보여줄 데이터는 3건
    * */
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                        .setParameter("age", age)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }
}
