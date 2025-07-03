package kr.map.food.service.apiData;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.map.food.domain.apiData.RestaurantDTO;
import kr.map.food.domain.apiData.RestaurantRawDTO;
import kr.map.food.domain.util.ApiResponse;
import kr.map.food.mapper.apiData.RestaurantApiDataMapper;

@Service
public class RestaurantApiDataService {
    
    private final RestaurantApiDataMapper restaurantMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public RestaurantApiDataService( RestaurantApiDataMapper restaurantMapper ) {
        this.restaurantMapper = restaurantMapper;
    }

    public void CollectApiDataRestaurant() {

        int page = 1;
    
        String url = String.format(
            "http://openapi.seoul.go.kr:8088/464850745570757236334247635442/xml/LOCALDATA_072404_YC/1/1");
        
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        
        int listTotalCount = response.getListTotalCount();
        int totalPage = ( listTotalCount + 1000 - 1 ) / 1000;

        while ( page <= totalPage ) {
        
            int fromNum = ( page - 1 ) * 1000 + 1;
            int toNum = ( page ) * 1000;
        
            url = String.format(
                "http://openapi.seoul.go.kr:8088/464850745570757236334247635442/xml/LOCALDATA_072404_YC/%d/%d",
                fromNum, toNum
            );
            
            response = restTemplate.getForObject(url, ApiResponse.class);

            List<RestaurantRawDTO> items = response.getRow();

            if (items == null || items.isEmpty()) {
                break;
            }

            for (RestaurantRawDTO raw : items) {
                
                // 필터 부분
                // 주소가 없을 때
                if (isEmpty(raw.getSITEWHLADDR()) && isEmpty(raw.getRDNWHLADDR())) {
                    continue;
                }
                
                // 지번주소 없음 -> 도로명주소를 지번주소로 변경
                if ( isEmpty( raw.getSITEWHLADDR() ) ) {
                    
                }
                
                // 도로명주소 없음 -> 지번주소를 도로명주소로 변경
                // 우편번호 없음 -> 도로명주소로 우편번호 가져오기
                // x, y 좌표 없음 -> 도로명주소로 x,y 좌표값 가져오기
                // 업태 구분 -> RESTAURANTTYPE TABLE에서 업태구분명과 일치하는 IDX 입력
                int typeIdx = 1;

                // 레스토랑 엔티티 매핑
                RestaurantDTO r = new RestaurantDTO();
                r.setResIdx(raw.getMGTNO());
                r.setResName(raw.getBPLCNM());
                r.setResRun(parseIntSafe(raw.getDTLSTATEGBN()));
                r.setResNum(raw.getSITETEL());
                r.setTypeIdx(typeIdx);
                r.setResCleanScore(raw.getLVSENM());
                r.setADDRGU(parseGu(raw.getSITEWHLADDR()));
                r.setADDRDONG(parseDong(raw.getSITEWHLADDR()));
                r.setOLDADDR(raw.getSITEWHLADDR());
                r.setNEWADDR(raw.getRDNWHLADDR());
                r.setNUMADDR(parseIntSafe(raw.getRDNPOSTNO()));
                r.setXPOS(parseDoubleSafe(raw.getX()));
                r.setYPOS(parseDoubleSafe(raw.getY()));

                // insert
                //restaurantMapper.insertRestaurant(r);
            }

            page++;
        }
    }

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
