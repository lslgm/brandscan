package com.comeandsee.brandscan.Service;

import com.comeandsee.brandscan.DTO.RequestDTO;
import com.comeandsee.brandscan.Entity.RequestEntity;
import com.comeandsee.brandscan.Repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class RequestService {
    private final RequestRepository requestRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    //생성자 주입
    @Autowired
    public RequestService(RequestRepository requestRepository, FileService fileService, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.fileService = fileService;
        this.modelMapper = modelMapper;
    }

    @Value("${requestPath}")
    private String requestPath;


    /*public void requestregister(RequestDTO requestDTO)throws  Exception{
        String originalFileName = requestDTO.getImage().getOriginalFilename();
        String newFileName ="";

        if (originalFileName != null){
            newFileName = fileService.uploadFile(requestPath,originalFileName,requestDTO.getImage().getBytes());
        }
        requestDTO.setImgPath(newFileName);
        RequestEntity requestEntity = modelMapper.map(requestDTO,RequestEntity.class);
        requestRepository.save(requestEntity);
    }*/
    public void requestregister(RequestDTO requestDTO,MultipartFile imgFile)throws  Exception{
        String originalFileName = imgFile.getOriginalFilename();
        String newFileName ="";

        if (originalFileName != null){
            newFileName = fileService.uploadFile(
                    requestPath,
                    originalFileName,
                    imgFile.getBytes()
            );
        }
        requestDTO.setImgPath(newFileName);

        RequestEntity requestEntity = modelMapper.map(requestDTO,RequestEntity.class);

        requestRepository.save(requestEntity);
    }
}
