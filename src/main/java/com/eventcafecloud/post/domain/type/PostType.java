package com.eventcafecloud.post.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostType {
    NOTICE("공지사항"),
    USERPOST("유저게시판");
    private String value;
}
