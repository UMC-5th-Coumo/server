package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.NoticeType;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/**
 * 게시글 테이블
 * 게시글 종류 : NoticeType
 */
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; //매장 아이디

    @Column(nullable = false, length = 50)
    private String title; //제목

    @Column(length = 50)
    private String image; //게시글 이미지

    @Column(nullable = false, length = 2048)
    private String noticeContent; //게시글 내용

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(16)")
    private NoticeType noticeType; //게시글 종류

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<NoticeImage> noticeImageList = new ArrayList<>();

    public void setStore(Store store) {
        this.store = store;
    }
}


