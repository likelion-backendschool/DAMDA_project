package com.ll.exam.damda.service.user;

import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SiteUser create(String nickname, String email, String password) {
        SiteUser user = new SiteUser();
        user.setNickname(nickname);
        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        user.setMethod("d"); // d : default, n : naver, g : google, k : kakao
        user.setCreateDate(LocalDateTime.now());
        this.userRepository.save(user);
        return user;
    }
}