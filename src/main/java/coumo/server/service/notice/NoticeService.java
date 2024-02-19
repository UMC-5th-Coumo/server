package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.domain.enums.NoticeType;
import coumo.server.web.dto.NoticeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Notice postNotice(Long ownerId, NoticeType noticeType, String title, String noticeContent, Optional<MultipartFile[]> noticeImages);

    Page<Notice> findOwnerNotice(Store store, Pageable pageable);

    NoticeResponseDTO.OwnerNoticeDetail readNoticeDetail(Long noticeId);

    void updateNotice(Long noticeId, String noticeType, String title, String noticeContent, MultipartFile[] noticeImages);

    void deleteNotice(Long noticeId);

    Optional<Notice> findNotice(Long noticeId);

    Page<Store> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable);

    public List<NoticeResponseDTO.NearestNoticeDTO> findNearestNotice(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable);

}
