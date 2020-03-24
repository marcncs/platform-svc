package com.winsafe.drp.dao;

import java.util.Date;

/** @author Hibernate CodeGenerator */
public class StockAlterMoveForm  {

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String movedate;
    
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
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Integer ismaketicket;
    
    private String ismaketicketname;

    /** nullable persistent field */
    private String tickettitle;

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
    
    /** nullable persistent field */
    private Integer istally;
    
    private String istallyname;

    /** nullable persistent field */
    private Integer tallyid;
    
    private String tallyidname;

    /** nullable persistent field */
    private Date tallydate;
    
    /** nullable persistent field */
    private Integer isblankout;

    private String isblankoutname;
    
    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isshipment;
    
    private String isshipmentname;

    /** nullable persistent field */
    private Integer shipmentid;
    
    private String shipmentidname;

    /** nullable persistent field */
    private Date shipmentdate;

    /** nullable persistent field */
    private String receiveorganid;

    /** nullable persistent field */
    private String receiveorganidname;

    /** nullable persistent field */
    private Integer receivedeptid;
    
    private String receivedeptidname;

    /** nullable persistent field */
    private Integer iscomplete;
    
    private String iscompletename;

    /** nullable persistent field */
    private Integer receiveid;
    
    private String receiveidname;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private Integer printtimes;
    
    private Integer takestatus;
    
    private String takestatusname;
    
    private String nccode;
    private String nccode2;
    private Integer bsort; 
    
    //出库机构名称
    private String outOrganName;

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInwarehouseid() {
		return inwarehouseid;
	}

	public void setInwarehouseid(String inwarehouseid) {
		this.inwarehouseid = inwarehouseid;
	}

	public String getInwarehouseidname() {
		return inwarehouseidname;
	}

	public void setInwarehouseidname(String inwarehouseidname) {
		this.inwarehouseidname = inwarehouseidname;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public Integer getIscomplete() {
		return iscomplete;
	}

	public void setIscomplete(Integer iscomplete) {
		this.iscomplete = iscomplete;
	}

	public String getIscompletename() {
		return iscompletename;
	}

	public void setIscompletename(String iscompletename) {
		this.iscompletename = iscompletename;
	}

	public Integer getIsshipment() {
		return isshipment;
	}

	public void setIsshipment(Integer isshipment) {
		this.isshipment = isshipment;
	}

	public String getIsshipmentname() {
		return isshipmentname;
	}

	public void setIsshipmentname(String isshipmentname) {
		this.isshipmentname = isshipmentname;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
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

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
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

	public String getMovecause() {
		return movecause;
	}

	public void setMovecause(String movecause) {
		this.movecause = movecause;
	}

	public String getMovedate() {
		return movedate;
	}

	public void setMovedate(String movedate) {
		this.movedate = movedate;
	}

	public String getOutwarehouseid() {
		return outwarehouseid;
	}

	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}

	public String getOutwarehouseidname() {
		return outwarehouseidname;
	}

	public void setOutwarehouseidname(String outwarehouseidname) {
		this.outwarehouseidname = outwarehouseidname;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Date getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public Integer getReceivedeptid() {
		return receivedeptid;
	}

	public void setReceivedeptid(Integer receivedeptid) {
		this.receivedeptid = receivedeptid;
	}

	public String getReceivedeptidname() {
		return receivedeptidname;
	}

	public void setReceivedeptidname(String receivedeptidname) {
		this.receivedeptidname = receivedeptidname;
	}

	public Integer getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(Integer receiveid) {
		this.receiveid = receiveid;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public String getReceiveorganid() {
		return receiveorganid;
	}

	public void setReceiveorganid(String receiveorganid) {
		this.receiveorganid = receiveorganid;
	}

	public String getReceiveorganidname() {
		return receiveorganidname;
	}

	public void setReceiveorganidname(String receiveorganidname) {
		this.receiveorganidname = receiveorganidname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getShipmentdate() {
		return shipmentdate;
	}

	public void setShipmentdate(Date shipmentdate) {
		this.shipmentdate = shipmentdate;
	}

	public Integer getShipmentid() {
		return shipmentid;
	}

	public void setShipmentid(Integer shipmentid) {
		this.shipmentid = shipmentid;
	}

	public String getShipmentidname() {
		return shipmentidname;
	}

	public void setShipmentidname(String shipmentidname) {
		this.shipmentidname = shipmentidname;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getMovedatename() {
		return movedatename;
	}

	public void setMovedatename(String movedatename) {
		this.movedatename = movedatename;
	}

	public Integer getTakestatus() {
		return takestatus;
	}

	public void setTakestatus(Integer takestatus) {
		this.takestatus = takestatus;
	}

	public String getTakestatusname() {
		return takestatusname;
	}

	public void setTakestatusname(String takestatusname) {
		this.takestatusname = takestatusname;
	}

	public Integer getInvmsg() {
		return invmsg;
	}

	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	public String getInvmsgname() {
		return invmsgname;
	}

	public void setInvmsgname(String invmsgname) {
		this.invmsgname = invmsgname;
	}

	public Integer getIsmaketicket() {
		return ismaketicket;
	}

	public void setIsmaketicket(Integer ismaketicket) {
		this.ismaketicket = ismaketicket;
	}

	public String getIsmaketicketname() {
		return ismaketicketname;
	}

	public void setIsmaketicketname(String ismaketicketname) {
		this.ismaketicketname = ismaketicketname;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	public String getTransportaddr() {
		return transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	public Integer getIstally() {
		return istally;
	}

	public void setIstally(Integer istally) {
		this.istally = istally;
	}

	public String getIstallyname() {
		return istallyname;
	}

	public void setIstallyname(String istallyname) {
		this.istallyname = istallyname;
	}

	public Date getTallydate() {
		return tallydate;
	}

	public void setTallydate(Date tallydate) {
		this.tallydate = tallydate;
	}

	public Integer getTallyid() {
		return tallyid;
	}

	public void setTallyid(Integer tallyid) {
		this.tallyid = tallyid;
	}

	public String getTallyidname() {
		return tallyidname;
	}

	public void setTallyidname(String tallyidname) {
		this.tallyidname = tallyidname;
	}

	public String getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(String blankoutdate) {
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

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String getNccode2() {
		return nccode2;
	}

	public void setNccode2(String nccode2) {
		this.nccode2 = nccode2;
	}

	public Integer getBsort() {
		return bsort;
	}

	public void setBsort(Integer bsort) {
		this.bsort = bsort;
	}

	public String getOutOrganName() {
		return outOrganName;
	}

	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}

}
