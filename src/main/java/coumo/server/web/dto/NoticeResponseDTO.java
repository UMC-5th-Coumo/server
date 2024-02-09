package coumo.server.web.dto;

import coumo.server.domain.NoticeImage;
import coumo.server.domain.enums.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeResponseDTO {


    // --------WEB-----------------------

    // 나 = 사장님

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 리스트 읽기
    public static class OwnerNoticeList {
        public Integer total;
        public List<NoticeThumbInfo> notice;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 세부내용 읽기
    public static class OwnerNoticeDetail {
        public NoticeType noticeType;
        public String title;
        public String noticeContent;
        public List<NoticeImage> noticeImage;
        public LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 리스트를 위한 썸네일 컨텐츠
    public static class NoticeThumbInfo{
        public Long noticeId;
        public NoticeType noticeType;
        public String title;
        public LocalDateTime createdAt;
    }

    // --------APP-----------------------

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 고객 주변 동네 소식 (NoticeType 3가지)
    public static class NearestNoticeDTO{
        String title;               // 게시글 제목
        NoticeType noticeType;      // 게시글 종류
        String noticeContent;       // 게시글 내용
        LocalDateTime createdAt;    // 게시글 생성일
        String storeName;           // 매장 이름
        List<NoticeImage> noticeImage;  // 게시글 사진 리스트
    }

}
