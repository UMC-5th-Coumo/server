package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.web.dto.NoticeRequestDTO;

public interface NoticeService {
    void postNotice(Integer ownerId, NoticeRequestDTO.updateNoticeDTO dto);

    Notice readNotice(Integer ownerId);

    Notice readNoticeDetail(Integer ownerId, Integer noticeId);

    void updateNotice(Integer ownerId, Integer noticeId, NoticeRequestDTO.updateNoticeDTO dto);

    void deleteNotice(Integer ownerId, Integer noticeId);
}
