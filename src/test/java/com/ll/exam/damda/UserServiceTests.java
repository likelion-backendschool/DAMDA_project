package com.ll.exam.damda;

import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.user.UserRepository;
import com.ll.exam.damda.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /*//    @BeforeEach
    private void beforeEach(){
        userRepository.truncate();
    }
    @Test
    @DisplayName("truncate")
    public void t0() {
        userRepository.truncate();
    }

    @Test
    @DisplayName("유저 생성 기능")
    public void t1() throws SignupNicknameDuplicatedException {
        userService.create("user1","nick1","user1@email.com", "1234");
        userService.create("user2","nick2","user2@email.com", "1234");
    }

    @Test
    @DisplayName("유저 생성 기능2")
    public void t2() throws SignupNicknameDuplicatedException {
        userService.create("user1","nick1","user1@email.com", "1234");
        userService.create("user2","nick2","user2@email.com", "1234");
        userService.create("user3","nick3","user3@email.com", "1234");
        userService.create("user4","nick4","user4@email.com", "1234");
    }

    @Test
    @DisplayName("유저 정보 수정 기능")
    public void t3(){
        SiteUser user = userService.getUser("user1");
        userService.edit(user,"user1_1","user1_1@email.com", "1234");
    }*/
}