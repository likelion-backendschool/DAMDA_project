package com.ll.exam.damda.controller.search.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ReviewForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 50, message = "제목을 50자 이하로 입력해주세요.")
    private String title;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
    @NotEmpty(message = "태그는 필수항목입니다.")
    private String reviewTags;



}
