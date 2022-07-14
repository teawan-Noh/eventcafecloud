package com.eventcafecloud.cafe.sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PriceAsc implements SortStrategy{
    @Override
    public Sort sort() {

        return Sort.by(Sort.Direction.ASC, "cafeWeekdayPrice");
    }
}
