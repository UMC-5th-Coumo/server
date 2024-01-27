package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 동네소식 - 글쓰기
    @PostMapping("/{ownerId}/post")
    public ApiResponse<?> postNotice(@PathVariable("ownerId") Integer ownerId){
        return ApiResponse.onSuccess(0L);
    }

    // 동네소식 - 내가 쓴 글 보기
    @GetMapping("/{ownerId}/list")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> readNotice(@PathVariable("ownerId") Integer ownerId) {
        return ApiResponse.onSuccess(0L);
    }

    // 동네소식 - 내가 쓴 글 세부내용 보기
    @GetMapping("/{ownerId}/detail/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> readNoticeDetail(@PathVariable("ownerId") Integer ownerId, @PathVariable("noticeId") Integer noticeId){
        return ApiResponse.onSuccess(0L);
    }

    // 동네소식 - 수정 완료 버튼
    @PatchMapping("/{ownerId}/update/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> updateNotice(@PathVariable("ownerId") Integer ownerId, @PathVariable("noticeId") Integer noticeId){
        return ApiResponse.onSuccess(0L);
    }

    // 동네소식 - 내가 쓴 글 삭제
    @PatchMapping("/{ownerId}/delete/{noticeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "사장님 아이디, path variable"),
            @Parameter(name = "noticeId", description = "게시물 번호, path variable"),
    })
    public ApiResponse<?> deleteNotice(@PathVariable("ownerId") Integer ownerId, @PathVariable("noticeId") Integer noticeId){
        return ApiResponse.onSuccess(0L);
    }

}
