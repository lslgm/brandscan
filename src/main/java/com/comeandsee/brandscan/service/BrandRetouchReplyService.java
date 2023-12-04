package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRetouchReplyDTO;
import com.comeandsee.brandscan.entity.BrandRetouchReplyEntity;
import com.comeandsee.brandscan.repository.BrandRetouchReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandRetouchReplyService {
    private final BrandRetouchReplyRepository brandRetouchReplyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandRetouchReplyService(BrandRetouchReplyRepository brandRetouchReplyRepository, ModelMapper modelMapper) {
        this.brandRetouchReplyRepository = brandRetouchReplyRepository;
        this.modelMapper = modelMapper;
    }

    // 답글 작성
    @Transactional
    public BrandRetouchReplyDTO register(BrandRetouchReplyDTO replyDTO) {
        BrandRetouchReplyEntity entity = modelMapper.map(replyDTO, BrandRetouchReplyEntity.class);
        BrandRetouchReplyEntity result = brandRetouchReplyRepository.save(entity);

        return modelMapper.map(result, BrandRetouchReplyDTO.class);
    }
}
