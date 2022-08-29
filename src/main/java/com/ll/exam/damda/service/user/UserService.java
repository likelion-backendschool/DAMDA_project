package com.ll.exam.damda.service.user;

import com.ll.exam.damda.config.user.DataNotFoundException;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.config.user.SignupEmailDuplicatedException;
import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.config.user.SignupUsernameDuplicatedException;
import com.ll.exam.damda.repository.user.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String nickname, String email, String password) throws SignupUsernameDuplicatedException, SignupNicknameDuplicatedException, SignupEmailDuplicatedException {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setMethod("d"); // d : default, n : naver, g : google, k : kakao
        user.setCreateDate(LocalDateTime.now());
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (userRepository.existsByUsername(username)) {
                throw new SignupUsernameDuplicatedException("이미 사용중인 아이디 입니다.");
            } else if (userRepository.existsByNickname(nickname)) {
                throw new SignupEmailDuplicatedException("이미 사용중인 닉네임 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }
        return user;
    }

    public void edit(SiteUser user, String nickname, String email, String password) {
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public SiteUser getUser(String username) {
        return (SiteUser) this.userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }
}