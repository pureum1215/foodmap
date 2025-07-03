package kr.map.food.domain.apiData;

import lombok.Data;

@Data
public class RestaurantDTO {

    private String resIdx;
    private String resName;
    private int resRun;
    private String resNum;
    private int typeIdx;
    private String resCleanScore;
    private String ADDRGU;
    private String ADDRDONG;
    private String OLDADDR;
    private String NEWADDR;
    private int NUMADDR;
    private Double XPOS;
    private Double YPOS;
    
}
