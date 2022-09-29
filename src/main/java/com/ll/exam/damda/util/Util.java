package com.ll.exam.damda.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.damda.dto.user.MessageDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    // 길이가 max인 랜덤 문자열 반환
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

    // 알람을 띄우고 페이지 리다이렉트
    public static String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "user/messageRedirect";
    }

    // 3자리 이후 *로 마스킹
    public static String masking(String str){
        return str.replaceAll("(?<=.{3}).(?=.*)", "*");
    }

    public static List<String> getSpotImgUrl(String spotName, String city) {
        List<String> imgUrlList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            try {
                URL url = new URL("https://dapi.kakao.com/v2/search/image?query=" + URLEncoder.encode(spotName + " " + city, "UTF-8") + "&page=" + i);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setRequestMethod("GET"); // http 메서드
                conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
                conn.setRequestProperty("Authorization", "KakaoAK eca47217b689466a922e370c3a6c9ded"); // header의 auth 정보
                conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

                // 서버로부터 데이터 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                    sb.append(line);
                }

                JSONObject obj = new JSONObject(sb.toString()); // json으로 변경 (역직렬화)

                /* CORS 없는 이미지 url만 거름 */
                JSONArray documents = obj.getJSONArray("documents");

                String[] goodUrls = {"kakaocdn"};
                boolean CORS = true;

                for (int j = 0; j < documents.length(); j++) {
                    JSONObject element = (JSONObject) documents.opt(j);
                    String imgUrl = (String) element.getString("image_url");

                    for (String goodUrl : goodUrls) {
                        if (imgUrl.contains(goodUrl)) CORS = false;
                    }

                    if (CORS == false) imgUrlList.add(imgUrl);

                    CORS = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imgUrlList;
    }

    public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj){
        Map<String, Object> map = null;

        try {
            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }
}