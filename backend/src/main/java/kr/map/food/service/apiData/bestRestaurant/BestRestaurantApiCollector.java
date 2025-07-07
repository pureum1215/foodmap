package kr.map.food.service.apiData.BestRestaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import kr.map.food.domain.apiData.BestRestaurant.BestRestaurantApiResponse;
import kr.map.food.domain.apiData.BestRestaurant.BestRestaurantRawDTO;
import kr.map.food.domain.apiData.BestRestaurant.GuApiInfoENUM;

public class BestRestaurantApiCollector {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<BestRestaurantRawDTO> collect(GuApiInfoENUM guInfo, String apiKey) {
        List<BestRestaurantRawDTO> rawList = new ArrayList<>();

        int page = 1;

        String url = String.format(
            "%s/%s/xml/%s/1/1/",
            guInfo.getBaseUrl(),
            apiKey,
            guInfo.getCode()
        );

        BestRestaurantApiResponse response = restTemplate.getForObject(url, BestRestaurantApiResponse.class);

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

            BestRestaurantApiResponse pageResponse = restTemplate.getForObject(pageUrl, BestRestaurantApiResponse.class);
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
