package kr.map.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.map.food.domain.TestDTO;
import kr.map.food.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping
    public List<TestDTO> list() {
        return testService.getAll();
    }

    @PostMapping
    public String save(@RequestBody TestDTO dto) {
        testService.save(dto);
        return "저장완료";
    }
    
    
}
