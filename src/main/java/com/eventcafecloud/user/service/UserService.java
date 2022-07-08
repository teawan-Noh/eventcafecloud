package com.eventcafecloud.user.service;

import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.HostUser;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.ApproveType;
import com.eventcafecloud.user.dto.HostUserCreateRequestDto;
import com.eventcafecloud.user.dto.HostUserResponseDto;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.dto.UserResponseDto;
import com.eventcafecloud.user.repository.HostUserRepository;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HostUserRepository hostUserRepository;
    private final S3Service s3Service;

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
    public void modifyUserRoleAndStatus(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        user.updateUserRoleAndUserStatus(requestDto);
    }

    @Transactional
    public void modifyUserProfile(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        MultipartFile file = requestDto.getUserImage();
        String userImage = s3Service.upload(file, "userProfileImage");
        user.updateProfile(requestDto, userImage);
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

    public List<UserResponseDto> getUserList() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserResponseDto> result = new ArrayList<>();

        for (User user : users) {
            UserResponseDto responseDto = new UserResponseDto();
            responseDto.setUserNumber(user.getId());
            responseDto.setUserEmail(user.getUserEmail());
            responseDto.setUserNickname(user.getUserNickname());
            responseDto.setUserRegPath(user.getUserRegPath().getDisplayName());
            responseDto.setCreatedDate(user.getCreatedDate());
            responseDto.setModifiedDate(user.getModifiedDate());
            responseDto.setRole(user.getRole().getDisplayName());
            responseDto.setStatus(user.getUserStatus().getDisplayName());
            result.add(responseDto);

            responseDto.getRole();
        }
        return result;
    }
}
