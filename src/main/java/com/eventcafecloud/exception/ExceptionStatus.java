package com.eventcafecloud.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionStatus {
    USER_NOT_FOUND("회원을 찾을 수 없습니다."),
    EVENT_BOOKMARK_NOT_FOUND("이벤트 북마크를 찾을 수 없습니다."),
    EVENT_NOT_FOUND("이벤트를 찾을 수 없습니다."),
    POST_NOT_FOUND("게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    CAFE_NOT_FOUND("카페를 찾을 수 없습니다.");

    private final String message;

    public String getStatus(){
        return name();
    }

    public String getMessage(){
        return message;
    }
}
