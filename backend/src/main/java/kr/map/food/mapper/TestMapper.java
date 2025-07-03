package kr.map.food.mapper;


import kr.map.food.domain.TestDTO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TestMapper {
    List<TestDTO> findAll();
    void insertTest(TestDTO dto);
}
