package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.web.dto.NoticeRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Override
    public void postNotice(Integer ownerId, NoticeRequestDTO.updateNoticeDTO dto) {

    }

    @Override
    public Notice readNotice(Integer ownerId) {
        return null;
    }

    @Override
    public Notice readNoticeDetail(Integer ownerId, Integer noticeId) {
        return null;
    }

    @Override
    public void updateNotice(Integer ownerId, Integer noticeId, NoticeRequestDTO.updateNoticeDTO dto) {

    }

    @Override
    public void deleteNotice(Integer ownerId, Integer noticeId) {

    }
}
