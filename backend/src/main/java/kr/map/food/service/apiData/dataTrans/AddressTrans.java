package kr.map.food.service.apiData;

public class AddressTrans {

    // 구 설정
    public static String parseGu(String addr) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 2 ? parts[1] : "";
    }

    // 동 설정
    public static String parseDong(String addr) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 3 ? parts[2] : "";
    }

    
    // 지번주소 없음 -> 도로명주소를 지번주소로 변경

    
    // 도로명주소 없음 -> 지번주소를 도로명주소로 변경

    
    // 우편번호 없음 -> 도로명주소로 우편번호 가져오기


    // x, y 좌표 없음 -> 도로명주소로 x,y 좌표값 가져오기
    
    
}
