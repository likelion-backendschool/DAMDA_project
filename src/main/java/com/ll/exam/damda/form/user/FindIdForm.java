package com.ll.exam.damda.form.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FindIdForm {

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식으로 입력해주세요")
    private String email;
}
