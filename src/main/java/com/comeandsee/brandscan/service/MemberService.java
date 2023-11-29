package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.custom.CustomMember;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberEntity.getRole().getRole()));

        return new CustomMember(memberEntity, authorities);
    }

    public void saveMember(MemberDTO memberDTO) {
        String password = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(password);
        memberDTO.setRole(MemberRole.USER);

        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberRepository.save(memberEntity);
    }

    //이메일중복체크
    public boolean validateDuplicateMember(String email) {
        //조회
        MemberEntity findMember = memberRepository.findByEmail(email);
        if (findMember != null) { //이미 이메일이 존재하면
            return false;
        }

        return true;
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
