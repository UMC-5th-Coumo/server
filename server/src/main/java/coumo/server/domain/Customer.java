package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.Gender;
import coumo.server.domain.enums.State;
import coumo.server.domain.mapping.CustomerStore;
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
 * 고객 테이블
 * 성별: Gender
 * 계정 상태: State
 */
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer loginId;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 16)
    private String birthday;

    @Column(nullable = false, length = 32)
    private String nickname;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false, length = 32)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(8)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =  "VARCHAR(32)")
    private State state;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerStore> customerStoreList = new ArrayList<>();
}
