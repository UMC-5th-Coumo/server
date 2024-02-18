package coumo.server.service.notice;

import coumo.server.aws.s3.AmazonS3Manager;
import coumo.server.aws.s3.Filepath;
import coumo.server.domain.Notice;
import coumo.server.domain.NoticeImage;
import coumo.server.domain.Store;
import coumo.server.domain.enums.NoticeType;
import coumo.server.repository.NoticeImageRepository;
import coumo.server.repository.NoticeRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.util.geometry.Direction;
import coumo.server.util.geometry.GeometryUtil;
import coumo.server.util.geometry.Location;
import coumo.server.web.dto.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final StoreRepository storeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public Notice postNotice(Long ownerId, NoticeType noticeType, String title, String noticeContent, MultipartFile[] noticeImages) {

        // 엔티티 조회
        Store store = storeRepository.findByOwnerId(ownerId).orElseThrow();
        Notice notice = Notice.builder()
                .store(store)
                .noticeType(noticeType)
                .title(title)
                .noticeContent(noticeContent)
                .noticeImageList(new ArrayList<>())
                .build();

        Notice newNotice = noticeRepository.save(notice);

        if(noticeImages.length != 0)
        {
            for(MultipartFile image : noticeImages){
                String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.makeKeyName(Filepath.NOTICE), image);
                NoticeImage noticeImage = NoticeImage.builder()
                        .noticeImage(imageUrl)
                        .notice(newNotice)
                        .build();
                newNotice.addNoticeImage(noticeImage);
            }
        }
        return newNotice;
    }

    @Override
    public Page<Notice> findOwnerNotice(Store store, Pageable pageable) {

        return noticeRepository.findAllByStore(store, pageable);
    }

    @Override
    public NoticeResponseDTO.OwnerNoticeDetail readNoticeDetail(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).get();

        List<String> noticeImages = notice.getNoticeImageList() == null ? Collections.emptyList() :
                notice.getNoticeImageList().stream()
                        .map(NoticeImage::getNoticeImage)
                        .collect(Collectors.toList());

        NoticeResponseDTO.OwnerNoticeDetail ownerNoticeDetail = NoticeResponseDTO.OwnerNoticeDetail.builder()
                .noticeType(notice.getNoticeType())
                .noticeContent(notice.getNoticeContent())
                .noticeImages(noticeImages)
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();

        return ownerNoticeDetail;
    }

    @Override
    public void updateNotice(Long noticeId, String noticeType, String title, String noticeContent, MultipartFile[] noticeImages){

        // 조회
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();

        // 삭제
        noticeImageRepository.deleteAllByNotice(notice);
        notice.getNoticeImageList().clear();

        // 업데이트
        if(noticeImages.length != 0)
        {
            for(MultipartFile image : noticeImages){
                String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.makeKeyName(Filepath.NOTICE), image);
                NoticeImage noticeImage = NoticeImage.builder()
                        .noticeImage(imageUrl)
                        .notice(notice)
                        .build();
                notice.addNoticeImage(noticeImage);
            }
        }
        notice.setUpdateNotice(noticeType, title, noticeContent);

    }

    @Override
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        noticeImageRepository.deleteAllByNotice(notice);
        notice.getNoticeImageList().clear();
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public Optional<Notice> findNotice(Long noticeId) {
        return noticeRepository.findById(noticeId);
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
        Page<Store> storePage = findNearestStore(latitude, longitude, distance, category, pageable);
        log.info("storePage.isEmpty={}", storePage.isEmpty());
        if (storePage.isEmpty()) return Collections.emptyList();

        List<NoticeResponseDTO.NearestNoticeDTO> resultList = new ArrayList<>();
        List<Store> storeList = storePage.getContent();

        storeList.forEach(item -> {
            List<Notice> notices = noticeRepository.findAllByStoreId(item.getId());
            notices.forEach(notice -> {
                List<String> noticeImages = noticeImageRepository.findAllByNoticeId(notice.getId());

                resultList.add(NoticeResponseDTO.NearestNoticeDTO.builder()
                        .noticeImages(noticeImages)
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


