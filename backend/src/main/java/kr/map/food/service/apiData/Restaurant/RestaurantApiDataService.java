package kr.map.food.service.apiData;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.map.food.domain.apiData.RestaurantDTO;
import kr.map.food.domain.apiData.RestaurantRawDTO;
import kr.map.food.mapper.apiData.RestaurantApiDataMapper;
import kr.map.food.service.apiData.dataTrans.AddressTrans;
import kr.map.food.service.apiData.dataTrans.DataTypeTrans;
import kr.map.food.service.apiData.dataTrans.FindNullData;
import kr.map.food.service.apiData.dataTrans.TelNumTrans;

@Service
public class RestaurantApiDataService {
    
    private final RestaurantApiCollector collector;
    private final RestaurantApiDataMapper restaurantMapper;

    public RestaurantApiDataService( RestaurantApiCollector collector, RestaurantApiDataMapper restaurantMapper ) {
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
            for ( RestaurantRawDTO raw : rawList ) {

                // null값 찾기
                if ( FindNullData.isEmpty( raw.getSITEWHLADDR() ) && FindNullData.isEmpty( raw.getRDNWHLADDR() ) ) {
                    continue;
                }

                RestaurantDTO dto = buildRestaurant(raw);

                // 주소 가공
                if ( FindNullData.isEmpty( raw.getSITEWHLADDR() ) 
                    || FindNullData.isEmpty( raw.getRDNWHLADDR() )
                    || FindNullData.isEmpty( raw.getX() )
                    || FindNullData.isEmpty( raw.getY() )
                    ) {
                    AddressTrans.setAddress( raw, dto );
                }

                // 전화번호 가공
                TelNumTrans.setTelNum( dto.getRESNUM() );

                // restaurantMapper.insertRestaurant(dto);

            }
        }
    }

    private RestaurantDTO buildRestaurant(RestaurantRawDTO raw) {
        RestaurantDTO r = new RestaurantDTO();
        r.setRESIDX(raw.getMGTNO());
        r.setRESNAME(raw.getBPLCNM());
        r.setRESRUN(DataTypeTrans.parseIntSafe(raw.getDTLSTATEGBN()));
        r.setRESNUM(raw.getSITETEL());
        r.setTYPEIDX(1); // 업태구분은 임시
        r.setRESCLEANSCORE(raw.getLVSENM());
        r.setOLDADDR(raw.getSITEWHLADDR());
        r.setNEWADDR(raw.getRDNWHLADDR());
        r.setNUMADDR(AddressTrans.formatPostCode(raw.getRDNPOSTNO()));
        r.setXPOS(DataTypeTrans.parseDoubleSafe(raw.getX()));
        r.setYPOS(DataTypeTrans.parseDoubleSafe(raw.getY()));
        return r;
    }

}
