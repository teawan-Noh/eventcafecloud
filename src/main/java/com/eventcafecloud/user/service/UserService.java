package com.eventcafecloud.user.service;

import com.eventcafecloud.user.domain.HostUser;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.ApproveType;
import com.eventcafecloud.user.dto.HostUserCreateRequestDto;
import com.eventcafecloud.user.dto.HostUserResponseDto;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.repository.HostUserRepository;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HostUserRepository hostUserRepository;

    public User getUserByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        return user;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        return user;
    }

    @Transactional
    public void saveHostUser(HostUserCreateRequestDto hostUserCreateRequestDto) {

        User user = userRepository.findByUserEmail(hostUserCreateRequestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));

        HostUser hostUser = HostUser.builder()
                .userEmail(hostUserCreateRequestDto.getUserEmail())
                .certificationFile(hostUserCreateRequestDto.getCertificationFile())
                .isApprove(ApproveType.WAITING)
                .build();

        user.registHost(hostUser);
    }

    @Transactional
    public void modifyUserProfile(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        //todo 유저 프로필 이미지 s3 과정과 연동하기
        user.updateProfile(requestDto);
    }

    @Transactional
    public void modifyNormalUserToHostUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        user.updateNormalUserToHostUser(user);
    }

    @Transactional
    public void approveIsFail(Long id) {
        HostUser hostUser = hostUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        hostUser.updateApprove(ApproveType.FAIL);
    }

    public List<HostUserResponseDto> getHostUserList() {
        List<HostUser> hostUsers = hostUserRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<HostUserResponseDto> result = new ArrayList<>();

        for (HostUser hostUser : hostUsers) {
            HostUserResponseDto responseDto = new HostUserResponseDto();
            responseDto.setHost_user_number(hostUser.getId());
            responseDto.setUserEmail(hostUser.getUserEmail());
            responseDto.setCertificationFile(hostUser.getCertificationFile());
            responseDto.setIsApprove(hostUser.getIsApprove());
            responseDto.setUser_number(hostUser.getUser().getId());
            responseDto.setCreated_date(hostUser.getCreatedDate());
            result.add(responseDto);
        }
        return result;
    }
}
