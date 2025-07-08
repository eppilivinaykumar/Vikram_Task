package in.ashokit.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import in.ashokit.dto.ViewEnqFilterRequest;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Enquiry;
import in.ashokit.repos.CounsellorRepo;
import in.ashokit.repos.EnquiryRepo;


@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	private EnquiryRepo enqRepo;
	
	private CounsellorRepo counsellorRepo;
	
	public EnquiryServiceImpl(EnquiryRepo enqRepo,CounsellorRepo counsellorRepo) {
		this.enqRepo = enqRepo;
		this.counsellorRepo = counsellorRepo;
	}
	

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor	counsellor 	= counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellorRepo == null) {
			
			throw new Exception("No Counsellor found");
		}
		
		enq.setCounsellor(counsellor);
		 Enquiry save = enqRepo.save(enq);
		 
		 if(save.getEnqId() != null) {
			 
			 return true;
		 }
		
	
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquires(Integer counsellorId) {
	
		
		
		return enqRepo.getEnquiresByCounsellorId(counsellorId);
	}

	
	@Override
	public Enquiry getEnquiryById(Integer id) {
	return enqRepo.findById(id).orElse(null);
	}
	
	@Override
	public List<Enquiry> getEnquiresWithFilter(ViewEnqFilterRequest filterReq, Integer counsellorId) {
		
		//QBE implementation (Dynamic Query preparation)
		
		Enquiry enq = new Enquiry();
		
	if(StringUtils.isNoneEmpty(filterReq.getClassMode())) {
		enq.setClassMode(filterReq.getClassMode());
		
	}
	
//	io.micrometer.common.util.StringUtils.isNotEmpty(null)
	
	if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
		enq.setCourseName(filterReq.getCourseName());
	}
	
	if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
		enq.setEnqStatus(filterReq.getEnqStatus());
	}
	
		
		
Counsellor c =	counsellorRepo.findById(counsellorId).orElse(null);
enq.setCounsellor(c);
		
		
	Example<Enquiry> of = Example.of(enq);
	
	List<Enquiry> enqList = enqRepo.findAll(of);

		return enqList;
	}

}
