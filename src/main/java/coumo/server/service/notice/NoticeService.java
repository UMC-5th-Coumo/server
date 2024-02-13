package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import coumo.server.web.dto.StoreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Notice postNotice(Store store, NoticeRequestDTO.updateNoticeDTO dto);

    Page<Notice> findOwnerNotice(Store store, Pageable pageable);

    NoticeResponseDTO.OwnerNoticeDetail readNoticeDetail(Long noticeId);

    void updateNotice(Long noticeId, NoticeRequestDTO.updateNoticeDTO dto);

    void deleteNotice(Long noticeId);

    Notice findNotice(Long noticeId);

    Page<Store> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable);

    public List<NoticeResponseDTO.NearestNoticeDTO> findNearestNotice(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable);

}
