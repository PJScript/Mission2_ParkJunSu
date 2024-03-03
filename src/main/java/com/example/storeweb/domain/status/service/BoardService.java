package com.example.storeweb.domain.status.service;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.board.repo.BoardRepository;
import com.example.storeweb.domain.status.dto.ProductAddRequest;
import com.example.storeweb.domain.status.entity.StatusEntity;
import com.example.storeweb.domain.status.repo.StatusRepository;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final StatusRepository statusRepository;
    private final TenantRepository tenantRepository;


    // TODO: 이미지를 업로드하고 이미지의 경로를 DB에 기록, 게시글번호를 해당 DB에 기록
    // TODO: 게시글 생성 후 연결된 이미지 정보를 가져옴
    @Transactional
    public UsedItemTradingBoardEntity productAdd(MultipartFile file, ProductAddRequest dto) throws IOException {

        log.info(dto.getTitle() + "title");
        log.info(dto.getDesc() + "desc");
        log.info(dto.getMinAmount() + "min");
        if(file != null){
            log.info(file.getOriginalFilename() + "file");

            try {
                saveFile(file);
                return savePost(dto);

            } catch (Exception e) {
                log.warn(e + "익셉셥");
                // 임시
                throw new CustomException(GlobalSystemStatus.TEST);
            }
        }else{
            return savePost(dto);
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


    public UsedItemTradingBoardEntity savePost(ProductAddRequest dto){


        String TYPE = "BOARD_ITEM";
        String VALUE = "ON_SALE";
        StatusEntity status = statusRepository.findStatusEntityByValueAndType(VALUE,TYPE);
        TenantEntity tenant = tenantRepository.findTenantEntityByUuid(SecurityUtil.getCurrentUserDetails().getUsername())
                .orElseThrow(() -> new CustomException(GlobalSystemStatus.TOKEN_INVALID));
        UsedItemTradingBoardEntity entity = UsedItemTradingBoardEntity.builder()
                .title(dto.getTitle())
                .desc(dto.getDesc())
                .tenant(tenant)
                .minAmount(dto.getMinAmount())
                .status(status)
                .build();

        return boardRepository.save(entity);

    }
}
