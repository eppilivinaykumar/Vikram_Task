package in.ashokit.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.dto.ViewEnqFilterRequest;
import in.ashokit.entities.Enquiry;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public String getEnquires(HttpServletRequest request,Model model) {
		
		//get existing session obj
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enquiryService.getAllEnquires(counsellorId);
		
		model.addAttribute("enquiries",enqList);
		
		// search form binding object 
		ViewEnqFilterRequest filterReq = new ViewEnqFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		
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
	
	
	

}
