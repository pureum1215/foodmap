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
    
    private final RestaurantApiCollector collector;
    private final RestaurantApiDataMapper restaurantMapper;

    public RestaurantApiDataService(
        RestaurantApiCollector collector,
        RestaurantApiDataMapper restaurantMapper
    ) {
        this.collector = collector;
        this.restaurantMapper = restaurantMapper;
    }

    public void collectAllGuData() {
        // 서울시 전체 구 코드 리스트
        String[] guCodes = {
            "LOCALDATA_072404_YC", // 양천구
            "LOCALDATA_072404_GG", // 강남구
            "LOCALDATA_072404_GD", // 강동구
            // ...
            // 필요하면 전부 추가
        };

        for (String code : guCodes) {
            List<RestaurantRawDTO> rawList = collector.collect(code);
            for (RestaurantRawDTO raw : rawList) {
                if (AddressHelper.isEmpty(raw.getSITEWHLADDR()) && AddressHelper.isEmpty(raw.getRDNWHLADDR())) {
                    continue;
                }

                RestaurantDTO dto = buildRestaurant(raw);
                restaurantMapper.insertRestaurant(dto);
            }
        }
    }

    private RestaurantDTO buildRestaurant(RestaurantRawDTO raw) {
        RestaurantDTO r = new RestaurantDTO();
        r.setResIdx(raw.getMGTNO());
        r.setResName(raw.getBPLCNM());
        r.setResRun(AddressHelper.parseIntSafe(raw.getDTLSTATEGBN()));
        r.setResNum(raw.getSITETEL());
        r.setTypeIdx(1); // 업태구분은 임시
        r.setResCleanScore(raw.getLVSENM());
        r.setADDRGU(AddressHelper.parseGu(raw.getSITEWHLADDR()));
        r.setADDRDONG(AddressHelper.parseDong(raw.getSITEWHLADDR()));
        r.setOLDADDR(raw.getSITEWHLADDR());
        r.setNEWADDR(raw.getRDNWHLADDR());
        r.setNUMADDR(AddressHelper.parseIntSafe(raw.getRDNPOSTNO()));
        r.setXPOS(AddressHelper.parseDoubleSafe(raw.getX()));
        r.setYPOS(AddressHelper.parseDoubleSafe(raw.getY()));
        return r;
    }
    
}
