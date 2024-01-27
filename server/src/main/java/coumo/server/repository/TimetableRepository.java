package coumo.server.repository;

import coumo.server.domain.Store;
import coumo.server.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    void deleteAllByStore(Store store);
}
