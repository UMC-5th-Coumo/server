package coumo.server.repository;

import coumo.server.domain.Notice;
import coumo.server.domain.NoticeImage;
import coumo.server.domain.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findAllByNoticeId(Long NoticeId);

    Optional<List<Notice>> findAllByNotice(Notice notice);

    void deleteAllByNotice(Notice notice);
}
