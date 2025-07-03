package kr.map.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.map.food.domain.TestDTO;
import kr.map.food.mapper.TestMapper;


@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    public List<TestDTO> getAll() {
        return testMapper.findAll();
    }

    public void save(TestDTO dto) {
        testMapper.insertTest(dto);
    }
}
