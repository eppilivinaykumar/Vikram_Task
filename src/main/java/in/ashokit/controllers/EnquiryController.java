package in.ashokit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.dto.EnquiryDTO;
import in.ashokit.dto.EnquiryWrapperDTO;
import in.ashokit.dto.ViewEnqFilterRequest;
import in.ashokit.entities.Enquiry;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Controller
public class EnquiryController {
	
	private EnquiryService enquiryService;

	public EnquiryController(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}
	
	@PostMapping("/filter-enqs")
	public String filterEnquires(ViewEnqFilterRequest viewEnqsFilterRequest,HttpServletRequest req,Model model) {
		
		//get existing session obj
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enquiryService.getEnquiresWithFilter(viewEnqsFilterRequest, counsellorId);
		
		
		model.addAttribute("enquiries",enqList);
		model.addAttribute("viewEnqsFilterRequest", viewEnqsFilterRequest);
		
		
		return "viewEnqsPage";
		
	}
	
	@GetMapping("/view-enquiries")
	public String getEnquires(HttpServletRequest request, Model model) {

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
	        dto.setEnqId(e.getEnqId());   // change is done for new requirment 10/07/2025
	        dtoList.add(dto);
	    }

	    EnquiryWrapperDTO wrapper = new EnquiryWrapperDTO();
	    wrapper.setEnquiryList(dtoList);

	    model.addAttribute("enquiryWrapper", wrapper);

	    // Optional: filters and other UI objects
	    model.addAttribute("viewEnqsFilterRequest", new ViewEnqFilterRequest());

	    return "viewEnqsPage";
	}

	
	

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		Enquiry enqObj = new Enquiry();
		
		model.addAttribute("enq",enqObj);
		
		return "enquiryForm";
		
		
	}
	
	
	@PostMapping("/addEnq")
	public String handleAddEnquiry(@ModelAttribute("enq") Enquiry enq,HttpServletRequest req, Model model) throws Exception {
		
		// get existing session obj for counsellor id
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
	boolean isSaved = enquiryService.addEnquiry(enq, counsellorId);
	
	if(isSaved) {
		model.addAttribute("smsg","Enquiry Added");
		
	}else {
		
		model.addAttribute("emsg","Failed To Add Enquiry");
	}
		
		
		return "enquiryForm";
		
	}
	
//	@PostMapping("/api/save-selected-enquiries")
//	public ResponseEntity<?> saveSelected(@RequestBody List<EnquiryDTO> enquiries) {
//	    enquiries.forEach(System.out::println); // Or save to DB
//	    return ResponseEntity.ok("Saved");
//	}
	
	@PostMapping("/submit-selected-enquiries")
	public String handleSelectedEnquiries(
	        @ModelAttribute("enquiryWrapper") EnquiryWrapperDTO wrapperDTO,
	        Model model
	) {
	    List<EnquiryDTO> selectedEnquiries = wrapperDTO.getEnquiryList()
	            .stream()
	            .filter(EnquiryDTO::isSelected)
	            .toList();

	    // Example: Just printing for now
	    for (EnquiryDTO dto : selectedEnquiries) {
	    	System.out.println("Selected SNo: " + dto.getsNo());
	        System.out.println("Selected Enquiry ID: " + dto.getEnqId());
	        System.out.println("Selected CourseName ID: " + dto.getCourseName());
	        System.out.println("Selected classMode: " + dto.getClassMode());
	        System.out.println("Selected Student Name: " + dto.getStuName());
	        
	        
	        
	        
	    }

	    // You can pass data to UI if needed
	    model.addAttribute("message", selectedEnquiries.size() + " enquiries selected.");

	    // Redirect or return view name as needed
	    
//	    
//	    try {
//	        // 2. Convert list to JSON string (optional: to see JSON as string)
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        String jsonPayload = objectMapper.writeValueAsString(selectedEnquiries);
//
//	        // 3. Set headers
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        // 4. Wrap JSON into HttpEntity
//	        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
//
//	        // 5. Define your 3rd-party URL
//	        String url = "https://thirdparty.com/api/endpoint";
//
//	        // 6. Send POST request using RestTemplate
//	        RestTemplate restTemplate = new RestTemplate();
//	        String response = restTemplate.postForObject(url, request, String.class);
//
//	        // 7. Print or log response
//	        System.out.println("Response from third-party: " + response);
//
//	    } catch (Exception e) {
//	    	 System.out.println("Error occurred: " + e.getMessage());
////	        e.printStackTrace(); // Handle exceptions properly in real applications
//	        
//	    }
	    
	    
	    return "redirect:/view-enquiries";
	}

	// Update existing Fields
	
	@GetMapping("/update-enquiries")
	public String updateEnquiryPage(Model model) {
		
		Enquiry enqObj = new Enquiry();
		
		model.addAttribute("enq",enqObj);
		
		return "enquiryForm";
		
		
	}
	
	
	
	

}
