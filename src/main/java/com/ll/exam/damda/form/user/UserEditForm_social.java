package com.ll.exam.damda.form.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserEditForm_social {

    @NotEmpty(message = "닉네임을 입력해주세요")
    private String nickname;
}
