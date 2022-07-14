package com.eventcafecloud.comment.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentCreateRequestDto {
    @Length(min = 1, max = 60)
    private String commentContent;
}
