package com.eventcafecloud.common.like;

public interface LikeService {
    LikeResponse createLike(Long eventNumber, String userEmail);
    LikeResponse deleteLike(Long eventNumber, String userEmail);
    LikeResponse countLikeByEvent(Long eventNumber);
}
