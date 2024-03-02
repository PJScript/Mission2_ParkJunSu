package com.example.storeweb.domain.status.service;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.status.dto.ProductAddRequest;
import com.example.storeweb.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class BoardService {

    // TODO: 이미지를 업로드하고 이미지의 경로를 DB에 기록, 게시글번호를 해당 DB에 기록
    // TODO: 게시글 생성 후 연결된 이미지 정보를 가져옴
    public void productAdd(MultipartFile file, ProductAddRequest dto) throws IOException {

        log.info(dto.getTitle() + "title");
        log.info(dto.getDesc() + "desc");
        log.info(dto.getMinAmount() + "min");
        if(file != null){
            log.info(file.getOriginalFilename() + "file");

            try {
                saveFile(file);
            } catch (Exception e) {
                // 임시
                throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
            }
        }else{

        }

    }

    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File("media");
        if (!directory.exists()) {
            directory.mkdir();
        }


        long currentTimeMillis = System.currentTimeMillis();

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = originalFilename.substring(0, originalFilename.lastIndexOf("."))
                + "_" + currentTimeMillis + fileExtension;

        File destFile = new File(directory.getAbsolutePath() + File.separator + newFileName);
        String filePath = directory.getAbsolutePath() + File.separator + newFileName;

        file.transferTo(destFile);
        return filePath;
    }
}
