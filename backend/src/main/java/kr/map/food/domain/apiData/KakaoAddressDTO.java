package kr.map.food.domain.apiData;

import lombok.Data;

@Data
public class KakaoAddressDTO {

    private String roadAddress;
    private String jibunAddress;
    private String postCode;
    private Double latitude;
    private Double longitude;
    private String gu;
    private String dong;
    
}
