package com.eventcafecloud.common.bookmark;

public interface BookmarkService {
    BookmarkResponse createBookmark(Long eventNumber, String userEmail);
    BookmarkResponse findBookmark(Long eventNumber);
    BookmarkResponse deleteBookmark(Long eventNumber, String userEmail);
}
