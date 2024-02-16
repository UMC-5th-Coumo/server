package coumo.server.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerStore is a Querydsl query type for CustomerStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerStore extends EntityPathBase<CustomerStore> {

    private static final long serialVersionUID = -1190052863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerStore customerStore = new QCustomerStore("customerStore");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final coumo.server.domain.QCustomer customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> stampCurrent = createNumber("stampCurrent", Integer.class);

    public final EnumPath<coumo.server.domain.enums.StampMax> stampMax = createEnum("stampMax", coumo.server.domain.enums.StampMax.class);

    public final NumberPath<Integer> stampTotal = createNumber("stampTotal", Integer.class);

    public final coumo.server.domain.QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCustomerStore(String variable) {
        this(CustomerStore.class, forVariable(variable), INITS);
    }

    public QCustomerStore(Path<? extends CustomerStore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerStore(PathMetadata metadata, PathInits inits) {
        this(CustomerStore.class, metadata, inits);
    }

    public QCustomerStore(Class<? extends CustomerStore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new coumo.server.domain.QCustomer(forProperty("customer")) : null;
        this.store = inits.isInitialized("store") ? new coumo.server.domain.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

