package coumo.server.converter;

import coumo.server.domain.Notice;
import coumo.server.web.dto.NoticeResponseDTO;

public class NoticeConverter {

    public static NoticeResponseDTO.NoticeThumbInfo toNoticeThumbInfo(Notice notice){
        return NoticeResponseDTO.NoticeThumbInfo.builder()
                .noticeId(notice.getId())
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .contentThumb(notice.getNoticeContent())
                .build();
    }
}
