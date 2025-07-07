package kr.map.food.domain.apiData.Restaurant;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.map.food.domain.util.ResultDTO;
import lombok.Data;

@XmlRootElement(name = "LOCALDATA_072404_YC")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RestaurantApiResponse {
    
    @XmlElement(name = "list_total_count")
    private int listTotalCount;

    @XmlElement(name = "RESULT")
    private ResultDTO result;

    @XmlElement(name = "row")
    private List<RestaurantRawDTO> row;

}
