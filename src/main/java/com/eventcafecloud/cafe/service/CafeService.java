package com.eventcafecloud.cafe.service;


import com.eventcafecloud.cafe.domain.*;
import com.eventcafecloud.cafe.dto.*;
import com.eventcafecloud.cafe.repository.*;
import com.eventcafecloud.cafe.sort.SortStrategy;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import com.nhncorp.lucy.security.xss.XssPreventer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.eventcafecloud.exception.ExceptionStatus.CAFE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final UserRepository userRepoistory;
    private final CafeReviewRepository cafeReviewRepository;
    private final CafeScheduleRepository cafeScheduleRepository;
    private final EventRepository eventRepository;
    private final S3Service s3Service;
    private final Map<String, SortStrategy> sortStrategyMap;
    private final CafeOptionRepository cafeOptionRepository;
    private final CafeImageRepository cafeImageRepository;
    private final CafeBookmarkRepository cafeBookmarkRepository;

    @Transactional
    public void saveCafe(CafeCreateRequestDto requestDto, User securityUser) {
        User user = userRepoistory.getById(securityUser.getId());
        Cafe cafe = new Cafe(requestDto);
        user.addCafe(cafe);

        List<MultipartFile> files = requestDto.getFiles();
        // s3저장 후 url 반환받음
        List<String> cafeImageUrlList = s3Service.upload(files, "cafeImage");

        // 카페 이미지 생성
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String cafeImageUrl = cafeImageUrlList.get(i);
            CafeImage cafeImage = new CafeImage(file.getOriginalFilename(), cafeImageUrl);
            cafe.addCafeImage(cafeImage);
        }

        // 카페 옵션 생성성
        List<CafeOptionType> optionList = requestDto.getCafeOptions();
        for (CafeOptionType option : optionList) {
            CafeOption cafeOption = new CafeOption(option);
            cafe.addCafeOption(cafeOption);
        }
        cafeRepository.save(cafe);
    }

    public Page<CafeListResponseDto> findAllCafeList(int page, int size, String searchVal, String sortStrategyKey) {

        SortStrategy sortStrategy = sortStrategyMap.get(sortStrategyKey);
        Sort sort = sortStrategy.sort();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Cafe> all;
        if (searchVal.equals("")) {
            all = cafeRepository.findAll(pageable);
        } else {
            all = cafeRepository.findAllByCafeNameContaining(searchVal, pageable);
            // like 대신 Containing
        }
//        return all.map(cafe -> new CafeListResponseDto(cafe));
        // 위의 주석단 람다식을 아래의 식으로 치환
        return all.map(CafeListResponseDto::new);
    }

    /**
     * 카페 리스트 for 메인페이지
     */
    public List<CafeListResponseDto> findCafeTopFiveList() {
        List<Cafe> cafeList = cafeRepository.findTop5ByOrderByCreatedDateDesc();

        List<CafeListResponseDto> cafeListResponseDtos = cafeList.stream()
                .map(c -> new CafeListResponseDto(c))
                .collect(Collectors.toList());

        return cafeListResponseDtos;
    }

    public Page<Cafe> findAllCafeList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return cafeRepository.findAll(pageable);
    }

    public Page<Cafe> findCafeListByUserId(Long id, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3);

        return cafeRepository.findAllByUserId(id, pageable);
    }

    public CafeDetailResponseDto findCafeByIdForDetail(Long cafeId, User loginUser) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage()));

        boolean checkBookmarkByLoginUser;
        if (loginUser != null) {
            checkBookmarkByLoginUser = cafeBookmarkRepository.existsByCafeIdAndUserId(cafeId, loginUser.getId());
        } else {
            checkBookmarkByLoginUser = false;
        }

        return new CafeDetailResponseDto(cafe, checkBookmarkByLoginUser);
    }

    public CafeUpdateRequestDto findCafeByIdForUpdateForm(Long id, User loginUser) {

        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage()));

        if (!cafe.getUser().getId().equals(loginUser.getId())) {
            return CafeUpdateRequestDto.builder().statusCode(500).build();
        }

        // Builder pattern으로 수정해보기
        return CafeUpdateRequestDto.toDto(cafe);
//        return new CafeUpdateRequestDto(cafe);
    }

    // 카페 수정
    @Transactional
    public int modifyCafe(Long id, CafeUpdateRequestDto requestDto, User loginUser) {
        Cafe cafe = cafeRepository.getById(id);
        if (!cafe.getUser().getId().equals(loginUser.getId())) {
            return 500;
        }
        cafe.updateCafeInfo(requestDto);

        if (requestDto.getCafeOptions() != null) {
            // db에 저장된 데이터 삭제
            List<CafeOption> cafeOptions = cafe.getCafeOptions();
            cafeOptionRepository.deleteAllInBatch(cafeOptions);

            List<CafeOptionType> optionList = requestDto.getCafeOptions();
            for (CafeOptionType option : optionList) {
                CafeOption cafeOption = new CafeOption(option);

                cafe.addCafeOption(cafeOption);
            }
        }
        if (requestDto.getFiles().size() == 2) {
            List<CafeImage> cafeImages = cafe.getCafeImages();
            List<String> imageKeys = new ArrayList<>();
            for (CafeImage cafeImage : cafeImages) {
                // 3번째 '/'의 위치를 찾아서 +1 번째 부터 문자열 반환받아 key값으로 사용
                int location = cafeImage.getCafeImageUrl().indexOf("/", 10);
                String imageKey = cafeImage.getCafeImageUrl().substring(location + 1);
                imageKeys.add(imageKey);
            }
            cafeImageRepository.deleteAllInBatch(cafeImages);

            List<MultipartFile> files = requestDto.getFiles();
            // s3저장 후 url 반환받음
            List<String> cafeImageUrlList = s3Service.reupload(files, "cafeImage", imageKeys);

            // 카페 이미지 생성
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String cafeImageUrl = cafeImageUrlList.get(i);
                CafeImage cafeImage = new CafeImage(file.getOriginalFilename(), cafeImageUrl);
                cafe.addCafeImage(cafeImage);
            }
        }
        return 200;
    }

    /**
     * 카페 삭제
     * 카페 삭제시 s3에 등록된 파일 함께 삭제
     * 현재 날짜 기준 뒤로 이벤트가 등록되어 있으면 삭제 불가
     */
    @Transactional
    public String removeCafe(Long id, User loginUser) {
        Cafe cafe = cafeRepository.getById(id);
        if (!cafe.getUser().getId().equals(loginUser.getId())) {
            return "삭제 실패 : 비정상적인 접근입니다.";
        }
        LocalDate now = LocalDate.now();
        // 이벤트 존재 여부 확인
        Event hasEventAfterNow = eventRepository.findTop1ByCafeIdAndEventStartDateAfter(id, now.toString());

        if (hasEventAfterNow != null) {
            return "이벤트 예약내역이 존재하므로 삭제가 불가능합니다.";
        } else {
            List<CafeImage> cafeImages = cafe.getCafeImages();
            List<String> imageKeys = new ArrayList<>();
            for (CafeImage cafeImage : cafeImages) {
                // 3번째 '/'의 위치를 찾아서 +1 번째 부터 문자열 반환받아 key값으로 사용
                int location = cafeImage.getCafeImageUrl().indexOf("/", 10);
                String imageKey = cafeImage.getCafeImageUrl().substring(location + 1);
                imageKeys.add(imageKey);
            }
            // s3에 저장된 파일들 삭제
            s3Service.deleteImages(imageKeys);

            cafeRepository.deleteById(id);
            return "삭제 성공";
        }
    }

    /**
     * 어드민페이지용, 전체삭제 메소드
     */
    @Transactional
    public String removeCafeByAdmin(Long id) {
        Cafe cafe = cafeRepository.getById(id);

        LocalDate now = LocalDate.now();
        // 이벤트 존재 여부 확인
        Event hasEventAfterNow = eventRepository.findTop1ByCafeIdAndEventStartDateAfter(id, now.toString());

        if (hasEventAfterNow != null) {
            return "이벤트 예약내역이 존재하므로 삭제가 불가능합니다.";
        } else {
            List<CafeImage> cafeImages = cafe.getCafeImages();
            List<String> imageKeys = new ArrayList<>();
            for (CafeImage cafeImage : cafeImages) {
                // 3번째 '/'의 위치를 찾아서 +1 번째 부터 문자열 반환받아 key값으로 사용
                int location = cafeImage.getCafeImageUrl().indexOf("/", 10);
                String imageKey = cafeImage.getCafeImageUrl().substring(location + 1);
                imageKeys.add(imageKey);
            }
            // s3에 저장된 파일들 삭제
            s3Service.deleteImages(imageKeys);

            cafeRepository.deleteById(id);
            return "삭제 성공";
        }
    }

    public List<CafeCalenderInfoResponseDto> findEventListForCalenderByCafeId(Long id) {
        List<Event> eventList = eventRepository.findALLByCafeId(id);

        List<CafeCalenderInfoResponseDto> cafeCalenderInfoResponseDtos = eventList.stream()
                .map(e -> {
                    try {
                        return new CafeCalenderInfoResponseDto(e);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());

        return cafeCalenderInfoResponseDtos;
    }

    // 리뷰등록
    @Transactional
    public void saveCafeReview(CafeReviewRequestDto requestDto, Long cafeNumber, User securityUser) {
        User user = userRepoistory.getById(securityUser.getId());
        Cafe cafe = cafeRepository.findById(cafeNumber).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage())
        );

        requestDto.setReviewContent(XssPreventer.escape(requestDto.getReviewContent()));
        CafeReview cafeReview = new CafeReview(requestDto);
        user.addCafeReview(cafeReview);
        cafe.addCafeReview(cafeReview);

        int newCafeReviewScore = cafe.getCafeReviewScore() + requestDto.getReviewRating();
        cafe.updateCafeReviewScore(newCafeReviewScore);

        cafeReviewRepository.save(cafeReview);
    }

    public Page<CafeReviewResponseDto> findCafeReviewListByCafeId(Long cafeNumber, int page, int size, String sortStrategyKey) {

        SortStrategy sortStrategy = sortStrategyMap.get(sortStrategyKey);
        Sort sort = sortStrategy.sort();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<CafeReview> all = cafeReviewRepository.findAllByCafeId(cafeNumber, pageable);
//        return all.map(CafeReview -> new CafeReviewResponseDto(CafeReview));
        return all.map(CafeReviewResponseDto::new);
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void removeCafeReviewByReviewId(Long id) {
        CafeReview reviewById = cafeReviewRepository.getById(id);
        Cafe cafe = reviewById.getCafe();
        int newCafeReviewScore = cafe.getCafeReviewScore() - cafeReviewRepository.getById(id).getCafeReviewRating();
        cafe.updateCafeReviewScore(newCafeReviewScore);
        cafeReviewRepository.deleteById(id);
    }

    /**
     * 카페에 등록된 모든 일정을 조회하는 메소드
     */
    public ArrayList<String> AllReservationListByCafe(Long cafeId) throws ParseException {
        List<Event> eventList = eventRepository.findALLByCafeId(cafeId);
        List<CafeSchedule> scheduleList = cafeScheduleRepository.findAllByCafeId(cafeId);
        ArrayList<String> dates = eventAndScheduleFromCafe(eventList, scheduleList);

        return dates;
    }

    /**
     * 카페에 저장 되어 있는 일정목록을 전부 리스트에 담는 메소드
     */
    public ArrayList<String> eventAndScheduleFromCafe(@NotNull List<Event> eventList, List<CafeSchedule> cafeScheduleList) throws ParseException {
        final String DATE_PATTERN = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < eventList.size(); i++) {
            String inputEventStartDate = eventList.get(i).getEventStartDate();
            String inputEventEndDate = eventList.get(i).getEventEndDate();

            Date startDate = sdf.parse(inputEventStartDate);
            Date endDate = sdf.parse(inputEventEndDate);
            Date currentEvent = startDate;

            while (currentEvent.compareTo(endDate) <= 0) {
                dates.add(sdf.format(currentEvent));
                Calendar c = Calendar.getInstance();
                c.setTime(currentEvent);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentEvent = c.getTime();
            }
        }
        for (int i = 0; i < cafeScheduleList.size(); i++) {
            String inputScheduleStart = cafeScheduleList.get(i).getCafeScheduleStartDate();
            String inputScheduleEnd = cafeScheduleList.get(i).getCafeScheduleEndDate();

            Date startDate = sdf.parse(inputScheduleStart);
            Date endDate = sdf.parse(inputScheduleEnd);
            Date currentSchedule = startDate;

            while (currentSchedule.compareTo(endDate) <= 0) {
                dates.add(sdf.format(currentSchedule));
                Calendar c = Calendar.getInstance();
                c.setTime(currentSchedule);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentSchedule = c.getTime();
            }
        }
        return dates;
    }

    @Transactional
    public void saveCafeBookmark(Long id, User loginUser) {
        User user = userRepoistory.getById(loginUser.getId());
        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage()));

        CafeBookmark cafeBookmark = CafeBookmark.builder()
                .user(user)
                .cafe(cafe)
                .build();
        cafeBookmarkRepository.save(cafeBookmark);
    }

    @Transactional
    public void removeCafeBookmark(Long cafeId, User loginUser) {
        cafeBookmarkRepository.deleteByCafeIdAndUserId(cafeId, loginUser.getId());
    }
}
