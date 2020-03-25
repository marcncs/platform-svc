package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductIncome extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String handwordcode;
    
    private String billno;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Date incomedate;

    /** nullable persistent field */
    private Integer incomesort;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;
    
    private String makeorganid;
    
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private String keyscontent;
    
    private String nccode;
    
    private String confimUserId;
    private Date confimDate;
    private Integer confimState;
    
    //是否是自动生成的单据
    private Integer isAutoCreate;
    //批次(手动新增使用)
    private String batch;
    

    /** full constructor */
    public ProductIncome(String id, String handwordcode, String warehouseid, String producebatch, Integer incomedept, Date incomedate, Integer incomesort, String remark, Integer isrefer, Integer approvestatus, Date approvedate, Integer isaudit, Integer auditid, Date auditdate, Integer makeid, Date makedate,String nccode) {
        this.id = id;
        this.handwordcode = handwordcode;
        this.warehouseid = warehouseid;
        this.incomedate = incomedate;
        this.incomesort = incomesort;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeid = makeid;
        this.makedate = makedate;
        this.nccode=nccode;
    }

    /** default constructor */
    public ProductIncome() {
    }

    /** minimal constructor */
    public ProductIncome(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfimUserId() {
		return confimUserId;
	}

	public void setConfimUserId(String confimUserId) {
		this.confimUserId = confimUserId;
	}

	public Date getConfimDate() {
		return confimDate;
	}

	public void setConfimDate(Date confimDate) {
		this.confimDate = confimDate;
	}

	public Integer getConfimState() {
		return confimState;
	}

	public void setConfimState(Integer confimState) {
		this.confimState = confimState;
	}

	public String getHandwordcode() {
        return this.handwordcode;
    }

    public void setHandwordcode(String handwordcode) {
        this.handwordcode = handwordcode;
    }

    public Date getIncomedate() {
        return this.incomedate;
    }

    public void setIncomedate(Date incomedate) {
        this.incomedate = incomedate;
    }

    public Integer getIncomesort() {
        return this.incomesort;
    }

    public void setIncomesort(Integer incomesort) {
        this.incomesort = incomesort;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductIncome) ) return false;
        ProductIncome castOther = (ProductIncome) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}


	public Integer getIsAutoCreate() {
		return isAutoCreate;
	}

	public void setIsAutoCreate(Integer isAutoCreate) {
		this.isAutoCreate = isAutoCreate;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	

}
