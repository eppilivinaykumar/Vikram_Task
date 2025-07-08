package in.ashokit.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{
	
	

	public Counsellor findByEmailAndPwd(String email,String pwd);
	
	 public Counsellor findByEmail(String email);
}
