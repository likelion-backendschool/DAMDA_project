package com.ll.exam.damda.service.user;

import com.ll.exam.damda.config.user.DataNotFoundException;
import com.ll.exam.damda.config.user.SignupEmailDuplicatedException;
import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.config.user.SignupUsernameDuplicatedException;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 유저 데이터와 관련된 서비스
@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 생성
    public SiteUser create(String username, String nickname, String email, String password) throws SignupUsernameDuplicatedException, SignupNicknameDuplicatedException, SignupEmailDuplicatedException {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setMethod("d"); // d : default 회원, k : kakao 회원
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

    //  소셜 로그인이 아닌 사용자의 정보 변경
    public void edit(SiteUser user, String nickname, String email, String password) {
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (userRepository.existsByNickname(nickname)) {
                throw new SignupEmailDuplicatedException("이미 사용중인 닉네임 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }
    }

    // 소셜 로그인 사용자의 정보 변경
    public void edit(SiteUser user, String nickname) {
        user.setNickname(nickname);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (userRepository.existsByNickname(nickname)) {
                throw new SignupEmailDuplicatedException("이미 사용중인 닉네임 입니다.");
            }
        }
    }

    // 비밀번호 찾기 시 비밀번호 변경
    public void edit_password(SiteUser user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    // 유저 ID로 유저를 반환
    public SiteUser getUser(String username) {
        return (SiteUser) this.userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

    // 유저로 유저 ID를 반환
    public long getUserId(SiteUser user){
        return user.getId();
    }
}