package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockAlterMove implements Serializable ,java.lang.Cloneable{
	

	private String ttid;
	
    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate;

    /** nullable persistent field */
    private String outwarehouseid;

    /** nullable persistent field */
    private String inwarehouseid;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Integer invmsg;

    /** nullable persistent field */
    private Integer transportmode;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Integer ismaketicket;
    
    /** nullable persistent field */
    private Integer isreceiveticket;

    /** nullable persistent field */
    private String tickettitle;
    
    private String olinkman;
    private String otel;

    /** nullable persistent field */
    private String movecause;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isshipment;

    /** nullable persistent field */
    private Integer shipmentid;

    /** nullable persistent field */
    private Date shipmentdate;
    
    /** nullable persistent field */
    private Integer istally;

    /** nullable persistent field */
    private Integer tallyid;

    /** nullable persistent field */
    private Date tallydate;
    
    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private String receiveorganid;

    /** nullable persistent field */
    private String receiveorganidname;

    /** nullable persistent field */
    private Integer receivedeptid;

    /** nullable persistent field */
    private Integer iscomplete;

    /** nullable persistent field */
    private Integer receiveid;

    /** nullable persistent field */
    private Date receivedate;

    /** 是否移库 richie.yu 2011-12-19 */
    private Integer ismove;
    
	/** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer takestatus;
    
    private String keyscontent;
    
    /**
     * 外部单号
     */
    private String nccode;
    /**
     * 配送单号
     */
    private String nccode2;
    
    /** 运输车车牌号 */
    private String busNo;
    
    /** 运输路线 */
    private String busWay;
    //出库机构编号
    private String outOrganId;
    //出库机构名称
    private String outOrganName;
    //原入库仓库
    private String oldInwarehouseId;
    
    private  int Bsort;//单据类型
    //入库仓库改变原因
    private String changeReason;
    //是否是整单销售单
    private Integer isTransferred;
    
    private Integer version;
    
    private Integer bonusStatus;
    
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	

    public StockAlterMove(String id, Date movedate, String outwarehouseid,
			String inwarehouseid, Double totalsum, Integer paymentmode,
			Integer invmsg, Integer transportmode, String transportaddr,
			Integer ismaketicket, String tickettitle, String olinkman,
			String otel, String movecause, String remark, Integer isaudit,
			Integer auditid, Date auditdate, String makeorganid,
			String makeorganidname, Integer makedeptid, Integer makeid,
			Date makedate, Integer isshipment, Integer shipmentid,
			Date shipmentdate, Integer istally, Integer tallyid,
			Date tallydate, Integer isblankout, Integer blankoutid,
			Date blankoutdate, String blankoutreason, String receiveorganid,
			String receiveorganidname, Integer receivedeptid,
			Integer iscomplete, Integer receiveid, Date receivedate,
			Integer printtimes, Integer takestatus, String keyscontent,String nccode,
			String busNo,String busWay) {
		super();
		this.id = id;
		this.movedate = movedate;
		this.outwarehouseid = outwarehouseid;
		this.inwarehouseid = inwarehouseid;
		this.totalsum = totalsum;
		this.paymentmode = paymentmode;
		this.invmsg = invmsg;
		this.transportmode = transportmode;
		this.transportaddr = transportaddr;
		this.ismaketicket = ismaketicket;
		this.tickettitle = tickettitle;
		this.olinkman = olinkman;
		this.otel = otel;
		this.movecause = movecause;
		this.remark = remark;
		this.isaudit = isaudit;
		this.auditid = auditid;
		this.auditdate = auditdate;
		this.makeorganid = makeorganid;
		this.makeorganidname = makeorganidname;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
		this.isshipment = isshipment;
		this.shipmentid = shipmentid;
		this.shipmentdate = shipmentdate;
		this.istally = istally;
		this.tallyid = tallyid;
		this.tallydate = tallydate;
		this.isblankout = isblankout;
		this.blankoutid = blankoutid;
		this.blankoutdate = blankoutdate;
		this.blankoutreason = blankoutreason;
		this.receiveorganid = receiveorganid;
		this.receiveorganidname = receiveorganidname;
		this.receivedeptid = receivedeptid;
		this.iscomplete = iscomplete;
		this.receiveid = receiveid;
		this.receivedate = receivedate;
		this.printtimes = printtimes;
		this.takestatus = takestatus;
		this.keyscontent = keyscontent;
		this.nccode=nccode;
		this.busNo = busNo;
		this.busWay = busWay;
	}

	/** default constructor */
    public StockAlterMove() {
    }

    /** minimal constructor */
    public StockAlterMove(String id) {
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

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public Integer getInvmsg() {
        return this.invmsg;
    }

    public void setInvmsg(Integer invmsg) {
        this.invmsg = invmsg;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
    }

    public Integer getIsmaketicket() {
        return this.ismaketicket;
    }

    public void setIsmaketicket(Integer ismaketicket) {
        this.ismaketicket = ismaketicket;
    }

    public String getTickettitle() {
        return this.tickettitle;
    }

    public void setTickettitle(String tickettitle) {
        this.tickettitle = tickettitle;
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

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String getMakeorganidname() {
        return this.makeorganidname;
    }

    public void setMakeorganidname(String makeorganidname) {
        this.makeorganidname = makeorganidname;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Integer getIsreceiveticket() {
		return isreceiveticket;
	}

	public void setIsreceiveticket(Integer isreceiveticket) {
		this.isreceiveticket = isreceiveticket;
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

    public String getReceiveorganid() {
        return this.receiveorganid;
    }

    public void setReceiveorganid(String receiveorganid) {
        this.receiveorganid = receiveorganid;
    }

    public String getReceiveorganidname() {
        return this.receiveorganidname;
    }

    public void setReceiveorganidname(String receiveorganidname) {
        this.receiveorganidname = receiveorganidname;
    }

    public Integer getReceivedeptid() {
        return this.receivedeptid;
    }

    public void setReceivedeptid(Integer receivedeptid) {
        this.receivedeptid = receivedeptid;
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

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public Integer getTakestatus() {
        return this.takestatus;
    }

    public void setTakestatus(Integer takestatus) {
        this.takestatus = takestatus;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StockAlterMove) ) return false;
        StockAlterMove castOther = (StockAlterMove) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getIstally() {
		return istally;
	}

	public void setIstally(Integer istally) {
		this.istally = istally;
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


	public Integer getIsmove() {
		return ismove;
	}

	public void setIsmove(Integer ismove) {
		this.ismove = ismove;
	}

	public String getOlinkman() {
		return olinkman;
	}

	public void setOlinkman(String olinkman) {
		this.olinkman = olinkman;
	}

	public String getOtel() {
		return otel;
	}

	public void setOtel(String otel) {
		this.otel = otel;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String getBusNo()
	{
		return busNo;
	}

	public void setBusNo(String busNo)
	{
		this.busNo = busNo;
	}

	public String getBusWay()
	{
		return busWay;
	}

	public void setBusWay(String busWay)
	{
		this.busWay = busWay;
	}

	public String getNccode2() {
		return nccode2;
	}

	public void setNccode2(String nccode2) {
		this.nccode2 = nccode2;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}

	public int getBsort() {
		return Bsort;
	}

	public void setBsort(int bsort) {
		Bsort = bsort;
	}
	
	@Override
	public StockAlterMove clone()  {
		StockAlterMove sam = null;
		try {
			sam = (StockAlterMove)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return sam;
	}

	public String getOutOrganId() {
		return outOrganId;
	}

	public void setOutOrganId(String outOrganId) {
		this.outOrganId = outOrganId;
	}

	public String getOutOrganName() {
		return outOrganName;
	}

	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}

	public String getOldInwarehouseId() {
		return oldInwarehouseId;
	}

	public void setOldInwarehouseId(String oldInwarehouseId) {
		this.oldInwarehouseId = oldInwarehouseId;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Integer getIsTransferred() {
		return isTransferred;
	}

	public void setIsTransferred(Integer isTransferred) {
		this.isTransferred = isTransferred;
	}

	public Integer getBonusStatus() {
		return bonusStatus;
	}

	public void setBonusStatus(Integer bonusStatus) {
		this.bonusStatus = bonusStatus;
	}
	
}
