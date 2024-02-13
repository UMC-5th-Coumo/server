package coumo.server.web.dto;

import coumo.server.domain.Notice;
import coumo.server.domain.NoticeImage;
import coumo.server.domain.Store;
import coumo.server.domain.enums.NoticeType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public class NoticeRequestDTO {


    // --------WEB-----------------------

    // 동네소식 글 쓰기, 수정
    @Getter
    @NoArgsConstructor
    public static class updateNoticeDTO {
        @NotBlank
        public NoticeType noticeType;
        @NotBlank
        public String title;
        @NotBlank
        public String noticeContent;
        @Nullable
        public List<NoticeImage> noticeImage;

        public Notice toEntity(Store store){
            return Notice.builder()
                    .store(store)
                    .noticeType(noticeType)
                    .title(title)
                    .noticeContent(noticeContent)
                    .noticeImageList(noticeImage)
                    .build();
        }
    }





    // --------APP-----------------------

    // 고객 위치 (위도, 경도)
    @Getter
    public static class customerLocationDTO{
        @NotBlank
        public String customerId;
        @NotBlank
        public String latitude;
        @NotBlank
        public String longitude;
    }

}
