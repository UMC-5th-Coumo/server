package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeImage is a Querydsl query type for NoticeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeImage extends EntityPathBase<NoticeImage> {

    private static final long serialVersionUID = 2101187681L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeImage noticeImage1 = new QNoticeImage("noticeImage1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNotice notice;

    public final StringPath noticeImage = createString("noticeImage");

    public QNoticeImage(String variable) {
        this(NoticeImage.class, forVariable(variable), INITS);
    }

    public QNoticeImage(Path<? extends NoticeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeImage(PathMetadata metadata, PathInits inits) {
        this(NoticeImage.class, metadata, inits);
    }

    public QNoticeImage(Class<? extends NoticeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice"), inits.get("notice")) : null;
    }

}

