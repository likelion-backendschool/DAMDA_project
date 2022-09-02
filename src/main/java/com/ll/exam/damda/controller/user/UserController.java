package com.ll.exam.damda.controller.user;

import com.ll.exam.damda.config.user.SignupEmailDuplicatedException;
import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.config.user.SignupUsernameDuplicatedException;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.form.user.UserCreateForm;
import com.ll.exam.damda.form.user.UserEditForm;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(),
                    userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/access")
    public String user() {
        return "user_access";
    }


    @GetMapping("/my_page")
    public String mypage(Principal principal, UserEditForm userEditForm) {
        SiteUser siteUser = userService.getUser(principal.getName());

        userEditForm.setNickname(siteUser.getNickname());
        userEditForm.setEmail(siteUser.getEmail());

        return "my_page_form";
    }

    @PostMapping("/my_page")
    public String mypage(Principal principal, @Valid UserEditForm userEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "my_page_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());

        if (!userEditForm.getPassword().equals(userEditForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "my_page_form";
        }

        try {
            userService.edit(siteUser, userEditForm.getNickname(), userEditForm.getEmail(), userEditForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "my_page_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "my_page_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "my_page_form";
        }
        return "redirect:/user/my_page";
    }
}