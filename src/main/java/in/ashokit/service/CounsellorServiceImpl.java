package in.ashokit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Enquiry;
import in.ashokit.repos.CounsellorRepo;
import in.ashokit.repos.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {
	
	
	private CounsellorRepo counsellorRepo;
	
	
	private EnquiryRepo enquiryRepo;
	
	

	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enquiryRepo) {
		this.counsellorRepo = counsellorRepo;
		this.enquiryRepo = enquiryRepo;
	}
	@Override
	public Counsellor findByEmail(String email) {

		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {
		
//		 Optional<Counsellor> existing = counsellorRepo.findByEmail(counsellor.getEmail());
//		 
//		 if(existing.isPresent()) {
//			 
//			 return false;
//		 }
	
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		
		if(null != savedCounsellor.getCounsellorId()) {
			
			return true;
		}
		
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
			
		return counsellorRepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public DashboardResponse getDashBoardInfo(Integer counsellorId) {
		
		DashboardResponse response = new DashboardResponse();
		
		List<Enquiry> enqList = enquiryRepo.getEnquiresByCounsellorId(counsellorId);
		
	int totalEnq = enqList.size();
	
	int enrolledEnqs = enqList.stream()
			.filter(e -> e.getEnqStatus().equalsIgnoreCase("Enrolled")).toList().size();
	
int lostEnqs =	enqList.stream()
	.filter(e -> e.getEnqStatus().equalsIgnoreCase("Lost")).toList().size();
	
	int openEnqs =enqList.stream()
	.filter(e -> e.getEnqStatus().equalsIgnoreCase("Open")).toList().size();
	
	response.setEnrolledEnqs(enrolledEnqs);
	
	response.setLostEnqs(lostEnqs);
	
	response.setOpenEnqs(openEnqs);
	
	response.setTotalEnqs(totalEnq);
	

		return response;
	}

	

}
