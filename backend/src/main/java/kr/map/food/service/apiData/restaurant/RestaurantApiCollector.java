package kr.map.food.service.apiData.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.map.food.domain.apiData.restaurant.GuApiInfoENUM;
import kr.map.food.domain.apiData.restaurant.RestaurantApiResponse;
import kr.map.food.domain.apiData.restaurant.RestaurantRawDTO;

public class RestaurantApiCollector {

    private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();

    public List<RestaurantRawDTO> collect(GuApiInfoENUM guInfo, String apiKey) {
        List<RestaurantRawDTO> rawList = new ArrayList<>();
        int page = 1;

        while (true) {
            int fromNum = (page - 1) * 1000 + 1;
            int toNum = page * 1000;

            String pageUrl = String.format(
                "%s/%s/xml/%s/%d/%d/",
                guInfo.getBaseUrl(),
                apiKey,
                guInfo.getCode(),
                fromNum,
                toNum
            );

            String xmlBody = restTemplate.getForObject(pageUrl, String.class);

            RestaurantApiResponse response = xmlMapper.readValue(xmlBody, RestaurantApiResponse.class);

            if (response.getRow() == null || response.getRow().isEmpty()) {
                break;
            }

            rawList.addAll(response.getRow());

            if (rawList.size() >= response.getListTotalCount()) {
                break;
            }

            page++;
        }

        return rawList;
    }
}
