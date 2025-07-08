package in.ashokit.service;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.entities.Counsellor;

public interface CounsellorService {
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email,String pwd);
	
	public DashboardResponse getDashBoardInfo(Integer counsellorId);
	
	public Counsellor findByEmail(String email);

}
