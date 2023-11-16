package com.comeandsee.brandscan.Service;

import com.comeandsee.brandscan.DTO.BrandDTO;
import com.comeandsee.brandscan.Entity.BrandEntity;
import com.comeandsee.brandscan.Repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final FileService fileService;

    //생성자 주입
    @Autowired
    public BrandService(BrandRepository brandRepository, FileService fileService){
        this.brandRepository = brandRepository;
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

}
