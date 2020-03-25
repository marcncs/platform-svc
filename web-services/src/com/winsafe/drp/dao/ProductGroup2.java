package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class ProductGroup2 extends ProductGruopBase { 

	private Integer group1Id;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(1);
		this.setDeleteTime(new Date());
		this.setLastModifyTime(new Date());
	}

	public Integer getGroup1Id() {
		return group1Id;
	}

	public void setGroup1Id(Integer group1Id) {
		this.group1Id = group1Id;
	}
}
