package kr.map.food.service.apiData.BestRestaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import kr.map.food.domain.apiData.BestRestaurant.BestRestaurantRawDTO;
import kr.map.food.domain.apiData.Restaurant.RestaurantApiResponse;

public class BestRestaurantApiCollector {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<BestRestaurantRawDTO> collect( String guURL, String guCode ) {
        List<BestRestaurantRawDTO> rawList = new ArrayList<>();

        int page = 1;

        String url = String.format(
            "%s/464850745570757236334247635442/xml/%s/1/1/",
            guURL, guCode
        );

        RestaurantApiResponse response = restTemplate.getForObject(url, RestaurantApiResponse.class);

        int listTotalCount = response.getListTotalCount();
        int totalPage = (listTotalCount + 1000 - 1) / 1000;

        while (page <= totalPage) {
            int fromNum = (page - 1) * 1000 + 1;
            int toNum = (page) * 1000;

            String pageUrl = String.format(
                "%s/464850745570757236334247635442/xml/%s/%d/%d/",
                guURL, guCode, fromNum, toNum
            );

            RestaurantApiResponse pageResponse = restTemplate.getForObject(pageUrl, RestaurantApiResponse.class);
            List<BestRestaurantRawDTO> rows = pageResponse.getRow();

            if ( rows == null || rows.isEmpty() ) {
                break;
            }

            rawList.addAll(rows);
            page++;

        }    

        return rawList;

    }
    
}
