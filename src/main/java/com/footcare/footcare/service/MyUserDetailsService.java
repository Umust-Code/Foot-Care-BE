package com.footcare.footcare.service;

import com.footcare.footcare.entity.Member.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import com.footcare.footcare.Repository.Member.MemberRepository;

import java.util.ArrayList;

public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MyUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // findById로 사용자 조회
        Member member = memberRepository.findById(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // UserDetails 객체 반환
        return new User(member.getId(), member.getPassword(), new ArrayList<>());  // 권한 리스트는 필요에 따라 설정
    }
}
