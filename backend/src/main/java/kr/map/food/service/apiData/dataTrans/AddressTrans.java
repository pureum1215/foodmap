package kr.map.food.service.apiData.dataTrans;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AddressTrans {

    private static final String KAKAO_API_KEY = "KakaoAK {991e8640696acefd76255f05e2328d69}";

    private static final RestTemplate restTemplate = new RestTemplate();

    // 구 설정
    public static String parseGu( String addr ) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 2 ? parts[1] : "";
    }

    // 동 설정
    public static String parseDong( String addr ) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 3 ? parts[2] : "";
    }





    // 우편번호 앞에 0 추가

    
    // 지번주소 없음 -> 도로명주소를 지번주소로 변경
    // public static String roadToSite ( String roadAddress ) {
    //     String url = UriComponentsBuilder.fromUriString("https://dapi.kakao.com/v2/local/convert/addr2coord.json")
    //             .queryParam("query", roadAddress)
    //             .build()
    //             .toString();

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", KAKAO_API_KEY);
    //     HttpEntity<String> entity = new HttpEntity<>(headers);

    //     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

    //     JSONObject body = new JSONObject(response.getBody());
    //     JSONArray documents = body.getJSONArray("documents");
    //     if (documents.isEmpty()) return "";

    //     JSONObject doc = documents.getJSONObject(0);
    //     return doc.getString("address_name");
    // }
    
    // 도로명주소 없음 -> 지번주소를 도로명주소로 변경
    // public static String siteToRoad ( String siteAddress ) {
    //     String roadAddress = ;
    //     return roadAddress;
    // }

    // // 우편번호 없음 -> 도로명주소로 우편번호 가져오기
    // public static String getPostCode ( String roadAddress ) {
    //     String postNum = ;
    //     return postNum;
    // }

    // x, y 좌표 -> 위도, 경도로 변환
    

    // x, y 좌표 없음 -> 도로명주소로 위도, 경도 가져오기
    
    
}
