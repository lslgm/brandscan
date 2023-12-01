package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRetouchDTO;
import com.comeandsee.brandscan.entity.BrandRetouchEntity;
import com.comeandsee.brandscan.repository.BrandRetouchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BrandRetouchService {
    private final BrandRetouchRepository brandRetouchRepository;
    private final ModelMapper modelMapper;
    public BrandRetouchService(BrandRetouchRepository brandRetouchRepository, ModelMapper modelMapper) {
        this.brandRetouchRepository = brandRetouchRepository;
        this.modelMapper = modelMapper;
    }
    //요청 사항 등록
    public BrandRetouchDTO register(BrandRetouchDTO brandRetouchDTO){
        BrandRetouchEntity brandRetouchEntity = modelMapper.map(brandRetouchDTO,BrandRetouchEntity.class);
        BrandRetouchEntity result = brandRetouchRepository.save(brandRetouchEntity);
        return modelMapper.map(result,BrandRetouchDTO.class);
    }
}
