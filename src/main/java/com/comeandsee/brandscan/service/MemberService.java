package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.MemberRole;
import com.comeandsee.brandscan.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_PARAMETER_ERROR_MESSAGE;

@Service
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
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
                .username(memberEntity.getName())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getRole().toString())
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

    /* 관리자 기능 */
    // 역할에 따른 회원 조회
    public PageDTO<MemberDTO> findByRoleWithPage(String role, Pageable pageable) {
        if (role != null) {
            String realRole = "ROLE_" + role.toUpperCase();
            MemberRole memberRole = MemberRole.ofRole(realRole);
            PageRequest pageRequest = PageRequest.of(
                    pageable.getPageNumber() - 1,
                    pageable.getPageSize(),
                    Sort.by("id").descending()
            );

            Page<MemberEntity> result = memberRepository.findByRole(memberRole, pageRequest);
            List<MemberDTO> members = result.getContent().stream().map(entity -> modelMapper.map(entity, MemberDTO.class))
                    .collect(Collectors.toList());
            PageDTO<MemberDTO> memberPage = PageDTO.<MemberDTO>builder()
                    .dataList(members)
                    .pageNumber(result.getNumber() + 1)
                    .pageSize(result.getSize())
                    .totalPage(result.getTotalPages())
                    .hasPrev(result.hasPrevious())
                    .hasNext(result.hasNext())
                    .build();

            return memberPage;
        } else {
            throw new NoSuchElementException(NOT_FOUND_PARAMETER_ERROR_MESSAGE + " : role");
        }
    }
}
