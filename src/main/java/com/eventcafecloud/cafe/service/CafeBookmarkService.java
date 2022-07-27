package com.eventcafecloud.cafe.service;

import com.eventcafecloud.cafe.domain.CafeBookmark;
import com.eventcafecloud.cafe.dto.CafeBookmarkResponseDto;
import com.eventcafecloud.cafe.repository.CafeBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeBookmarkService {

    private final CafeBookmarkRepository cafeBookmarkRepository;

    /**
     * 유저 별 북마크 불러오기
     */
    @Transactional
    public Page<CafeBookmarkResponseDto> findCafeBookmarkByUserId(Long userId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 6, Sort.Direction.DESC, "id");
        Page<CafeBookmark> result = cafeBookmarkRepository.findAllByUserId(userId, pageable);
        return result.map(CafeBookmarkResponseDto::new);
    }
}
