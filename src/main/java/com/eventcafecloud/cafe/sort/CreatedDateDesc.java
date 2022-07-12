package com.eventcafecloud.cafe.sort;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CreatedDateDesc implements SortStrategy{
    @Override
    public Sort sort() {

        return Sort.by(Sort.Direction.DESC, "createdDate");
    }
}
