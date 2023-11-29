package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.entity.BrandRequestEntity;
import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.BrandRequestState;
import com.comeandsee.brandscan.repository.BrandRequestRepository;
import com.comeandsee.brandscan.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.comeandsee.brandscan.constants.BrandScanConstant.BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE;
import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_PARAMETER_ERROR_MESSAGE;

@Service
public class BrandRequestService {
    private final BrandRequestRepository brandRequestRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;

    @Autowired
    public BrandRequestService(
            BrandRequestRepository brandRequestRepository,
            MemberRepository memberRepository,
            ModelMapper modelMapper,
            S3Service s3Service)
    {
        this.brandRequestRepository = brandRequestRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.s3Service = s3Service;
    }

    // 브랜드 요청 목록 조회
    @Transactional(readOnly = true)
    public PageDTO<BrandRequestDTO> findAllWithPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("id").descending()
        );
        Page<BrandRequestEntity> results = brandRequestRepository.findAll(pageRequest);
        List<BrandRequestDTO> brandRequests = results.getContent().stream().map(entity -> modelMapper.map(entity, BrandRequestDTO.class))
                .collect(Collectors.toList());
        PageDTO<BrandRequestDTO> brandRequestPage = PageDTO.<BrandRequestDTO>builder()
                .dataList(brandRequests)
                .pageNumber(results.getNumber() + 1)
                .pageSize(results.getSize())
                .totalPage(results.getTotalPages())
                .hasPrev(results.hasPrevious())
                .hasNext(results.hasNext())
                .build();

        return brandRequestPage;
    }

    // 브랜드 요청 정보 조회
    @Transactional(readOnly = true)
    public BrandRequestDTO findById(Long id) {
        BrandRequestEntity result = brandRequestRepository.findById(id).orElseThrow();
        return modelMapper.map(result, BrandRequestDTO.class);
    }

    // 브랜드 요청 정보 조회 by 로그인 사용자
    @Transactional(readOnly = true)
    public PageDTO<BrandRequestDTO> findAllByMember(String email, Pageable pageable) throws Exception {
        // 사용자 정보 조회
        MemberEntity member = memberRepository.findByEmail(email);
        if (member != null) {
            PageRequest pageRequest = PageRequest.of(
                    pageable.getPageNumber() - 1,
                    pageable.getPageSize(),
                    Sort.by("createdAt").descending()
            );
            Page<BrandRequestEntity> result = brandRequestRepository.findAllByMember(member, pageRequest);
            List<BrandRequestDTO> brandRequests = result.getContent().stream().map(entity -> modelMapper.map(entity, BrandRequestDTO.class))
                    .collect(Collectors.toList());
            PageDTO<BrandRequestDTO> brandRequestPage = PageDTO.<BrandRequestDTO>builder()
                    .dataList(brandRequests)
                    .pageNumber(result.getNumber() + 1)
                    .pageSize(result.getSize())
                    .totalPage(result.getTotalPages())
                    .hasPrev(result.hasPrevious())
                    .hasNext(result.hasNext())
                    .build();

            return brandRequestPage;

        } else {
            throw new Exception(BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE + " : " + email);
        }
    }

    // 브랜드 요청 정보 상태 업데이트
    public BrandRequestDTO saveStateById(Long id, int stateCode) {
        BrandRequestEntity entity = brandRequestRepository.findById(id).orElseThrow();
        entity.update(BrandRequestState.ofCode(stateCode));

        BrandRequestEntity result = brandRequestRepository.save(entity);
        return modelMapper.map(result, BrandRequestDTO.class);
    }

    // 브랜드 요청 정보 등록
    @Transactional
    public BrandRequestDTO register(BrandRequestDTO brandRequestDTO, String email) throws Exception {
        MemberEntity member = memberRepository.findByEmail(email);
        if (member != null) {
            MultipartFile imageFile = brandRequestDTO.getImageFile();
            if (!imageFile.isEmpty()) {
                String uploadUrl = s3Service.uploadFile(imageFile);

                brandRequestDTO.setImagePath(uploadUrl);
                brandRequestDTO.setState(BrandRequestState.REQUEST);
                brandRequestDTO.setMember(member);

                BrandRequestEntity requestEntity = modelMapper.map(brandRequestDTO, BrandRequestEntity.class);
                BrandRequestEntity result = brandRequestRepository.save(requestEntity);

                return modelMapper.map(result, BrandRequestDTO.class);
            } else {
                throw new NoSuchElementException(NOT_FOUND_PARAMETER_ERROR_MESSAGE + " : imageFile");
            }
        }
        else {
            throw new Exception(BRAND_SCAN_NOT_FOUND_USER_ERROR_MESSAGE + " : " + email);
        }
    }
}
