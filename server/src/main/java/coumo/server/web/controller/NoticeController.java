package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.service.notice.NoticeService;
import coumo.server.service.owner.OwnerService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final OwnerService ownerService;
    private final StoreQueryService storeQueryService;

    // 동네소식 - 글쓰기
    @PostMapping("/{ownerId}/post")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> postNotice(@PathVariable("ownerId") Long ownerId, @RequestBody NoticeRequestDTO.updateNoticeDTO dto){


        ownerService.findOwner(ownerId);                                // owner 존재 여부
        Optional<Store> store = storeQueryService.findStore(ownerId);   // store 존재 여부
        Notice newNotice = noticeService.postNotice(store.get(), dto).get();                     // 글 저장

        return ApiResponse.onSuccess(newNotice.getId());
    }

    // 동네소식 - 내가 쓴 글 보기
    @GetMapping("/{ownerId}/list")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> readNotice(@PathVariable("ownerId") Long ownerId) {

        ownerService.findOwner(ownerId);                                // owner 존재 여부
        Optional<Store> store = storeQueryService.findStore(ownerId);   // store 존재 여부
        NoticeResponseDTO.MyNoticeListDTO myNoticeListDTO = noticeService.readNotice(store.get()); // 내가 쓴 글 보기

        return ApiResponse.onSuccess(myNoticeListDTO);
    }

    // 동네소식 - 내가 쓴 글 세부내용 보기
    @GetMapping("/{ownerId}/detail/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> readNoticeDetail(@PathVariable("ownerId") Long ownerId, @PathVariable("noticeId") Long noticeId){

        ownerService.findOwner(ownerId);            // owner 존재 여부
        storeQueryService.findStore(ownerId);       // store 존재 여부
        noticeService.findNotice(noticeId);         // 글 존재 여부
        NoticeResponseDTO.MyNoticeDetail myNoticeDetail = noticeService.readNoticeDetail(noticeId);   // 내가 쓴 글 세부내용 보기

        return ApiResponse.onSuccess(myNoticeDetail);
    }

    // 동네소식 - 수정 완료 버튼
    @PatchMapping("/{ownerId}/update/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> updateNotice(@PathVariable("ownerId") Long ownerId, @PathVariable("noticeId") Long noticeId, @RequestBody NoticeRequestDTO.updateNoticeDTO dto){

        ownerService.findOwner(ownerId);            // owner 존재 여부
        storeQueryService.findStore(ownerId);       // store 존재 여부
        noticeService.findNotice(noticeId);         // 글 존재 여부
        noticeService.updateNotice(noticeId, dto);  // 수정

        return ApiResponse.onSuccess(noticeId);
    }

    // 동네소식 - 내가 쓴 글 삭제
    @PatchMapping("/{ownerId}/delete/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> deleteNotice(@PathVariable("ownerId") Long ownerId, @PathVariable("noticeId") Long noticeId){

        ownerService.findOwner(ownerId);            // owner 존재 여부
        storeQueryService.findStore(ownerId);       // store 존재 여부
        noticeService.findNotice(noticeId);         // 글 존재 여부
        noticeService.deleteNotice(noticeId);       // 글 삭제

        return ApiResponse.onSuccess(noticeId);
    }

}
