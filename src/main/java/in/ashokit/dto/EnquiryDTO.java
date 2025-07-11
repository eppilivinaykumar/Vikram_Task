package in.ashokit.dto;

public class EnquiryDTO {
	
	private String sNo;
	private Integer enqId;
    private String courseName;
    private String stuName;
    private String classMode;
    private Long studentPhno;
    private String enqStatus;
    private boolean selected;  
    
    
    
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "EnquiryDTO [sNo=" + sNo + ", enqId=" + enqId + ", courseName=" + courseName + ", stuName=" + stuName
				+ ", classMode=" + classMode + ", studentPhno=" + studentPhno + ", enqStatus=" + enqStatus + "]";
	}

	public String getsNo() {
		return sNo;
	}

	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	public Integer getEnqId() {
		return enqId;
	}

	public void setEnqId(Integer enqId) {
		this.enqId = enqId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getClassMode() {
		return classMode;
	}

	public void setClassMode(String classMode) {
		this.classMode = classMode;
	}

	public Long getStudentPhno() {
		return studentPhno;
	}

	public void setStudentPhno(Long studentPhno) {
		this.studentPhno = studentPhno;
	}

	public String getEnqStatus() {
		return enqStatus;
	}

	public void setEnqStatus(String enqStatus) {
		this.enqStatus = enqStatus;
	}
    
	
	    
	
}
