//package com.eventcafecloud.event.service;
//
//import com.eventcafecloud.common.bookmark.BookmarkResponse;
//import com.eventcafecloud.common.bookmark.BookmarkService;
//import com.eventcafecloud.event.domain.Event;
//import com.eventcafecloud.event.domain.EventBookmark;
//import com.eventcafecloud.event.repository.EventBookmarkRepository;
//import com.eventcafecloud.event.repository.EventRepository;
//import com.eventcafecloud.user.domain.User;
//import com.eventcafecloud.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import static com.eventcafecloud.exception.ExceptionStatus.*;
//
//@RequiredArgsConstructor
//@Service
//public class EventBookmarkService implements BookmarkService {
//
//    private final EventBookmarkRepository eventBookmarkRepository;
//    private final EventRepository eventRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public BookmarkResponse createBookmark(Long eventNumber, String userEmail) {
//        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
//        Event event = eventRepository.findById(eventNumber).orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage()));
//
//        EventBookmark eventBookmark = new EventBookmark(user, event);
//        eventBookmarkRepository.save(eventBookmark);
//
//        int count = (int) eventBookmarkRepository.countByEventEventNumber(eventNumber);
//        return new BookmarkResponse(count);
//    }
//
//    @Override
//    public BookmarkResponse findBookmark(Long eventNumber) {
//        int count = (int) eventBookmarkRepository.countByEventEventNumber(eventNumber);
//        return new BookmarkResponse(count);
//    }
//
//    @Override
//    public BookmarkResponse deleteBookmark(Long eventNumber, String userEmail) {
//        EventBookmark eventBookmark = eventBookmarkRepository.findEventBookmarkByUserAndEvent(userEmail, eventNumber).orElseThrow(() -> new IllegalArgumentException(EVENT_BOOKMARK_NOT_FOUND.getMessage()));
//        eventBookmarkRepository.delete(eventBookmark);
//
//        int count = (int) eventBookmarkRepository.countByEventEventNumber(eventNumber);
//        return new BookmarkResponse(count);
//    }
//}
