package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Notice postNotice(Store store, NoticeRequestDTO.updateNoticeDTO dto);

    Page<Notice> findOwnerNotice(Store store, Pageable pageable);

    NoticeResponseDTO.OwnerNoticeDetail readNoticeDetail(Long noticeId);

    void updateNotice(Long noticeId, NoticeRequestDTO.updateNoticeDTO dto);

    void deleteNotice(Long noticeId);

    Notice findNotice(Long noticeId);
}
