package com.eventcafecloud.cafe.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class BookmarkDesc implements SortStrategy {

    @Override
    public Sort sort() {

        return Sort.by(Sort.Direction.DESC, "cafeBookmarkCount");
    }
}
