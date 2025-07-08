package in.ashokit.service;



import java.util.List;

import in.ashokit.dto.ViewEnqFilterRequest;
import in.ashokit.entities.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquires(Integer counsellorId);
	
	public List<Enquiry> getEnquiresWithFilter(ViewEnqFilterRequest filterReq,Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer id);

}
