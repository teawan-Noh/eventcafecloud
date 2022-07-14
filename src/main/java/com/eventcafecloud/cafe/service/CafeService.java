package com.eventcafecloud.cafe.service;


import com.eventcafecloud.cafe.domain.*;
import com.eventcafecloud.cafe.dto.*;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.cafe.repository.CafeReviewRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final UserRepository userRepoistory;
    private final CafeReviewRepository cafeReviewRepository;
    private final EventRepository eventRepository;
    private final S3Service s3Service;

    // 리뷰등록
    @Transactional
    public void saveCafeReview(CafeReviewRequestDto requestDto, Long cafeNumber, User securityUser) {
        User user = userRepoistory.getById(securityUser.getId());
        Cafe cafe = cafeRepository.findById(cafeNumber).orElseThrow();
        CafeReview cafeReview = new CafeReview(requestDto);
        user.addCafeReview(cafeReview);
        cafe.addCafeReview(cafeReview);

        cafeReviewRepository.save(cafeReview);
    }

    @Transactional
    public void createCafe(CafeCreateRequestDto requestDto, User securityUser) {
        User user = userRepoistory.getById(securityUser.getId());
        Cafe cafe = new Cafe(requestDto);
        user.addCafe(cafe);

        List<MultipartFile> files = requestDto.getFiles();
        // s3저장 후 url 반환받음
        List<String> cafeImageUrlList = s3Service.upload(files, "cafeImage");

        // 카페 이미지 생성
        MultipartFile file;
        String cafeImageUrl;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            cafeImageUrl = cafeImageUrlList.get(i);
            CafeImage cafeImage = new CafeImage(file.getOriginalFilename(), cafeImageUrl);
            cafe.addCafeImage(cafeImage);
        }

        // 카페 옵션 생성성
        List<CafeOptionType> optionList = requestDto.getOptions();
        for (CafeOptionType option : optionList) {
            CafeOption cafeOption = new CafeOption(option);
            cafe.addCafeOption(cafeOption);
        }
        cafeRepository.save(cafe);
    }

//    public Page<Cafe> findAllCafeList3() {
////        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
////        Sort sort = Sort.by(direction, sortBy);
////        Pageable pageable = PageRequest.of(page, size, sort);
//
////        Pageable pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate"));
//        Pageable pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate"));
//
//        return cafeRepository.findAll(pageRequest);
//    }
//
    public Page<CafeListResponseDto> findAllCafeList(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Cafe> all = cafeRepository.findAll(pageRequest);
        //return all.map(cafe -> new CafeListResponseDto(cafe));
        // 위의 주석단 람다식을 아래의 식으로 치환
        return all.map(CafeListResponseDto::new);
    }

    public List<CafeListResponseDto> findCafeTopFiveList() {
        List<Cafe> cafeList = cafeRepository.findTop5ByOrderByCreatedDateDesc();

        List<CafeListResponseDto> cafeListResponseDtos = cafeList.stream()
                .map(c -> new CafeListResponseDto(c))
                .collect(Collectors.toList());

        return cafeListResponseDtos;
    }

    public Page<Cafe> getCafeList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10);

        return cafeRepository.findAll(pageable);
    }

    public Page<Cafe> getCafeListByUserId(Long id, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3);

        return cafeRepository.findAllByUserId(id, pageable);
    }

    public CafeDetailResponseDto findCafeByIdForDetail(Long id) {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));

        return new CafeDetailResponseDto(cafe);
    }

    public CafeUpdateRequestDto findCafeByIdForUpdate(Long id) {

        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));

        return CafeUpdateRequestDto.toDto(cafe);
//        return new CafeUpdateRequestDto(cafe);
    }

    @Transactional
    public void modifyCafe(Long id, CafeUpdateRequestDto requestDto) {
        Cafe cafe = cafeRepository.getById(id);
        cafe.updateCafeInfo(requestDto);
    }

    @Transactional
    public void removeCafe(Long id) {
        cafeRepository.deleteById(id);
    }

    public List<CafeCalenderInfoResponseDto> findEventListForCalenderByCafeId(Long id) {
        List<Event> eventList = eventRepository.findALLByCafeId(id);

        List<CafeCalenderInfoResponseDto> cafeCalenderInfoResponseDtos = eventList.stream()
                .map(e -> new CafeCalenderInfoResponseDto(e))
                .collect(Collectors.toList());

        return cafeCalenderInfoResponseDtos;
    }
}
