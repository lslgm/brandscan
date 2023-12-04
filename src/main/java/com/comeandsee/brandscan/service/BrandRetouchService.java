package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRetouchDTO;
import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.entity.BrandRetouchEntity;
import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.repository.BrandRetouchRepository;
import com.comeandsee.brandscan.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.comeandsee.brandscan.constants.BrandScanConstant.BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE;

@Log4j2
@Service
public class BrandRetouchService {
    private final BrandRetouchRepository brandRetouchRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandRetouchService(BrandRetouchRepository brandRetouchRepository, MemberRepository memberRepository, ModelMapper modelMapper) {
        this.brandRetouchRepository = brandRetouchRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }
    //요청 사항 등록
    public BrandRetouchDTO register(BrandRetouchDTO brandRetouchDTO, String email) throws Exception {
        MemberEntity member = memberRepository.findByEmail(email);
        if (member != null) {
            brandRetouchDTO.setMember(modelMapper.map(member, MemberDTO.class));

            BrandRetouchEntity brandRetouchEntity = modelMapper.map(brandRetouchDTO, BrandRetouchEntity.class);
            BrandRetouchEntity result = brandRetouchRepository.save(brandRetouchEntity);

            return modelMapper.map(result, BrandRetouchDTO.class);
        }
        else {
            throw new Exception(BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE + " : " + email);
        }
    }

    // 수정 요청 정보 목록 조회
    @Transactional(readOnly = true)
    public PageDTO<BrandRetouchDTO> findAllWithPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("id").descending()
        );

        Page<BrandRetouchEntity> results = brandRetouchRepository.findAll(pageRequest);
        List<BrandRetouchDTO> brandRetouches = results.getContent().stream().map(entity -> modelMapper.map(entity, BrandRetouchDTO.class))
                .collect(Collectors.toList());
        PageDTO<BrandRetouchDTO> brandRetouchPage = PageDTO.<BrandRetouchDTO>builder()
                .dataList(brandRetouches)
                .pageNumber(results.getNumber() + 1)
                .pageSize(results.getSize())
                .totalPage(results.getTotalPages())
                .hasPrev(results.hasPrevious())
                .hasNext(results.hasNext())
                .build();

        return brandRetouchPage;
    }

    // 수정 요청 정보 목록 조회 by 로그인 한 사용자
    @Transactional(readOnly = true)
    public PageDTO<BrandRetouchDTO> findAllByMember(String email, Pageable pageable) throws Exception {
        // 사용자 정보 조회
        MemberEntity member = memberRepository.findByEmail(email);
        if (member != null) {
            PageRequest pageRequest = PageRequest.of(
                    pageable.getPageNumber() - 1,
                    pageable.getPageSize(),
                    Sort.by("createdAt").descending()
            );
            Page<BrandRetouchEntity> result = brandRetouchRepository.findAllByMember(member, pageRequest);
            List<BrandRetouchDTO> brandRetouches = result.getContent().stream().map(entity -> modelMapper.map(entity, BrandRetouchDTO.class))
                    .collect(Collectors.toList());
            PageDTO<BrandRetouchDTO> brandRetouchPage = PageDTO.<BrandRetouchDTO>builder()
                    .dataList(brandRetouches)
                    .pageNumber(result.getNumber() + 1)
                    .pageSize(result.getSize())
                    .totalPage(result.getTotalPages())
                    .hasPrev(result.hasPrevious())
                    .hasNext(result.hasNext())
                    .build();

            return brandRetouchPage;

        } else {
            throw new Exception(BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE + " : " + email);
        }
    }

    // 수정 요청 정보 조회
    @Transactional(readOnly = true)
    public BrandRetouchDTO findById(Long id) {
        BrandRetouchEntity result = brandRetouchRepository.findById(id).orElseThrow();
        return modelMapper.map(result, BrandRetouchDTO.class);
    }

    @Transactional
    public BrandRetouchDTO update(BrandRetouchDTO brandRetouchDTO) {
        BrandRetouchEntity entity = modelMapper.map(brandRetouchDTO, BrandRetouchEntity.class);
        BrandRetouchEntity result = brandRetouchRepository.save(entity);

        return modelMapper.map(result, BrandRetouchDTO.class);
    }
}
