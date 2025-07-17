package in.ashokit.RestController;

import in.ashokit.dto.EnquiryDTO;
import in.ashokit.dto.EnquiryWrapperDTO;
import in.ashokit.entities.Enquiry;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ViewEnqFilterRequestRestController {

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/enquiriesJSON")
    public ResponseEntity<EnquiryWrapperDTO> getEnquiriesJson(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        
        List<Enquiry> enqList = enquiryService.getAllEnquires(counsellorId);
        
        // Convert to DTOs
        List<EnquiryDTO> dtoList = new ArrayList<>();
        int index = 1;
        for (Enquiry e : enqList) {
            EnquiryDTO dto = new EnquiryDTO();
            dto.setsNo(String.valueOf(index++));
            dto.setStuName(e.getStuName());
            dto.setCourseName(e.getCourseName());
            dto.setClassMode(e.getClassMode());
            dto.setStudentPhno(e.getStudentPhno());
            dto.setEnqStatus(e.getEnqStatus());
            dto.setEnqId(e.getEnqId());
            dtoList.add(dto);
        }
        
        EnquiryWrapperDTO wrapper = new EnquiryWrapperDTO();
        wrapper.setEnquiryList(dtoList);
        
        return ResponseEntity.ok(wrapper);
    }
}