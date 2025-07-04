package kr.map.food.domain.apiData.BestRestaurant;

public enum GuApiInfoENUM {

    YANGCHEON(
        "http://openapi.yangcheon.go.kr:8088",
        "YcModelRestaurantDesignate"
    ),
    SONGPA(
        "http://openAPI.songpa.seoul.kr:8088",
        "SpModelRestaurantDesignate"
    );

    private final String baseUrl;
    private final String code;

    GuApiInfoENUM(String baseUrl, String code) {
        this.baseUrl = baseUrl;
        this.code = code;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCode() {
        return code;
    }
    
}
