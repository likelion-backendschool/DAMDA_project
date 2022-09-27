package com.ll.exam.damda.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address; // 메일을 보낼 주소
    private String title; // 메일의 제목
    private String message; // 메일의 내용
}