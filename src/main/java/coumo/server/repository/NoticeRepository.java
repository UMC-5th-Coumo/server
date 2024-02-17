package coumo.server.repository;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findById(Long id);

    Notice save(Notice notice);

    Page<Notice> findAllByStore(Store store, Pageable pageable);

    List<Notice> findAllByStoreId(Long id);
}
