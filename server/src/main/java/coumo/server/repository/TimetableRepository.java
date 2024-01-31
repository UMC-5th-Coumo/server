package coumo.server.repository;

import coumo.server.domain.Store;
import coumo.server.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    Optional<Timetable> findByStoreAndDay(Store store, String day);

    void deleteAllByStore(Store store);
}