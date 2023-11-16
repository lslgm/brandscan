package com.comeandsee.brandscan.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    //파일 등록 서비스
    public String uploadFile(String uploadPath,String originalFileName,
                             byte[] filedata) throws  Exception{
        UUID uuid = UUID.randomUUID(); //문자열 생성
        String extendsion = originalFileName.substring(
                originalFileName.lastIndexOf("."));//문자열분리

        String saveFileName= uuid.toString()+extendsion;
        String uploadFileUrl = uploadPath+saveFileName;

        //하드디스크에 파일 저장
        FileOutputStream fos = new FileOutputStream(uploadFileUrl);
        fos.write(filedata);
        fos.close();

        return saveFileName; //데이터베이스에 저장할 파일명을 전달
    }

    //파일 삭제
    public void deleteFile(String uploadPath,String fileName) throws  Exception{
        String deleteFileName = uploadPath+fileName;
        File deleteFile = new File(deleteFileName);

        if (deleteFile.exists()) { //파일이 존재하면
            deleteFile.delete();
        }

    }
}
