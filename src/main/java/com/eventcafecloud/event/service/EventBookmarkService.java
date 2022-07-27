package com.eventcafecloud.event.service;

import com.eventcafecloud.event.domain.EventBookmark;
import com.eventcafecloud.event.dto.EventBookmarkResponseDto;
import com.eventcafecloud.event.repository.EventBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventBookmarkService {

    private final EventBookmarkRepository eventBookmarkRepository;

    @Transactional
    public Page<EventBookmarkResponseDto> findEventBookmarkByUserId(Long userId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 6, Sort.Direction.DESC, "id");
        Page<EventBookmark> result = eventBookmarkRepository.findAllByUserId(userId, pageable);
        return result.map(EventBookmarkResponseDto::new);
    }
}
