package kr.map.food.service.apiData;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.map.food.domain.apiData.RestaurantDTO;
import kr.map.food.domain.apiData.RestaurantRawDTO;
import kr.map.food.domain.util.ApiResponse;
import kr.map.food.mapper.apiData.RestaurantApiDataMapper;
import kr.map.food.service.apiData.dataTrans.AddressTrans;
import kr.map.food.service.apiData.dataTrans.FindNullData;

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

        AddressTrans addressTrans = new AddressTrans();

        for (String code : guCodes) {
            List<RestaurantRawDTO> rawList = collector.collect(code);
            for (RestaurantRawDTO raw : rawList) {

                // null값 찾기
                if ( FindNullData.isEmpty( raw.getSITEWHLADDR() ) && FindNullData.isEmpty( raw.getRDNWHLADDR() ) ) {
                    continue;
                }

                RestaurantDTO dto = buildRestaurant(raw);

                if ( FindNullData.isEmpty( raw.getSITEWHLADDR() ) ) {
                    String siteaddr = addressTrans.roadToSite(raw.getRDNWHLADDR());
                    dto.setOLDADDR(siteaddr);
                }

                if ( FindNullData.isEmpty( raw.getRDNWHLADDR() ) ) {
                    String roadaddr = addressTrans.siteToRoad(raw.getSITEWHLADDR());
                    dto.setNEWADDR(roadaddr);
                }

                if ( FindNullData.isEmpty( raw.getRDNPOSTNO() ) && !FindNullData.isEmpty( dto.getNEWADDR() ) ) {
                    String post = addressTrans.getPostCode( dto.getNEWADDR() );
                    dto.setNUMADDR( AddressTrans.parseIntSafe(post) );
                }

                

                if ( FindNullData.isEmpty( raw.getX() ) || FindNullData.isEmpty( raw.getY() ) ) {
                    // String baseAddress = !FindNullData.isEmpty(dto.getNEWADDR()) ? dto.getNEWADDR() : dto.getOLDADDR();
                    // if ( !FindNullData.isEmpty(baseAddress) ) {
                    //     Double[] latlng = addressTrans.addressToLatLng(baseAddress);
                    //     if ( latlng != null ) {
                    //         dto.setYPOS(latlng[0]);
                    //         dto.setXPOS(latlng[1]);
                    //     }
                    // }
                }

                restaurantMapper.insertRestaurant(dto);

            }
        }
    }

    private RestaurantDTO buildRestaurant(RestaurantRawDTO raw) {
        RestaurantDTO r = new RestaurantDTO();
        r.setResIdx(raw.getMGTNO());
        r.setResName(raw.getBPLCNM());
        r.setResRun(AddressTrans.parseIntSafe(raw.getDTLSTATEGBN()));
        r.setResNum(raw.getSITETEL());
        r.setTypeIdx(1); // 업태구분은 임시
        r.setResCleanScore(raw.getLVSENM());
        r.setADDRGU(AddressTrans.parseGu(raw.getSITEWHLADDR()));
        r.setADDRDONG(AddressTrans.parseDong(raw.getSITEWHLADDR()));
        r.setOLDADDR(raw.getSITEWHLADDR());
        r.setNEWADDR(raw.getRDNWHLADDR());
        r.setNUMADDR(AddressTrans.parseIntSafe(raw.getRDNPOSTNO()));
        r.setXPOS(AddressTrans.parseDoubleSafe(raw.getX()));
        r.setYPOS(AddressTrans.parseDoubleSafe(raw.getY()));
        return r;
    }

}
