package com.app.dao;

import java.util.List;

import com.app.pojo.Laboratory;

public interface LaboratoryDAO 
{
	public void saveLaboratory(Laboratory lab);

	public List<Laboratory> listLaboratory();

	public List<Laboratory> getLaboratory(int labId);

	public void updateLaboratory(Laboratory lab);

	public Integer[] idsLaboratory();

	public Integer getIdLaboratory();

	public String[] getIdcLaboratory();

	public List<Laboratory> getWholeLaboratory();

	public List<Laboratory> getWholeLaboratoryNames(String labName);

	public List getParticularLaboratory(Laboratory lab);

	public Integer getMaxIdOfLaboratory();
}
