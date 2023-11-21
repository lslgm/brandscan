package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.entity.BrandEntity;
import com.comeandsee.brandscan.repository.BrandRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    //생성자 주입
    @Autowired
    public BrandService(BrandRepository brandRepository, ModelMapper modelMapper, FileService fileService){
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }
    //이미지 저장 위치
    /* @Value("${logoloadPath}")
    private String logoloadPath; //로고

    @Value("${buildingloadPath}")
    private String buildingloadPath; //빌딩
    */

    //파일 저장 서비스(임시(오류있음))
    /*public void register(BrandDTO brandDTO, MultipartFile logo,MultipartFile building) throws Exception {
        String originallogoFileName=logo.getOriginalFilename();
        System.out.println(originallogoFileName);
        String originalbuildingFileName = building.getOriginalFilename();

        String newlogoFileName ="";
        String newbuildingFileName="";

        if (originallogoFileName !=null){
            newlogoFileName=fileSerivce.uploadFile(logoloadPath,originallogoFileName,logo.getBytes());
        }

        if (originalbuildingFileName !=null){
            newbuildingFileName=fileSerivce.uploadFile(buildingloadPath,originalbuildingFileName,building.getBytes());
        }

        brandDTO.setLogo(newlogoFileName);
        brandDTO.setBuilding(newbuildingFileName);
        BrandEntity brandEntity = modelMapper.map(brandDTO,BrandEntity.class);
        brandRepository.save(brandEntity);
    }
*/
    //단일 조회
    public BrandDTO findOne(Long id){
        Optional<BrandEntity> brandEntity = brandRepository.findById(id);
        BrandDTO brandDTO =modelMapper.map(brandEntity,BrandDTO.class);
        return  brandDTO;
    }

    //스캔결과 서비스
    public BrandDTO scanresult(String name){
        //초기화
        BrandEntity brandEntity;
        BrandDTO brandDTO = new BrandDTO();

        try{ //오류시 비어있는  BrandDTO를 return
            brandEntity = brandRepository.findByName(name);
            brandDTO =modelMapper.map(brandEntity,BrandDTO.class);
        }catch (IllegalArgumentException illegalArgumentException){
        }
        return  brandDTO;
    }

    /*  관리자 기능 */
    // 브랜드 정보 등록
    public BrandDTO register(BrandDTO brandDTO) {
        BrandEntity entity = modelMapper.map(brandDTO, BrandEntity.class);
        BrandEntity result = brandRepository.save(entity);

        return modelMapper.map(result, BrandDTO.class);
    }

    // 브랜드 전체 목록 조회
    public PageDTO<BrandDTO> findAllWithPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("id").descending()
        );
        Page<BrandEntity> result = brandRepository.findAll(pageRequest);
        List<BrandDTO> brands = result.getContent().stream().map(entity -> modelMapper.map(entity, BrandDTO.class))
                .collect(Collectors.toList());
        PageDTO<BrandDTO> brandPage = PageDTO.<BrandDTO>builder()
                .dataList(brands)
                .pageNumber(result.getNumber() + 1)
                .pageSize(result.getSize())
                .totalPage(result.getTotalPages())
                .hasPrev(result.hasPrevious())
                .hasNext(result.hasNext())
                .build();

        return brandPage;
    }

    // 브랜드 단일 정보 조회
    public BrandDTO findById(Long id) {
        BrandEntity result = brandRepository.findById(id).orElseThrow();
        return modelMapper.map(result, BrandDTO.class);
    }

    // 브랜드 정보 수정
    public BrandDTO updateById(BrandDTO brandDTO) {
        Long id = brandDTO.getId();
        BrandEntity entity = brandRepository.findById(id).orElseThrow();
        entity.update(
                brandDTO.getName(),
                brandDTO.getDescription(),
                brandDTO.getYear(),
                brandDTO.getHomepage(),
                brandDTO.getAddress(),
                brandDTO.getLogo(),
                brandDTO.getBuilding()
        );

        BrandEntity result = brandRepository.save(entity);
        return modelMapper.map(result, BrandDTO.class);
    }
}
