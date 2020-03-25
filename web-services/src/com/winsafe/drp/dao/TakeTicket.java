package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TakeTicket implements Serializable {

	private static final Long SerializableUID = 1L;
    /** identifier field */
    private String id;
    
    private Integer bsort;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String rlinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String inwarehouseid;

    /** nullable persistent field */
    private Integer takeid;

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
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer printtimes;
    
    private Integer isread;
    //产品名称+批号(用于页面显示)
    private String productNames;
    //产品的总数量(用于页面显示)
    private Double totalQuantity;
    
    private Integer ismove;// 2011-12-19 richie.yu 是否移库标示位
    
	private Integer isOverQuantity;
	
	private Integer isPicked;
	
	private Integer pickedId;
	
	private Date pickedDate;
	
	private Integer isChecked;
	
	private Integer checkedId;
	
	private Date checkedDate;
	
	//入库机构编号
	private String inOid;
	//入库机构
	private String inOname;
	//是否是通过无单生成的单据
	private Integer isNoBill;
	
	private Integer version;
	
	public Integer getIsPicked() {
		return isPicked;
	}

	public void setIsPicked(Integer isPicked) {
		this.isPicked = isPicked;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getPickedId() {
		return pickedId;
	}



	public void setPickedId(Integer pickedId) {
		this.pickedId = pickedId;
	}



	public Date getPickedDate() {
		return pickedDate;
	}



	public void setPickedDate(Date pickedDate) {
		this.pickedDate = pickedDate;
	}



	public Integer getCheckedId() {
		return checkedId;
	}



	public void setCheckedId(Integer checkedId) {
		this.checkedId = checkedId;
	}



	public Date getCheckedDate() {
		return checkedDate;
	}



	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}



	public Integer getIsChecked() {
		return isChecked;
	}



	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	//采集器编号
	private String scannerNo;
    
    public String getScannerNo() {
		return scannerNo;
	}



	public void setScannerNo(String scannerNo) {
		this.scannerNo = scannerNo;
	}

	/** 运输车牌号 */
    private String busNo;
    
    /** 运输路线 */
    private String busWay;

    /** 外部单号 */
    private String nccode;
    /** 配送单号 */
    private String nccode2;
    
    
    public Integer getIsOverQuantity() {
		return isOverQuantity;
	}



	public void setIsOverQuantity(Integer isOverQuantity) {
		this.isOverQuantity = isOverQuantity;
	}



	public String getProductNames() {
        return productNames;
    }
    
    

    /**
     * 
     * 主要功能：根据明细找到对应的产品明细
     */
    public void setProductNames(){
	String productNames = "";
	Double totalQuantity = 0D;
	AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
	AppProduct appProduct = new AppProduct();
	 
	try {
	    ttdetails = appTakeTicketDetail.getTakeTicketDetailByTtid(this.id);
	     
	    for (TakeTicketDetail takeTicketDetail : ttdetails) {
		Product product = appProduct.getProductByID(takeTicketDetail.getProductid());
		//设置显示的产品名字+规格
		productNames += product.getProductname() + product.getSpecmode() + ";";
		//设置显示的产品总数
		totalQuantity += takeTicketDetail.getQuantity();
	    }
	    
	} catch (Exception e) {
	    
	}
	
        this.productNames = productNames;
        this.totalQuantity = totalQuantity;
    }

    /**
     * 主要功能：根据条码初始化产品明细
     * @param ttidcodes 条码集合
     */
    public void setProductNames(List<TakeTicketIdcode> ttidcodes){
	AppProduct appProduct = new AppProduct();
	String productName = "";
	Double totalQuantity = 0D;
	for (TakeTicketIdcode takeTicketIdcode : ttidcodes) {
	    try {
		//初始化产品明细
		Product product = appProduct.getProductByID(takeTicketIdcode.getProductid());
		if(!productName.contains(product.getProductname()+product.getSpecmode()))
		productName += product.getProductname()+product.getSpecmode()+ ";";
		//累加数量
		totalQuantity += takeTicketIdcode.getPackquantity();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	this.productNames = productName;
	this.totalQuantity = totalQuantity;
    }
    
    public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	/** 一般类型的明细 */
    private List<TakeTicketDetail> ttdetails = new ArrayList<TakeTicketDetail>();

    /** full constructor */
    public TakeTicket(String id, String warehouseid, String billno, String oid, String oname, String rlinkman, String tel, String inwarehouseid, Integer takeid, String remark, Integer isaudit, Integer auditid, Date auditdate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String equiporganid, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, Integer printtimes,Integer isOverQuantity) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.billno = billno;
        this.oid = oid;
        this.oname = oname;
        this.rlinkman = rlinkman;
        this.tel = tel;
        this.inwarehouseid = inwarehouseid;
        this.takeid = takeid;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.equiporganid = equiporganid;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.printtimes = printtimes;
        this.isOverQuantity=isOverQuantity;
    }

    /** default constructor */
    public TakeTicket() {
    }

    /** minimal constructor */
    public TakeTicket(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getRlinkman() {
        return this.rlinkman;
    }

    public void setRlinkman(String rlinkman) {
        this.rlinkman = rlinkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public Integer getTakeid() {
        return this.takeid;
    }

    public void setTakeid(Integer takeid) {
        this.takeid = takeid;
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

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
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

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
    }

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Integer getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Integer blankoutid) {
        this.blankoutid = blankoutid;
    }

    public Date getBlankoutdate() {
        return this.blankoutdate;
    }

    public void setBlankoutdate(Date blankoutdate) {
        this.blankoutdate = blankoutdate;
    }

    public String getBlankoutreason() {
        return this.blankoutreason;
    }

    public void setBlankoutreason(String blankoutreason) {
        this.blankoutreason = blankoutreason;
    }

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }
    
    public Integer getIsmove() {
		return ismove;
	}



	public void setIsmove(Integer ismove) {
		this.ismove = ismove;
	}


    public Integer getBsort() {
		return bsort;
	}

	public void setBsort(Integer bsort) {
		this.bsort = bsort;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TakeTicket) ) return false;
        TakeTicket castOther = (TakeTicket) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    public List<TakeTicketDetail> getTtdetails() {
		return ttdetails;
	}

	public void setTtdetails(List<TakeTicketDetail> ttdetails) {
		this.ttdetails = ttdetails;
	}

	public Double getTotalQuantity() {
	    return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
	    this.totalQuantity = totalQuantity;
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

	public String getInOid() {
		return inOid;
	}

	public void setInOid(String inOid) {
		this.inOid = inOid;
	}

	public String getInOname() {
		return inOname;
	}

	public void setInOname(String inOname) {
		this.inOname = inOname;
	}

	public Integer getIsNoBill() {
		return isNoBill;
	}

	public void setIsNoBill(Integer isNoBill) {
		this.isNoBill = isNoBill;
	}

}
