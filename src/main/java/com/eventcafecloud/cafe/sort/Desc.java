package com.eventcafecloud.cafe.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Desc implements SortStrategy {
    @Override
    public Sort sort(String sortValue) {
        return Sort.by(Sort.Direction.DESC, sortValue);
    }
}
