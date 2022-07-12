package com.eventcafecloud.cafe.sort;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Qualifier("priceDesc")
public class PriceDesc implements SortStrategy{
    @Override
    public Sort sort() {

        return Sort.by(Sort.Direction.DESC, "cafeWeekdayPrice");
    }
}
