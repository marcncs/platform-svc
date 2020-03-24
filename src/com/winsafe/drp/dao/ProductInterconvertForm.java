package com.winsafe.drp.dao;

import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ProductInterconvertForm   {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate; 
    
    private String movedatename;
    

    /** nullable persistent field */
    private String outwarehouseid;
    
    private String outwarehouseidname;

    /** nullable persistent field */
    private String inwarehouseid;
    
    private String inwarehouseidname;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String movecause;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;
    
    private String auditdatename;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;
    
    private String makedatename;

    /** nullable persistent field */
    private Integer isshipment;
    
    private String isshipmentname;

    /** nullable persistent field */
    private Integer shipmentid;
    
    private String shipmentidname;

    /** nullable persistent field */
    private Date shipmentdate;
    
    private String shipmentdatename;

    /** nullable persistent field */
    private Integer iscomplete;
    
    private String iscompletename;

    /** nullable persistent field */
    private Integer receiveid;
    
    private String receiveidname;
    
    /** nullable persistent field */
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Date receivedate;
    
    private String receivedatename;

    /** full constructor */
  
    /** default constructor */
    public ProductInterconvertForm() {
    }

    /** minimal constructor */
    public ProductInterconvertForm(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMovedate() {
        return this.movedate;
    }

    public void setMovedate(Date movedate) {
        this.movedate = movedate;
    }

    public String getOutwarehouseid() {
        return this.outwarehouseid;
    }

    public void setOutwarehouseid(String outwarehouseid) {
        this.outwarehouseid = outwarehouseid;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public String getMovecause() {
        return this.movecause;
    }

    public void setMovecause(String movecause) {
        this.movecause = movecause;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getIsshipment() {
        return this.isshipment;
    }

    public void setIsshipment(Integer isshipment) {
        this.isshipment = isshipment;
    }

    public Integer getShipmentid() {
        return this.shipmentid;
    }

    public void setShipmentid(Integer shipmentid) {
        this.shipmentid = shipmentid;
    }

    public Date getShipmentdate() {
        return this.shipmentdate;
    }

    public void setShipmentdate(Date shipmentdate) {
        this.shipmentdate = shipmentdate;
    }

    public Integer getIscomplete() {
        return this.iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    public Integer getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(Integer receiveid) {
        this.receiveid = receiveid;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

	public String getAuditdatename() {
		return auditdatename;
	}

	public void setAuditdatename(String auditdatename) {
		this.auditdatename = auditdatename;
	}

	public String getInwarehouseidname() {
		return inwarehouseidname;
	}

	public void setInwarehouseidname(String inwarehouseidname) {
		this.inwarehouseidname = inwarehouseidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getIscompletename() {
		return iscompletename;
	}

	public void setIscompletename(String iscompletename) {
		this.iscompletename = iscompletename;
	}

	public String getMakedatename() {
		return makedatename;
	}

	public void setMakedatename(String makedatename) {
		this.makedatename = makedatename;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMovedatename() {
		return movedatename;
	}

	public void setMovedatename(String movedatename) {
		this.movedatename = movedatename;
	}

	public String getOutwarehouseidname() {
		return outwarehouseidname;
	}

	public void setOutwarehouseidname(String outwarehouseidname) {
		this.outwarehouseidname = outwarehouseidname;
	}

	public String getReceivedatename() {
		return receivedatename;
	}

	public void setReceivedatename(String receivedatename) {
		this.receivedatename = receivedatename;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public String getShipmentdatename() {
		return shipmentdatename;
	}

	public void setShipmentdatename(String shipmentdatename) {
		this.shipmentdatename = shipmentdatename;
	}

	public String getShipmentidname() {
		return shipmentidname;
	}

	public void setShipmentidname(String shipmentidname) {
		this.shipmentidname = shipmentidname;
	}

	public String getIsshipmentname() {
		return isshipmentname;
	}

	public void setIsshipmentname(String isshipmentname) {
		this.isshipmentname = isshipmentname;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	public Date getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Integer getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
	}

	public String getBlankoutreason() {
		return blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
	}

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public String getIsblankoutname() {
		return isblankoutname;
	}

	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
	}

   

}
