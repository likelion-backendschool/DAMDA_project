package com.ll.exam.damda.controller.user;

import com.ll.exam.damda.config.user.SignupEmailDuplicatedException;
import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.config.user.SignupUsernameDuplicatedException;
import com.ll.exam.damda.dto.user.MailDto;
import com.ll.exam.damda.dto.user.MessageDto;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.form.user.*;
import com.ll.exam.damda.service.user.MailService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

import static com.ll.exam.damda.util.Util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    // 회원가입
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(),
                    userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "user/signup_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "user/signup_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "user/signup_form";
        }
        return "redirect:/user/login";
    }

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "user/login_form";
    }

    @GetMapping("/access")
    public String user() {
        return "user/login_form";
    }


    // 정보수정용 마이페이지 (소셜로그인 X)
    @GetMapping("/my_page")
    public String mypage(Principal principal, UserEditForm userEditForm) {
        SiteUser siteUser = userService.getUser(principal.getName());

        userEditForm.setNickname(siteUser.getNickname());
        userEditForm.setEmail(siteUser.getEmail());
        return "user/my_page_form";
    }

    @PostMapping("/my_page")
    public String mypage(Principal principal, Model model, @Valid UserEditForm userEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/my_page_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());

        if (!userEditForm.getPassword().equals(userEditForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/my_page_form";
        }

        try {
            userService.edit(siteUser, userEditForm.getNickname(), userEditForm.getEmail(), userEditForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "user/my_page_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupNicknameDuplicated", e.getMessage());
            return "user/my_page_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "user/my_page_form";
        }

        MessageDto message = new MessageDto("정보 변경이 완료되었습니다. 다시 로그인해주세요.", "/user/logout", RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    // 정보수정용 마이페이지 (소셜로그인 O)
    @GetMapping("/my_page_social")
    public String mypage_social(Principal principal, UserEditForm_social userEditForm_social) {
        SiteUser siteUser = userService.getUser(principal.getName());

        userEditForm_social.setNickname(siteUser.getNickname());

        return "user/my_page_form_social";

    }

    @PostMapping("/my_page_social")
    public String mypage_social(Principal principal, Model model, @Valid UserEditForm_social userEditForm_social, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/my_page_form_social";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        try {
            userService.edit(siteUser, userEditForm_social.getNickname());
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupNicknameDuplicated", e.getMessage());
            return "user/my_page_form_social";
        }

        MessageDto message = new MessageDto("정보 변경이 완료되었습니다. 다시 로그인해주세요.", "/user/logout", RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    // 아이디 찾기
    @GetMapping("/find_id")
    public String findid(FindIdForm findIdForm) {
        return "user/find_id_form";
    }

    @PostMapping("/find_id")
    public String findid(Model model, @Valid FindIdForm findIdForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/find_id_form";
        }
        String alert = "일치하는 아이디를 찾을 수 없습니다.";
        String redirectUri = "/user/find_id";
        if (userService.getUserRepository().findByEmail(findIdForm.getEmail()) != null) {
            String findId = userService.getUserRepository().findByEmail(findIdForm.getEmail()).getUsername();
            alert = "아이디는 " + masking(findId) + "입니다.";
            redirectUri = "/user/login";
        }
        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    // 비밀번호 찾기
    @GetMapping("/find_pw")
    public String findpw(FindPwForm findPwForm) {
        return "user/find_pw_form";
    }

    @PostMapping("/find_pw")
    public String findpw(Model model, @Valid FindPwForm findPwForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/find_pw_form";
        }

        String alert = "일치하는 아이디를 찾을 수 없습니다.";
        String redirectUri = "/user/find_pw";
        if (userService.getUserRepository().findByUsernameAndEmail(findPwForm.getUsername(), findPwForm.getEmail()) != null) {
            SiteUser user = userService.getUserRepository().findByUsernameAndEmail(findPwForm.getUsername(), findPwForm.getEmail());
            String newPw = getRandomText(10);
            String findPwMsg = "임시 비밀번호는 " + newPw + " 입니다.";
            userService.edit_password(user, newPw);

            MailDto mailDto = new MailDto();
            mailDto.setAddress(findPwForm.getEmail());
            mailDto.setTitle("DAMDA 임시 비밀번호 발급입니다");
            mailDto.setMessage(findPwMsg);
            mailService.mailSend(mailDto);

            alert = "임시 비밀번호가 이메일로 발송되었습니다";
            redirectUri = "/user/login";
        }

        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }
}