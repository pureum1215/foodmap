package kr.map.food.service.apiData;

public class AddressHelper {

    // null값 찾기
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private Integer parseIntSafe(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Integer.parseInt(value.trim());
        } 
        catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDoubleSafe(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Double.parseDouble(value.trim());
        } 
        catch (NumberFormatException e) {
            return null;
        }
    }

    // 구 설정
    private String parseGu(String addr) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 2 ? parts[1] : "";
    }

    // 동 설정
    private String parseDong(String addr) {
        if (addr == null || addr.isBlank()) return "";
        String[] parts = addr.trim().split(" ");
        return parts.length >= 3 ? parts[2] : "";
    }
    
}
