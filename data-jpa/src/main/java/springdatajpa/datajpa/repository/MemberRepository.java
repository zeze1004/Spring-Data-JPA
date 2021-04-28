package springdatajpa.datajpa.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springdatajpa.datajpa.dto.MemberDto;
import springdatajpa.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// extends JpaRepository<엔티티, pk 타입>
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    // 메소드 이름을 분석해서 JPQL 쿼리 실행
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);


    // @Query 를 사용해서 리파지토리 메소드에 쿼리를 직접 정의
    // 동적쿼리에서는 쓸 수 x 동적 쿼리는 QueryDsl이 체고
    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 단순히 값 하나를 조회
    // username을 모두 List에 저장장
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO 직접 조회: new 명령어를 사용해야함
    @Query("select new springdatajpa.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
           "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 파라미어 바인딩: 이름기반(name)을 바인딩하여 조회
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    // 컬렉션 파라미터 바인딩(in절로 여러 개 조회하고 싶을 때 많이 씀)
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // 반환 타입 엄청 많음
    List<Member> findListByUsername(String name);           // 컬렉션(List, Map, Set) => 결과 없어도 빈 컬렉션 반환
    Member findMemberByUsername(String name);               // 단건 => 단건은 결과가 없을 때 null 2개 이상일 때 에러
    Optional<Member> findOptionalByUsername(String name);   // 단건 Optional(null 일수도 있음을 가정한 것이므로 위 코드보다 optional 쓰기)

    // Page 사용
    Page<Member> findByAge(int age, Pageable pageable);
}
