package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns; 
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_group1")
public class ProductGroup1 extends ProductGruopBase {

//	private static final long serialVersionUID = -5371719166751687560L;
	
//	@OneToMany(fetch=FetchType.EAGER, targetEntity=ProductGroup2.class, cascade =      //单项一对多配置
//		{CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE,})
//	@JoinColumns(value={@JoinColumn(name="group1_id",referencedColumnName="id")})   //对应关系 group1_id = id
	private List<ProductGroup2> productGroup2s;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(1);
		this.setDeleteTime(new Date());
		this.setLastModifyTime(new Date());
	}

	public List<ProductGroup2> getProductGroup2s() {
		return productGroup2s;
	}

	public void setProductGroup2s(List<ProductGroup2> productGroup2s) {
		this.productGroup2s = productGroup2s;
	}
}
