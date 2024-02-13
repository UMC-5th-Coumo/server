package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.NoticeImage;
import coumo.server.domain.Store;
import coumo.server.repository.NoticeImageRepository;
import coumo.server.repository.NoticeRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.util.geometry.Direction;
import coumo.server.util.geometry.GeometryUtil;
import coumo.server.util.geometry.Location;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final StoreRepository storeRepository;
    private final NoticeImageRepository noticeImageRepository;

    @Override
    public Notice postNotice(Store store, NoticeRequestDTO.updateNoticeDTO dto) {

        Notice newNotice = dto.toEntity(store);
        return noticeRepository.save(newNotice);
    }

    @Override
    public Page<Notice> findOwnerNotice(Store store, Pageable pageable) {

        return noticeRepository.findAllByStore(store);
    }

    @Override
    public NoticeResponseDTO.OwnerNoticeDetail readNoticeDetail(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).get();
        NoticeResponseDTO.OwnerNoticeDetail ownerNoticeDetail = NoticeResponseDTO.OwnerNoticeDetail.builder()
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .noticeContent(notice.getNoticeContent())
                .noticeImage(notice.getNoticeImageList())
                .createdAt(notice.getCreatedAt())
                .build();

        return ownerNoticeDetail;
    }

    @Override
    public void updateNotice(Long noticeId, NoticeRequestDTO.updateNoticeDTO dto) {

        Notice notice = noticeRepository.findById(noticeId).get();
        Notice newNotice = Notice.builder()
                .id(noticeId)
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .noticeContent(notice.getNoticeContent())
                .image(notice.getImage())
                .build();

        noticeRepository.save(newNotice);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public Notice findNotice(Long noticeId) {
        return noticeRepository.findById(noticeId).get();
    }

    @Override
    public Page<Store> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        if (category.isPresent()) return storeRepository.findNearByStores(x1, y1, x2, y2, category.get(), pageable);
        else return storeRepository.findNearByStores(x1, y1, x2, y2, pageable);
    }

    @Override
    public List<NoticeResponseDTO.NearestNoticeDTO> findNearestNotice(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable) {

        //근처 매장 찾기
        Page<Store> storePage = findNearestStore(longitude, latitude, distance, category, pageable);
        if (storePage.isEmpty()) return Collections.emptyList();

        List<NoticeResponseDTO.NearestNoticeDTO> resultList = new ArrayList<>();
        List<Store> storeList = storePage.getContent();

        storeList.forEach(item -> {
            List<Notice> notices = noticeRepository.findAllByStoreId(item.getId());
            notices.forEach(notice -> {
                List<NoticeImage> noticeImages = noticeImageRepository.findAllByNoticeId(notice.getId());

                resultList.add(NoticeResponseDTO.NearestNoticeDTO.builder()
                        .noticeImage(noticeImages)
                        .noticeContent(notice.getNoticeContent())
                        .noticeType(notice.getNoticeType())
                        .title(notice.getTitle())
                        .storeName(item.getName())
                        .createdAt(notice.getCreatedAt())
                        .build());

            });

        });

        return resultList;

    }
}


