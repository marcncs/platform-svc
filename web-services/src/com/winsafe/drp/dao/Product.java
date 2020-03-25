package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
/**
 * @author huangxy
 * @date Jul 16, 2012 12:09:21 PM
 * @version v1.0
 */
public class Product implements Serializable {

	/**
	 * 是否允许单据导入，0：不可；1可以
	 */
	private Integer canImport; 
    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String productnameen;

    /** nullable persistent field */
    private String pycode;

    /** nullable persistent field */
    private String psid;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer countunit;
    
    private Integer sunit;

    /** nullable persistent field */
    private Integer brand;

    /** nullable persistent field */
    private Integer wise;

    /** nullable persistent field */
    private String barcode;

    /** nullable persistent field */
    private Integer isidcode;
    
    private Integer isbatch;

    /** nullable persistent field */
    private String productcode;

    /** nullable persistent field */
    private String productcodedef;

    /** nullable persistent field */
    private Double leastsale;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Integer abcsort;

    /** nullable persistent field */
    private String explain;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer useflag;

    /** nullable persistent field */
    private Integer isunify;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private String nccode;
    
    /**
     * 箱中小包装数量
     */
    private Double boxquantity;

    private Integer scatterunitid;
    
    //保质期
    private String protectDate;
    
    //物料号
    private String mCode;
    //物料中文描述
    private String matericalChDes;
    //物料英文文描述
    private String matericalEnDes;
    
    //包装大小
    private String packSizeName;
    //包装大小英文
    private String packSizeNameEn;
    //保质期
    private Integer expiryDays;
    //箱码是否打印
    private Integer cartonPrintFlag;
    //小包装是否打印
    private Integer primaryPrintFlag;
    //箱码是否扫描
    private Integer cartonScanning;
    
    private String countUnitName;
    //条码类型1.DM 2.QR
    private Integer codeType;
    //关联模式1.前关联2.后关联
    private Integer linkMode;
    //登记证类型
    private Integer regCertType;
    //登记证号
    private String regCertCode;
    //登记证持有人
    private String regCertUser;
    //农药标准名称
    private String standardName;
    //产品规格代码
    private String specCode;
    //生产类型
    private Integer produceType; 
    //小包装码类型1.旧规则2.新规则
    private Integer primaryCodeType;
    //内部生产类型
    private Integer innerProduceType;
    //六位登记证
    private String regCertCodeFixed;
    //更新日期
    private Date modificationTime;
    //箱到小包装比例关系 
    private Integer packingRatio; 
    //产品类型 
    private Integer productType;

    //打印份数
    private Integer copys;
    
//    //验证结果
//    private String validResult;
    //生产企业
    private String inspectionInstitution;
    
    /** full constructor */
    public Product(String id, String productname, String productnameen, String pycode, String psid, String specmode, Integer countunit, Integer brand, Integer wise, String barcode, Integer isidcode, String productcode, String productcodedef, Double leastsale, Double cost, Integer abcsort, String explain, String memo, Integer useflag, Integer isunify, String makeorganid, Integer makedeptid, Integer makeid, Date makedate,String nccode) {
        this.id = id;
        this.productname = productname;
        this.productnameen = productnameen;
        this.pycode = pycode;
        this.psid = psid;
        this.specmode = specmode;
        this.countunit = countunit;
        this.brand = brand;
        this.wise = wise;
        this.barcode = barcode;
        this.isidcode = isidcode;
        this.productcode = productcode;
        this.productcodedef = productcodedef;
        this.leastsale = leastsale;
        this.cost = cost;
        this.abcsort = abcsort;
        this.explain = explain;
        this.memo = memo;
        this.useflag = useflag;
        this.isunify = isunify;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.nccode=nccode;
    }

    /** default constructor */
    public Product() {
    }

    /** minimal constructor */
    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductnameen() {
        return this.productnameen;
    }

    public void setProductnameen(String productnameen) {
        this.productnameen = productnameen;
    }

    public String getPycode() {
        return this.pycode;
    }

    public void setPycode(String pycode) {
        this.pycode = pycode;
    }

    public String getPsid() {
        return this.psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public Integer getCountunit() {
        return this.countunit;
    }

    public Integer getScatterunitid() {
		return scatterunitid;
	}

	public void setScatterunitid(Integer scatterunitid) {
		this.scatterunitid = scatterunitid;
	}

	public void setCountunit(Integer countunit) {
        this.countunit = countunit;
    }
    
    

    public Integer getSunit() {
		return sunit;
	}

	public void setSunit(Integer sunit) {
		this.sunit = sunit;
	}

	public Integer getBrand() {
        return this.brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Integer getWise() {
        return this.wise;
    }

    public void setWise(Integer wise) {
        this.wise = wise;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getIsidcode() {
        return this.isidcode;
    }

    public void setIsidcode(Integer isidcode) {
        this.isidcode = isidcode;
    }

    public String getProductcode() {
        return this.productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductcodedef() {
        return this.productcodedef;
    }

    public void setProductcodedef(String productcodedef) {
        this.productcodedef = productcodedef;
    }

    public Double getLeastsale() {
        return this.leastsale;
    }

    public void setLeastsale(Double leastsale) {
        this.leastsale = leastsale;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getAbcsort() {
        return this.abcsort;
    }

    public void setAbcsort(Integer abcsort) {
        this.abcsort = abcsort;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getUseflag() {
        return this.useflag;
    }

    public void setUseflag(Integer useflag) {
        this.useflag = useflag;
    }

    public Integer getIsunify() {
        return this.isunify;
    }

    public void setIsunify(Integer isunify) {
        this.isunify = isunify;
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
    
    

    public Integer getIsbatch() {
		return isbatch;
	}

	public void setIsbatch(Integer isbatch) {
		this.isbatch = isbatch;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Product) ) return false;
        Product castOther = (Product) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getBoxquantity() {
		return boxquantity;
	}

	public void setBoxquantity(Double boxquantity) {
		this.boxquantity = boxquantity;
	}

	public Integer getCanImport() {
		return canImport;
	}

	public void setCanImport(Integer canImport) {
		this.canImport = canImport;
	}

	public String getProtectDate() {
		return protectDate;
	}

	public void setProtectDate(String protectDate) {
		this.protectDate = protectDate;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}

	public String getPackSizeNameEn() {
		return packSizeNameEn;
	}

	public void setPackSizeNameEn(String packSizeNameEn) {
		this.packSizeNameEn = packSizeNameEn;
	}

	public Integer getExpiryDays() {
		return expiryDays;
	}

	public void setExpiryDays(Integer expiryDays) {
		this.expiryDays = expiryDays;
	}

	public Integer getCartonPrintFlag() {
		return cartonPrintFlag;
	}

	public void setCartonPrintFlag(Integer cartonPrintFlag) {
		this.cartonPrintFlag = cartonPrintFlag;
	}

	public Integer getPrimaryPrintFlag() {
		return primaryPrintFlag;
	}

	public void setPrimaryPrintFlag(Integer primaryPrintFlag) {
		this.primaryPrintFlag = primaryPrintFlag;
	}

	public Integer getCartonScanning() {
		return cartonScanning;
	}

	public void setCartonScanning(Integer cartonScanning) {
		this.cartonScanning = cartonScanning;
	}

	public String getMatericalChDes() {
		return matericalChDes;
	}

	public void setMatericalChDes(String matericalChDes) {
		this.matericalChDes = matericalChDes;
	}

	public String getMatericalEnDes() {
		return matericalEnDes;
	}

	public void setMatericalEnDes(String matericalEnDes) {
		this.matericalEnDes = matericalEnDes;
	}

	public String getCountUnitName() {
		return countUnitName;
	}

	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public Integer getLinkMode() {
		return linkMode;
	}

	public void setLinkMode(Integer linkMode) {
		this.linkMode = linkMode;
	}

	public Integer getRegCertType() {
		return regCertType;
	}

	public void setRegCertType(Integer regCertType) {
		this.regCertType = regCertType;
	}

	public String getRegCertCode() {
		return regCertCode;
	}

	public void setRegCertCode(String regCertCode) {
		this.regCertCode = regCertCode;
	}

	public String getRegCertUser() {
		return regCertUser;
	}

	public void setRegCertUser(String regCertUser) {
		this.regCertUser = regCertUser;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getSpecCode() {
		return specCode;
	}

	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}

	public Integer getProduceType() {
		return produceType;
	}

	public void setProduceType(Integer produceType) {
		this.produceType = produceType;
	}

	public Integer getPrimaryCodeType() {
		return primaryCodeType;
	}

	public void setPrimaryCodeType(Integer primaryCodeType) {
		this.primaryCodeType = primaryCodeType;
	}

	public Integer getInnerProduceType() {
		return innerProduceType;
	}

	public void setInnerProduceType(Integer innerProduceType) {
		this.innerProduceType = innerProduceType;
	}

	public String getRegCertCodeFixed() {
		return regCertCodeFixed;
	}

	public void setRegCertCodeFixed(String regCertCodeFixed) {
		this.regCertCodeFixed = regCertCodeFixed;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public Integer getPackingRatio() {
		return packingRatio;
	}

	public void setPackingRatio(Integer packingRatio) {
		this.packingRatio = packingRatio;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getCopys() {
		return copys;
	}

	public void setCopys(Integer copys) {
		this.copys = copys;
	}

//	public String getValidResult() {
//		return validResult;
//	}
//
//	public void setValidResult(String validResult) {
//		this.validResult = validResult;
//	}

	public String getInspectionInstitution() {
		return inspectionInstitution;
	}

	public void setInspectionInstitution(String inspectionInstitution) {
		this.inspectionInstitution = inspectionInstitution;
	}
	
}
