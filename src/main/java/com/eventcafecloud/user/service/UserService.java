package com.eventcafecloud.user.service;

import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.HostUser;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.ApproveType;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.dto.HostUserCreateRequestDto;
import com.eventcafecloud.user.dto.HostUserResponseDto;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.dto.UserResponseDto;
import com.eventcafecloud.user.repository.HostUserRepository;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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

    public User findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        return user;
    }

    @Transactional
    public void saveHostUser(HostUserCreateRequestDto hostUserCreateRequestDto) {

        User user = userRepository.findByUserEmail(hostUserCreateRequestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));

        Optional<HostUser> checkUser = hostUserRepository.findByUserEmail(hostUserCreateRequestDto.getUserEmail());

        if (checkUser.isEmpty()) {
            HostUser hostUser = HostUser.builder()
                    .userEmail(hostUserCreateRequestDto.getUserEmail())
                    .certificationFile(hostUserCreateRequestDto.getCertificationFile())
                    .isApprove(ApproveType.WAITING)
                    .build();

            user.registHost(hostUser);
        } else {
            checkUser.get().updateIsApprove();
        }
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

    public Page<UserResponseDto> findAllUserList(RoleType roleType, Pageable pageable) {

        Page<User> userList;

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 15);

        if (roleType == null) {
            userList = userRepository.findAll(pageable);
        } else {
            userList = userRepository.findAllByRole(roleType, pageable);
        }

        return userList.map(UserResponseDto::new);
    }

    public Page<HostUserResponseDto> findAllHostUserList(ApproveType approveType, Pageable pageable) {

        Page<HostUser> hostList;

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 15);

        if (approveType == null) {
            hostList = hostUserRepository.findAll(pageable);
        } else {
            hostList = hostUserRepository.findAllByIsApprove(approveType, pageable);
        }

        return hostList.map(HostUserResponseDto::new);
    }
}
