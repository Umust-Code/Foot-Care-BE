package com.footcare.footcare.Repository.Member;

import com.footcare.footcare.entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 아이디로 회원 정보 조회
    boolean existsById(String username);

    // 회원 아이디로 회원 정보를 찾는 메서드
    Optional<Member> findById(String id);

    Member findByName(String name);

    // 전체 사용자 수
    @Query("SELECT COUNT(m) FROM Member m WHERE m.memberId <> 1")
    Long countAllMembers();

    // 성별 구분 사용자 수
    @Query("SELECT m.sex, COUNT(m) FROM Member m WHERE m.memberId <> 1 GROUP BY m.sex")
    List<Object[]> countMembersByGender();

    // 월별 신규 가입자 수
    @Query("SELECT FUNCTION('DATE_FORMAT', m.signUpDate, '%Y-%m') AS month, COUNT(m) " +
            "FROM Member m " +
            "WHERE m.signUpDate IS NOT NULL " +
            "GROUP BY FUNCTION('DATE_FORMAT', m.signUpDate, '%Y-%m')")
    List<Object[]> countMonthlySignups();
}