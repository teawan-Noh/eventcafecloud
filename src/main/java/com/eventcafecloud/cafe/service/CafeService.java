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

    @Transactional
    public void saveCafe(CafeCreateRequestDto requestDto, User securityUser) {
        User user = userRepoistory.getById(securityUser.getId());
        Cafe cafe = new Cafe(requestDto);
        user.addCafe(cafe);

        List<MultipartFile> files = requestDto.getFiles();
        // s3?????? ??? url ????????????
        List<String> cafeImageUrlList = s3Service.upload(files, "cafeImage");

        // ?????? ????????? ??????
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String cafeImageUrl = cafeImageUrlList.get(i);
            CafeImage cafeImage = new CafeImage(file.getOriginalFilename(), cafeImageUrl);
            cafe.addCafeImage(cafeImage);
        }

        // ?????? ?????? ?????????
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
            // like ?????? Containing
        }
//        return all.map(cafe -> new CafeListResponseDto(cafe));
        // ?????? ????????? ???????????? ????????? ????????? ??????
        return all.map(CafeListResponseDto::new);
    }

    /**
     * ?????? ????????? for ???????????????
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

    public CafeDetailResponseDto findCafeByIdForDetail(Long id) {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage()));

//        System.out.println((double) (cafe.getCafeReviewScore() / cafe.getCafeReviews().size()));

        return new CafeDetailResponseDto(cafe);
    }

    public CafeUpdateRequestDto findCafeByIdForUpdateForm(Long id) {

        Cafe cafe = cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage()));

        // Builder pattern?????? ???????????????
        return CafeUpdateRequestDto.toDto(cafe);
//        return new CafeUpdateRequestDto(cafe);
    }

    // ?????? ??????
    @Transactional
    public void modifyCafe(Long id, CafeUpdateRequestDto requestDto) {
        Cafe cafe = cafeRepository.getById(id);
        cafe.updateCafeInfo(requestDto);

        if (requestDto.getCafeOptions() != null) {
            // db??? ????????? ????????? ??????
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
                // 3?????? '/'??? ????????? ????????? +1 ?????? ?????? ????????? ???????????? key????????? ??????
                int location = cafeImage.getCafeImageUrl().indexOf("/", 10);
                String imageKey = cafeImage.getCafeImageUrl().substring(location + 1);
                imageKeys.add(imageKey);
            }
            cafeImageRepository.deleteAllInBatch(cafeImages);

            List<MultipartFile> files = requestDto.getFiles();
            // s3?????? ??? url ????????????
            List<String> cafeImageUrlList = s3Service.reupload(files, "cafeImage", imageKeys);

            // ?????? ????????? ??????
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String cafeImageUrl = cafeImageUrlList.get(i);
                CafeImage cafeImage = new CafeImage(file.getOriginalFilename(), cafeImageUrl);
                cafe.addCafeImage(cafeImage);
            }
        }
    }

    /**
     * ?????? ??????
     * ?????? ????????? s3??? ????????? ?????? ?????? ??????
     * ?????? ?????? ?????? ?????? ???????????? ???????????? ????????? ?????? ??????
     */
    @Transactional
    public String removeCafe(Long id) {
        Cafe cafe = cafeRepository.getById(id);
        LocalDate now = LocalDate.now();
        // ????????? ?????? ?????? ??????
        Event hasEventAfterNow = eventRepository.findTop1ByCafeIdAndEventStartDateAfter(id, now.toString());

        if (hasEventAfterNow != null) {
            return "????????? ??????????????? ??????????????? ????????? ??????????????????.";
        } else {
            List<CafeImage> cafeImages = cafe.getCafeImages();
            List<String> imageKeys = new ArrayList<>();
            for (CafeImage cafeImage : cafeImages) {
                // 3?????? '/'??? ????????? ????????? +1 ?????? ?????? ????????? ???????????? key????????? ??????
                int location = cafeImage.getCafeImageUrl().indexOf("/", 10);
                String imageKey = cafeImage.getCafeImageUrl().substring(location + 1);
                imageKeys.add(imageKey);
            }
            // s3??? ????????? ????????? ??????
            s3Service.deleteImages(imageKeys);

            cafeRepository.deleteById(id);
            return "?????? ??????";
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

    // ????????????
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
     * ?????? ??????
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
     * ????????? ????????? ?????? ????????? ???????????? ?????????
     */
    public ArrayList<String> AllReservationListByCafe(Long cafeId) throws ParseException {
        List<Event> eventList = eventRepository.findALLByCafeId(cafeId);
        List<CafeSchedule> scheduleList = cafeScheduleRepository.findAllByCafeId(cafeId);
        ArrayList<String> dates = eventAndScheduleFromCafe(eventList, scheduleList);

        return dates;
    }

    /**
     * ????????? ?????? ?????? ?????? ??????????????? ?????? ???????????? ?????? ?????????
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
}
