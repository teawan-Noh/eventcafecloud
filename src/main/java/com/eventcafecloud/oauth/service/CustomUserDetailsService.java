package com.eventcafecloud.oauth.service;

import com.eventcafecloud.oauth.domain.UserPrincipal;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(username).orElseThrow();
        //todo orElseThorw() 메세지 추가하기
//        if(user == null){
//            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
//        }
        return UserPrincipal.create(user);
    }
}
