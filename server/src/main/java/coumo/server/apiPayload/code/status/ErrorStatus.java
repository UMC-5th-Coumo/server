package coumo.server.apiPayload.code.status;

import coumo.server.apiPayload.code.BaseErrorCode;
import coumo.server.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _BAD_REQUEST_NOT_FOUND(HttpStatus.BAD_REQUEST,"COMMON400","데이터를 찾을 수 없습니다"),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _PAGE_OVER_RANGE(HttpStatus.BAD_REQUEST, "COMMON400", "페이징 범위를 넘었습니다. 페이징 0 부터 시작합니다."),


    STORE_NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE,"STORE406","가게에 대한 자세한 정보가 없습니다. (사장님이 아직 쿠폰이나 가게 설정 부족)"),
    STORE_CATEGORY_BAD_REQUEST(HttpStatus.BAD_REQUEST,"STORE400","카테고리 입력이 잘 못 되었습니다"),
    STORE_IMAGE_NOT_EXIST(HttpStatus.BAD_REQUEST,"STORE400","최소 하나의 매장 사진을 넣어주세요."),
    STORE_MENU_NOT_EXIST(HttpStatus.BAD_REQUEST,"STORE400","최소 하나의 메뉴는 입력해주세요."),
    STORE_MENU_BAD_REQUEST(HttpStatus.BAD_REQUEST,"STORE400","menu json 형식이 잘 못 되었습니다"),
    STORE_MENU_COUNT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"STORE400","메뉴 이미지 개수와 메뉴 json 개수가 다릅니다. 개수를 동일하게 설정해주세요"),
    STORE_POINT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"STORE400","위도는 -90부터 90까지, 경도는 -180부터 180까지의 값을 가져야 합니다.");


    // 예시,,,
    // 멤버 관려 에러
    //MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    //NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    //ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}