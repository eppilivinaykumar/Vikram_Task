package in.ashokit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.entities.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	

	private CounsellorService counsellorService;

	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}



	@GetMapping("/")
	public String index(Model model) {
		
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor",cobj);
		
		return "index";
		
	}
	
	@PostMapping("/login")
	public String handleLoginBtn(Counsellor counsellor, HttpServletRequest request, Model model) {
		
		Counsellor c =	counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		
		if(c == null) {
			model.addAttribute("emsg","Invaild Creditionals");
			return "index";
		}else {
			
			// valid login,Store counsellorId in session for future purpose 
			
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId",c.getCounsellorId());
			
//			DashboardResponse dbressObj = counsellorService.getDashBoardInfo(c.getCounsellorId());
//			model.addAttribute("dashboardInfo",dbressObj);
//			
//			
//			return "dashboard";
			
			return "redirect:/dashboard";
		}
		

		
	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {
		
		// get existing session Obj
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		DashboardResponse dbressObj = counsellorService.getDashBoardInfo(counsellorId);
		model.addAttribute("dashboardInfo",dbressObj);
		
		return "dashboard";
		
		
	}
	
	
	 @GetMapping("/register")
	  public String registerPage(Model model) {
		  
		  Counsellor cobj = new Counsellor();
		  
		  model.addAttribute("counsellor",cobj);
		  
		return "register";
		  
	  }
	 
	 
	 @PostMapping("/register")
	 public String handleRegistration(Counsellor counsellor, Model model) {
		 
		 Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		 
		 
		 if(byEmail!= null) {
			 // checking the email is present in application or not 
			 model.addAttribute("emsg","Duplicate Email");
			 
			return "register";
		 }
		 
		boolean isRegister =  counsellorService.register(counsellor);
		
		if(isRegister) {
			//success
			
			model.addAttribute("smsg","Registration Success....!!");
			
		}else {
			//Failed
			model.addAttribute("emsg","Registration Failed");
		}
		 
		 
		return "register";
		 
	 }
	 
	
	  @GetMapping("/logout")
	    public String logout(HttpServletRequest req) {
		  
		  // get existing session and invalidate it 
		HttpSession  session = req.getSession(false);
		
		session.invalidate();
		
		//redirect to login page 
		return "redirect:/";
	        
	    }
	  
//    // Show register page
//    @GetMapping("/register")
//    public String showRegisterPage(Model model) {
//        model.addAttribute("counsellor", new Counsellor());
//        return "register"; // loads register.html
//    }
//    
//    
//    @PostMapping("/register")
//    public String register(Counsellor counsellor, Model model) {
//        boolean c = counsellorService.register(counsellor);
//        
//        if (c) {
//            model.addAttribute("msg", "Registration successful!");
//            return "index"; // or return "login" if that's your login page
//        }
//        
//        model.addAttribute("emsg", "Registration failed. Try again.");
//        return "register"; // reload registration page if failed
//    }

	  
	 
	
	
	
}
