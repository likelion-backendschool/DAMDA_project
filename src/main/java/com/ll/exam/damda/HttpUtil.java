package com.ll.exam.damda;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static List<String> getSpotImgUrl(String spotName) {
        List<String> imgUrlList = new ArrayList<>();

        try {
            URL url = new URL("https://dapi.kakao.com/v2/search/image?query=" + URLEncoder.encode(spotName, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setRequestProperty("Authorization", "KakaoAK cf370f1373a90b3068542f420c680f61"); // header의 auth 정보
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

            for (int i = 0; i < documents.length(); i++) {
                JSONObject element = (JSONObject) documents.opt(i);
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
