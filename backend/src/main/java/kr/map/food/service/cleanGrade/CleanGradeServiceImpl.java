package kr.map.food.service.cleanGrade;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.map.food.domain.cleanGrade.CleanGradeDTO;
import kr.map.food.mapper.cleanGrade.CleanGradeMapper;
import kr.map.food.service.cleanGrade.excel.CleanGradeExcel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CleanGradeServiceImpl implements CleanGradeService {

    // 생성자 주입 (@RequiredArgsConstructor + final)
    private final CleanGradeMapper mapper;
    private final CleanGradeExcel excel;


    public void uploadExcel(MultipartFile file) throws Exception {
        
        // 논리 삭제 CLDELYN = "Y"
        mapper.markAllDeleted();

        // 엑셀 파싱
        List<CleanGradeDTO> list = excel.parse(file.getInputStream());
    
        for(CleanGradeDTO dto : list) {
            if(mapper.existsClean(dto) > 0) {
                mapper.updateClean(dto);
            } else {
                dto.setCleanIdx(generateNextCleanIdx());
                mapper.insertClean(dto);
            }
        }
    }

    private String generateNextCleanIdx() {
        String max = mapper.getMaxCleanIdx();
        int next = (max != null) ? Integer.parseInt(max) + 1 : 1;

        return String.format("%05d", next);
    }
}
