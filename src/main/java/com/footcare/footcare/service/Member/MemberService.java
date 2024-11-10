package com.footcare.footcare.service.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.dto.Member.MemberDTO;
import com.footcare.footcare.entity.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Entity -> DTO 변환
    private MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemberId(member.getMemberId());
        dto.setId(member.getId());
        dto.setPassword(member.getPassword());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setSignUpDate(member.getSignUpDate());
        dto.setStopDate(member.getStopDate());
        dto.setExitDate(member.getExitDate());
        dto.setAddress(member.getAddress());
        return dto;
    }

    // DTO -> Entity 변환
    private Member convertToEntity(MemberDTO dto) {
        Member member = new Member();
        member.setMemberId(dto.getMemberId());
        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());
        member.setPhone(dto.getPhone());
        member.setSignUpDate(dto.getSignUpDate());
        member.setStopDate(dto.getStopDate());
        member.setExitDate(dto.getExitDate());
        member.setAddress(dto.getAddress());
        return member;
    }

    // 회원 생성
    public MemberDTO createMember(MemberDTO memberDTO) {
        // DTO를 Entity로 변환 후 저장
        Member member = convertToEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        // 저장된 Entity를 DTO로 변환하여 반환
        return convertToDTO(savedMember);
    }

    // 모든 회원 조회
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 회원 ID로 특정 회원 조회
    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(this::convertToDTO);
    }

    // 회원 정보 업데이트
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setId(memberDTO.getId());
            member.setPassword(memberDTO.getPassword());
            member.setName(memberDTO.getName());
            member.setPhone(memberDTO.getPhone());
            member.setSignUpDate(memberDTO.getSignUpDate()); // 변경된 날짜 필드 할당
            member.setStopDate(memberDTO.getStopDate());
            member.setExitDate(memberDTO.getExitDate());
            member.setAddress(memberDTO.getAddress());
            Member updatedMember = memberRepository.save(member);
            return convertToDTO(updatedMember);
        } else {
            return null;
        }
    }

    // 회원 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
