package com.example.storeweb.domain.board.service;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.domain.board.dto.SaveFile;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardImageEntity;
import com.example.storeweb.domain.board.repo.BoardImageRepository;
import com.example.storeweb.domain.board.repo.BoardRepository;
import com.example.storeweb.domain.board.dto.BoardItem;
import com.example.storeweb.domain.status.entity.StatusEntity;
import com.example.storeweb.domain.status.repo.StatusRepository;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final StatusRepository statusRepository;
    private final TenantRepository tenantRepository;
    private final BoardImageRepository boardImageRepository;


    // TODO: 이미지를 업로드하고 이미지의 경로를 DB에 기록, 게시글번호를 해당 DB에 기록
    // TODO: 게시글 생성 후 연결된 이미지 정보를 가져옴
    @Transactional
    public UsedItemTradingBoardEntity productAdd(MultipartFile file, BoardItem dto) throws IOException {
        log.info(dto.getTitle() + "title");
        log.info(dto.getDesc() + "desc");
        log.info(dto.getMinAmount() + "min");
        if (file != null) {
            log.info(file.getOriginalFilename() + "file");

            try {
                SaveFile savedFile = saveFile(file, true);
                UsedItemTradingBoardEntity savedPost = savePost(dto);
                saveFileUrl(savedFile, savedPost);

                return savedPost;

            } catch (Exception e) {
                log.warn(e + "익셉셥");
                // 임시
                throw new CustomException(GlobalSystemStatus.TEST);
            }
        } else {
            return savePost(dto);
        }


    }

    @Transactional
    public UsedItemTradingBoardEntity productUpdate(BoardItem dto, Long id) {

        String currentUserUuid = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsedItemTradingBoardEntity> boardEntity = boardRepository.findById(id);



        if (boardEntity.isEmpty()) {
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        String tenantUuid = boardEntity.get().getTenant().getUuid();
        if (!currentUserUuid.equals(tenantUuid)) {
            log.warn(currentUserUuid + " USER Access" + id + " Board Item");
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }


        log.info(dto.getMinAmount() + "MIN VALUE");
        UsedItemTradingBoardEntity entity = UsedItemTradingBoardEntity.builder()
                .id(boardEntity.get().getId())
                .tenant(boardEntity.get().getTenant())
                .title(dto.getTitle())
                .desc(dto.getDesc())
                .minAmount(dto.getMinAmount())
                .status(boardEntity.get().getStatus())
                .build();


        return boardRepository.save(entity);
    }

    private SaveFile saveFile(MultipartFile file, boolean isThumbnail) throws IOException {
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

        File destFile = new File(directory.getCanonicalPath() + File.separator + newFileName);
        String workingDir = System.getProperty("user.dir");
        String relativePath = destFile.getAbsolutePath().substring(workingDir.length() + 1);

        file.transferTo(destFile);

        return SaveFile.builder()
                .originalFileName(originalFilename)
                .savedFilename(newFileName)
                .savedFilePath(relativePath)
                .isThumbnail(isThumbnail)
                .build();


    }

    private void saveFileUrl(SaveFile dto, UsedItemTradingBoardEntity entity) {

        UsedItemTradingBoardImageEntity boardImageEntity = UsedItemTradingBoardImageEntity.builder()
                .url(dto.getSavedFilePath())
                .board(entity)
                .fileViewName(dto.getSavedFilename())
                .originalFileName(dto.getOriginalFileName())
                .isThumbnail(dto.isThumbnail())
                .size(null)
                .format(null)
                .sort(null)
                .build();
        boardImageRepository.save(boardImageEntity);
    }

    public UsedItemTradingBoardEntity savePost(BoardItem dto) {


        String TYPE = "BOARD_ITEM";
        String VALUE = "ON_SALE";
        StatusEntity status = statusRepository.findStatusEntityByValueAndType(VALUE, TYPE);
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

    public Page<UsedItemTradingBoardEntity> findPost(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


}
