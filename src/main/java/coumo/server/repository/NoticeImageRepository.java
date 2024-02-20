package coumo.server.repository;

import coumo.server.domain.Notice;
import coumo.server.domain.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findAllByNoticeId(Long NoticeId);

    void deleteAllByNotice(Notice notice);
}
