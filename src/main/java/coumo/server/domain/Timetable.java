package coumo.server.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; //매장 아이디

    @Column(nullable = false, length = 50)
    private String day; //요일

    @Column(length = 32)
    private String startTime; //시작 시간

    @Column(length = 32)
    private String endTime; //종료 시간

    public void setStore(Store store) {
        this.store = store;
    }
}
