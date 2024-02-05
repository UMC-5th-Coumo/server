package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.GeneralException;
import coumo.server.converter.NoticeConverter;
import coumo.server.domain.Notice;
import coumo.server.domain.Store;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final StoreQueryService storeQueryService;

    @Operation(summary = "사장님 : 동네소식 글 쓰기")
    @PostMapping("/{ownerId}/post")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> postNotice(@ExistOwner(message = "존재안해요") @PathVariable("ownerId") Long ownerId, @RequestBody NoticeRequestDTO.updateNoticeDTO dto){

        Optional<Store> store = storeQueryService.findStore(ownerId);  // store 존재 여부
        Notice newNotice = noticeService.postNotice(store.get(), dto);  // 글 저장

        return ApiResponse.onSuccess(newNotice.getId());
    }

    @Operation(summary = "사장님 : 내가 쓴 글 리스트 보기 (페이징=5)")
    @GetMapping("/{ownerId}/list/{pageId}")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> readNotice(@ExistOwner @PathVariable("ownerId") Long ownerId, @CheckPage @PathVariable(name="pageId")Long pageId) {

        Pageable pageable = PageRequest.of((int) (pageId - 1), 5); // 페이지 크기 = 5

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

        NoticeResponseDTO.OwnerNoticeDetail ownerNoticeDetail = noticeService.readNoticeDetail(noticeId);   // 내가 쓴 글 세부내용 보기
        return ApiResponse.onSuccess(ownerNoticeDetail);
    }

    @Operation(summary = "사장님 : 동네소식 글 수정 완료 버튼")
    @PatchMapping("/{ownerId}/update/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> updateNotice(@ExistOwner @PathVariable("ownerId") Long ownerId, @ExistNotice @PathVariable("noticeId") Long noticeId, @RequestBody NoticeRequestDTO.updateNoticeDTO dto){

        noticeService.updateNotice(noticeId, dto);  // 수정
        return ApiResponse.onSuccess(noticeId);
    }

    @Operation(summary = "사장님 : 내가 쓴 동네소식 글 삭제")
    @PatchMapping("/{ownerId}/delete/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> deleteNotice(@ExistOwner @PathVariable("ownerId") Long ownerId, @ExistNotice @PathVariable("noticeId") Long noticeId){

        noticeService.deleteNotice(noticeId);       // 글 삭제
        return ApiResponse.onSuccess(noticeId);
    }

    // 동네소식 -

}
