package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-10-14 下午03:40:48
 * www.winsafe.cn
 */
public class BillNoFrom implements Serializable {
	
	private String id;
	
	private Date makedate;
	
	private Double totalsum;
	
	private Integer type;

	public BillNoFrom(String id, Date makedate, Double totalsum,
			Integer billnotype) {
		super();
		this.id = id;
		this.makedate = makedate;
		this.totalsum = totalsum;
		this.type = billnotype;
	}

	public BillNoFrom() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer billnotype) {
		this.type = billnotype;
	}
	
	

}
