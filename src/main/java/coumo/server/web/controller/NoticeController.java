package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.converter.NoticeConverter;
import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.domain.enums.NoticeType;
import coumo.server.service.notice.NoticeService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.validation.annotation.ExistNotice;
import coumo.server.validation.annotation.ExistOwner;
import coumo.server.validation.annotation.CheckPage;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final StoreQueryService storeQueryService;

    @Operation(summary = "사장님 : 동네소식 글 쓰기")
    @PostMapping(value = "/{ownerId}/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeType", description = "게시글 종류, NEW_PRODUCT/EVENT/NO_SHOW"),
            @Parameter(name = "title", description = "게시글 제목"),
            @Parameter(name = "noticeContent", description = "게시글 내용"),
            @Parameter(name = "noticeImages", description = "게시글 사진 (최대 5개)")
    })
    public ApiResponse<?> postNotice(
            @PathVariable("ownerId") Long ownerId,
            @RequestPart("noticeType") String noticeType,
            @RequestPart("title") String title,
            @RequestPart("noticeContent") String noticeContent,
            @RequestPart("noticeImages") MultipartFile[] noticeImages
    ){
        if(! (noticeType.equals("NEW_PRODUCT") ||  noticeType.equals("EVENT") || noticeType.equals("NO_SHOW")))
            return ApiResponse.onFailure("400", "올바른 게시글 종류가 아닙니다.", noticeType);
        NoticeType noticeType1 = NoticeType.valueOf(noticeType);
        Notice notice = noticeService.postNotice(ownerId, noticeType1, title, noticeContent, noticeImages);  // 글 저장

        return ApiResponse.onSuccess(notice.getId());
    }

    @Operation(summary = "사장님 : 내가 쓴 글 리스트 보기 (페이징=10)")
    @GetMapping("/{ownerId}/list")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> readNotice(@PathVariable("ownerId") Long ownerId, @RequestParam(name="pageId")Integer pageId) {

        Pageable pageable = PageRequest.of((int) (pageId - 1), 10); // 페이지 크기 = 10

        Optional<Store> store = storeQueryService.findStore(ownerId);  // store 존재 여부

        Page<Notice> noticePage = noticeService.findOwnerNotice(store.get(), pageable);

        List<NoticeResponseDTO.NoticeThumbInfo> noticeThumbInfos = noticePage.getContent().stream()
                .map(NoticeConverter::toNoticeThumbInfo)
                .collect(Collectors.toList());

        NoticeResponseDTO.OwnerNoticeList ownerNoticeList = NoticeResponseDTO.OwnerNoticeList.builder()
                .total(noticeThumbInfos.size())
                .notice(noticeThumbInfos)
                .build();

        return ApiResponse.onSuccess(ownerNoticeList);
    }

    @Operation(summary = "사장님 : 내가 쓴 동네소식 글 자세히 보기 & 수정할 때")
    @GetMapping("/{ownerId}/detail/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> readNoticeDetail(@ExistOwner @PathVariable("ownerId") Long ownerId, @ExistNotice @PathVariable("noticeId") Long noticeId){

        if(noticeService.findNotice(noticeId).isEmpty()) return ApiResponse.onFailure("400", "게시물이 존재하지 않습니다.", noticeId);
        NoticeResponseDTO.OwnerNoticeDetail ownerNoticeDetail = noticeService.readNoticeDetail(noticeId);   // 내가 쓴 글 세부내용 보기
        return ApiResponse.onSuccess(ownerNoticeDetail);
    }

    @Operation(summary = "사장님 : 동네소식 글 수정 완료 버튼")
    @PutMapping(value = "/{ownerId}/update/{noticeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> updateNotice(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("noticeId") Long noticeId,
            @RequestPart("noticeType") String noticeType,
            @RequestPart("title") String title,
            @RequestPart("noticeContent") String noticeContent,
            @RequestPart("noticeImages") MultipartFile[] noticeImages
    ){

        noticeService.updateNotice(noticeId, noticeType, title, noticeContent, noticeImages);  // 수정
        return ApiResponse.onSuccess(noticeId);
    }

    @Operation(summary = "사장님 : 내가 쓴 동네소식 글 삭제")
    @PatchMapping("/{ownerId}/delete/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> deleteNotice(@PathVariable("ownerId") Long ownerId, @PathVariable("noticeId") Long noticeId){

        noticeService.deleteNotice(noticeId);       // 글 삭제
        return ApiResponse.onSuccess(noticeId);
    }

    // 동네소식 - 필터링 4가지
    @Operation(summary = "고객 : 내 주변 동네 소식 (페이징=10)")
    @GetMapping("/around/list/{pageId}")
    public ApiResponse<?> aroundNotice(
            @RequestParam("longtitude") Double longitude,
            @RequestParam("latitde") Double latitude,
            @RequestParam("type")NoticeType noticeType,
            @CheckPage @RequestParam(name="pageId") Integer pageId){

        if (longitude < -180|| longitude > 180 || latitude > 90 || latitude < -90)  throw new StoreHandler(ErrorStatus.STORE_POINT_BAD_REQUEST);
        Pageable pageable = PageRequest.of((int) (pageId - 1), 10); // 페이지 크기 = 10

        List<NoticeResponseDTO.NearestNoticeDTO> nearestNoticeDTO = noticeService.findNearestNotice(latitude, longitude, 0.5, Optional.of(String.valueOf(noticeType)), pageable);
        List<NoticeResponseDTO.NearestNoticeDTO> nearestNoticeDTOS = new ArrayList<>();

        if(noticeType == NoticeType.NEW_PRODUCT || noticeType == NoticeType.EVENT || noticeType == NoticeType.NO_SHOW || noticeType == null){
            // Stream을 사용하여 조건에 맞는 항목만 필터링
            nearestNoticeDTOS = nearestNoticeDTO.stream()
                            .filter(dto -> noticeType == null || dto.getNoticeType().equals(noticeType))
                            .collect(Collectors.toList());
        }else{
            return ApiResponse.onFailure("400", "잘못된 게시글 종류입니다.", noticeType);
        }
        return ApiResponse.onSuccess(nearestNoticeDTOS);
    }
}
