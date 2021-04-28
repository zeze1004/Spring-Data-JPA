package springdatajpa.datajpa.repository;

import springdatajpa.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
