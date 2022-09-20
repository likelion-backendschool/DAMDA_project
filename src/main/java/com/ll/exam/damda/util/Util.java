package com.ll.exam.damda.util;

public class Util {
    public static String getRandomText(int max) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 max개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < max; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}