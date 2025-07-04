package kr.map.food.domain.util;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.map.food.domain.apiData.RestaurantRawDTO;

@XmlRootElement(name = "LOCALDATA_072404_YC")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiResponse {
    
    @XmlElement(name = "list_total_count")
    private int listTotalCount;

    @XmlElement(name = "RESULT")
    private ResultDTO result;

    @XmlElement(name = "row")
    private List<RestaurantRawDTO> row;

    public int getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(int listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public List<RestaurantRawDTO> getRow() {
        return row;
    }

    public void setRow(List<RestaurantRawDTO> row) {
        this.row = row;
    }

}
