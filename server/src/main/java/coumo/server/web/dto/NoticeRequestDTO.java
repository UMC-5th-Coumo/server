package coumo.server.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.lang.Nullable;

public class NoticeRequestDTO {


    // --------WEB-----------------------

    // 동네소식 글 쓰기, 수정
    @Getter
    public static class updateNoticeDTO {
        @NotBlank
        public String noticeType;
        @NotBlank
        public String title;
        @NotBlank
        public String noticeContent;
        @Nullable
        public String image;
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
