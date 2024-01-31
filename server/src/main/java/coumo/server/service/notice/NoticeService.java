package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Notice postNotice(Store store, NoticeRequestDTO.updateNoticeDTO dto);

    NoticeResponseDTO.MyNoticeListDTO readNotice(Store store);

    NoticeResponseDTO.MyNoticeDetail readNoticeDetail(Long noticeId);

    void updateNotice(Long noticeId, NoticeRequestDTO.updateNoticeDTO dto);

    void deleteNotice(Long noticeId);

    Notice findNotice(Long noticeId);
}
