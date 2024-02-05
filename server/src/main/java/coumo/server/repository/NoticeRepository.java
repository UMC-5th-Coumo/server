package coumo.server.repository;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.web.dto.NoticeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Override
    Optional<Notice> findById(Long noticeId);

    Notice save(Notice notice);

    Page<Notice> findAllByStore(Store store, Pageable pageable);
}
