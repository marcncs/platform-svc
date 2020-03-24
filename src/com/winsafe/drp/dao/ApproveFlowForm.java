package com.winsafe.drp.dao;

public class ApproveFlowForm {
	/** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String flowname;

    /** nullable persistent field */
    private String memo;

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
