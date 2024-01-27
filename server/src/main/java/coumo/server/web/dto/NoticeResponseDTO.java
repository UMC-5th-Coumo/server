package coumo.server.web.dto;

import coumo.server.domain.enums.NoticeType;
import coumo.server.domain.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class NoticeResponseDTO {

    // --------WEB-----------------------

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 리스트 읽기
    public static class myNoticeListDTO{
        public Integer total;
        public List<NoticeThumbInfo> notice;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 세부내용 읽기
    public static class myNoticeDetail{
        public NoticeType noticeType;
        public String title;
        public String noticeContent;
        public String image;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    // 내가 쓴 글 리스트를 위한 썸네일 컨텐츠
    public static class NoticeThumbInfo{
        public Integer noticeId;
        public NoticeType noticeType;
        public String title;
        public String contentThumb;
    }

    // --------APP-----------------------

//    @Builder
//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    // 고객 주변 동네소식
//    public static class aroundNoticeListDTO{
//        List<NoticeInfo> store;
//    }
//
//    @Builder
//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    // 동네 소식 (필터링 4가지)
//    public static class NoticeInfo{
//        public String storeName;
//        public StoreType storeType;
//        public String storeDescription;
//    }
}
