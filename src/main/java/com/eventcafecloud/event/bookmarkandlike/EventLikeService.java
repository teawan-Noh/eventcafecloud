//package com.eventcafecloud.event.service;
//
//import com.eventcafecloud.common.like.LikeResponse;
//import com.eventcafecloud.common.like.LikeService;
//import com.eventcafecloud.event.domain.Event;
//import com.eventcafecloud.event.domain.EventLike;
//import com.eventcafecloud.event.repository.EventLikeRepository;
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
//public class EventLikeService implements LikeService {
//
//    private final EventRepository eventRepository;
//    private final UserRepository userRepository;
//    private final EventLikeRepository eventLikeRepository;
//
//    @Override
//    public LikeResponse createLike(Long eventNumber, String userEmail) {
//        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
//        Event event = eventRepository.findById(eventNumber).orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage()));
//
//        EventLike eventLike = new EventLike(user, event);
//        eventLikeRepository.save(eventLike);
//
//        int count = (int) eventLikeRepository.countByEventEventNumber(eventNumber);
//        return new LikeResponse(count);
//    }
//
//    @Override
//    public LikeResponse deleteLike(Long eventNumber, String userEmail) {
//        EventLike eventLike = eventLikeRepository.findEventBookmarkByUserAndEvent(userEmail, eventNumber).orElseThrow(() -> new IllegalArgumentException(EVENT_BOOKMARK_NOT_FOUND.getMessage()));
//        eventLikeRepository.delete(eventLike);
//
//        int count = (int) eventLikeRepository.countByEventEventNumber(eventNumber);
//        return new LikeResponse(count);
//    }
//
//    @Override
//    public LikeResponse countLikeByEvent(Long eventNumber) {
//        int count = (int) eventLikeRepository.countByEventEventNumber(eventNumber);
//        return new LikeResponse(count);
//    }
//}
