package be.project.exhibition.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "중복된 유저 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALIDED_PASSWORD(HttpStatus.UNAUTHORIZED, "틀린 비밀버호입니다."),

    INVALIDED_PERMISSION(HttpStatus.UNAUTHORIZED, "작성자가 아닙니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    POST_NOT_FOUNDED(HttpStatus.NOT_FOUND, "존재하지 않는 포스트 입니다.")
    ;

    private HttpStatus status;
    private String message;
}
