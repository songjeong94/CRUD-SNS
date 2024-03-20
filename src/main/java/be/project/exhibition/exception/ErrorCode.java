package be.project.exhibition.exception;

import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "중복된 유저 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALIDED_PASSWORD(HttpStatus.UNAUTHORIZED, "틀린 비밀번호입니다."),

    INVALIDED_PERMISSION(HttpStatus.UNAUTHORIZED, "작성자가 아닙니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    COMMENT_NOT_FOUNDED(HttpStatus.NOT_FOUND, "존재하지 않는 댓글 입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "올바른 요청이 아닙니다."),
    POST_NOT_FOUNDED(HttpStatus.NOT_FOUND, "존재하지 않는 포스트 입니다."),

    ALREADY_LIKE(HttpStatus.CONFLICT, "이미 좋아요 하였습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 맞지 않습니다.")
    ;

    private HttpStatus status;
    private String message;
}
