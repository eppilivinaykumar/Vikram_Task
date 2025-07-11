package in.ashokit.dto;

import java.util.List;

public class EnquiryWrapperDTO {
	
	 private List<EnquiryDTO> enquiryList;

	 
	    @Override
	public String toString() {
		return "EnquiryWrapperDTO [enquiryList=" + enquiryList + "]";
	}

		// Getters and Setters
	    public List<EnquiryDTO> getEnquiryList() {
	        return enquiryList;
	    }

	    public void setEnquiryList(List<EnquiryDTO> enquiryList) {
	        this.enquiryList = enquiryList;
	    }

}
