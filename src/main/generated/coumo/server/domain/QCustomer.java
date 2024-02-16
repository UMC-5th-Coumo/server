package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = 1719838112L;

    public static final QCustomer customer = new QCustomer("customer");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    public final StringPath birthday = createString("birthday");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<coumo.server.domain.mapping.CustomerStore, coumo.server.domain.mapping.QCustomerStore> customerStoreList = this.<coumo.server.domain.mapping.CustomerStore, coumo.server.domain.mapping.QCustomerStore>createList("customerStoreList", coumo.server.domain.mapping.CustomerStore.class, coumo.server.domain.mapping.QCustomerStore.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final EnumPath<coumo.server.domain.enums.Gender> gender = createEnum("gender", coumo.server.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<coumo.server.domain.enums.State> state = createEnum("state", coumo.server.domain.enums.State.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCustomer(String variable) {
        super(Customer.class, forVariable(variable));
    }

    public QCustomer(Path<? extends Customer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomer(PathMetadata metadata) {
        super(Customer.class, metadata);
    }

}

