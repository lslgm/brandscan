package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.MemberRole;
import com.comeandsee.brandscan.repository.MemberRepository;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if (memberEntity == null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getMemberRole().toString())
                .build();
    }

    public void saveMember(MemberDTO memberDTO)throws Exception{
        validateDuplicateMember(memberDTO);
        String password = passwordEncoder.encode(memberDTO.getPassword());
        MemberEntity memberEntity = MemberEntity.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(password)
                .tel(memberDTO.getTel())
                .build();
        memberDTO.setState(MemberRole.USER);
        memberRepository.save(memberEntity);
    }

    //이메일중복체크
    private void validateDuplicateMember(MemberDTO memberDTO) {
        //조회
        MemberEntity findMember = memberRepository.findByEmail(memberDTO.getEmail());
        if (findMember != null) { //이미 이메일이 존재하면
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}