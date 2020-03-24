package com.winsafe.sap.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.sap.metadata.SeedType;
import com.winsafe.sap.pojo.Seed;

public class AppSeed { 
	
	public Seed getNotUsedSeed(SeedType seedType, String year) {
		String hql = "from Seed where isUsed=0 and year="+year+" and type="+seedType.getValue();
		return (Seed)EntityManager.find(hql);
	}
	
	public Set<String> getAllUsedSeedSet(String year, SeedType seedType) {
		String hql = "select seed from Seed where isUsed=1 and year="+year+" and type="+seedType.getValue();
		List<Object> seedList = EntityManager.getAllByHql(hql);
		Set<String> seedSet = new HashSet<String>();
		for(Object obj : seedList) {
			seedSet.add(obj.toString());
		}
		return seedSet;
	}

	public void addSeed(Seed seed) {
		EntityManager.save(seed);
	}

	public void updSeed(Seed seed) throws Exception {
		EntityManager.update(seed);
		
	}

	public Seed getNotUsedSeed(SeedType seedType, String lCode, int seqNum) {
		String hql = "from Seed where isUsed=0 and lCode='"+lCode+"' and type="+seedType.getValue()+" and seqNum="+seqNum;
		return (Seed)EntityManager.find(hql);
	}
	
	public Set<String> getAllUsedSeedSet(SeedType seedType, String lCode, int seqNum) {
		String hql = "select seed from Seed where isUsed=1 and lCode='"+lCode+"' and type="+seedType.getValue()+" and seqNum="+seqNum;
		List<Object> seedList = EntityManager.getAllByHql(hql);
		Set<String> seedSet = new HashSet<String>();
		for(Object obj : seedList) {
			seedSet.add(obj.toString());
		}
		return seedSet;
	}
}
