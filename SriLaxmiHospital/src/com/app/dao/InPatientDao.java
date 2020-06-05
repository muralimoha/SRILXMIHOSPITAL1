package com.app.dao;

import java.util.List;


import com.app.pojo.InpatientPojo;


public interface InPatientDao {
	
	


	public List<InpatientPojo> getpatientbyreno(String regno);

	public List<InpatientPojo> getbynamebyname(String username);

	public List<InpatientPojo> getinpatients();
	
	

}
