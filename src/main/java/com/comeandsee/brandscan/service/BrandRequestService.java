package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.entity.BrandRequestEntity;
import com.comeandsee.brandscan.enums.BrandRequestState;
import com.comeandsee.brandscan.repository.BrandRequestRepository;
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

import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_PARAMETER_ERROR_MESSAGE;

@Service
public class BrandRequestService {
    private final BrandRequestRepository brandRequestRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;

    @Autowired
    public BrandRequestService(BrandRequestRepository brandRequestRepository, ModelMapper modelMapper, S3Service s3Service) {
        this.brandRequestRepository = brandRequestRepository;
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

    // 브랜드 요청 정보 상태 업데이트
    public BrandRequestDTO saveStateById(Long id, int stateCode) {
        BrandRequestEntity entity = brandRequestRepository.findById(id).orElseThrow();
        entity.update(BrandRequestState.ofCode(stateCode));

        BrandRequestEntity result = brandRequestRepository.save(entity);
        return modelMapper.map(result, BrandRequestDTO.class);
    }

    // 브랜드 요청 정보 등록
    @Transactional
    public BrandRequestDTO register(BrandRequestDTO brandRequestDTO, MultipartFile imageFile) throws Exception {
        if (!imageFile.isEmpty()) {
            String uploadUrl = s3Service.uploadFile(imageFile);

            brandRequestDTO.setImagePath(uploadUrl);
            brandRequestDTO.setState(BrandRequestState.REQUEST);

            BrandRequestEntity requestEntity = modelMapper.map(brandRequestDTO, BrandRequestEntity.class);
            BrandRequestEntity result = brandRequestRepository.save(requestEntity);

            return modelMapper.map(result, BrandRequestDTO.class);
        } else {
            throw new NoSuchElementException(NOT_FOUND_PARAMETER_ERROR_MESSAGE + " : imageFile");
        }
    }
}
