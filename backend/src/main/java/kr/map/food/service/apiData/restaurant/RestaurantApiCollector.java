package kr.map.food.service.apiData.Restaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import kr.map.food.domain.apiData.Restaurant.GuApiInfoENUM;
import kr.map.food.domain.apiData.Restaurant.RestaurantApiResponse;
import kr.map.food.domain.apiData.Restaurant.RestaurantRawDTO;

public class RestaurantApiCollector {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RestaurantRawDTO> collect( GuApiInfoENUM guInfo, String apiKey ) {
        List<RestaurantRawDTO> rawList = new ArrayList<>();

        int page = 1;
        
        String url = String.format(
            "%s/%s/xml/%s/1/1",
            guInfo.getBaseUrl(),
            apiKey,
            guInfo.getCode()
        );

        RestaurantApiResponse response = restTemplate.getForObject(url, RestaurantApiResponse.class);

        int listTotalCount = response.getListTotalCount();
        int totalPage = (listTotalCount + 1000 - 1) / 1000;

        while (page <= totalPage) {
            int fromNum = (page - 1) * 1000 + 1;
            int toNum = (page) * 1000;

            String pageUrl = String.format(
                "%s/%s/xml/%s/%d/%d/",
                guInfo.getBaseUrl(),
                apiKey,
                guInfo.getCode(),
                fromNum,
                toNum
            );

            RestaurantApiResponse pageResponse = restTemplate.getForObject(pageUrl, RestaurantApiResponse.class);
            List<RestaurantRawDTO> rows = pageResponse.getRow();

            if ( rows == null || rows.isEmpty() ) {
                break;
            }

            rawList.addAll(rows);
            page++;
        }

        return rawList;
    }
    
}
