package com.footcare.footcare.Repository;

import com.footcare.footcare.entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 아이디로 회원 정보 조회
    boolean existsById(String username);

    // 회원 아이디로 회원 정보를 찾는 메서드
    Member findById(String id);

    Member findByName(String name);
}