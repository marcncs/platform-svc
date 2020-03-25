package com.winsafe.drp.report.pojo;

import java.util.Date;

public class DailyReport {
	
	private String regionpid;
	private String regionpcode;
	private String regionpname;
	private String regionpuserid;
	private String regionpusername;
	private Integer regionid; 
	private String regioncode;
    private String regionname;
    private String regionuid;
    private String regionuname;
    private Double price;
    private String dealdate;
    private String outorganid;
    private String outorganoecode;
    private String outorganname;
    private String inorganid;
    private String inparentorganid;
    private String inparentorganname;
    private String inparentorganoecode;
    private String inorganoecode;
    private String inorganname;
    private String productid;
    private String productname;
    private String specmode;
    private Integer unitid;
    private Double quantity;
    private Double boxnum;
    private Double scatternum;
    private String salemanid;
    private String salemanname;
    private String officeid; 
    private Date makedate;
    private String productStruct;
    private String regionusercode; //办事处经理编号
    private String salemancode; //主管编号
    private String salemanbelong; //主管所属区域
    private String salemanrank; //主管职级
    private Double regionpimporttarget; //大区进口指标
    private Double regionpchmantarget;//大区国产成人指标
    private Double regionpchbabytarget; //大区国产婴儿指标
    private Double regionptotaltarget;//--大区总指标
    private Double regionimporttarget;//--办事处进口指标
    private Double regionchmantarget;//--办事处国产成人指标
    private Double regionchbabytarget;//--办事处国产婴儿指标
    private Double regiontotaltarget;//--办事处总指标
    private Double dealerimporttarget;//--经销商进口指标
    private Double dealerchmantarget;//--经销商国产成人指标
    private Double dealerchbabytarget;//--经销商国产婴儿指标
    private Double dealertotaltarget;//--经销商总指标
    private Double frontimporttarget;//--门店进口指标
    private Double frontchmantarget;//--门店国产成人指标
    private Double frontchbabytarget;//--门店国产婴儿指标
    private Double fronttotaltarget;//--门店总指标
    private Double salemanimporttarget;//--主管进口指标
    private Double salemanchmantarget;//--主管国产成人指标
    private Double salemanchbabytarget;//--主管国产婴儿指标
    private Double salemantotaltarget;//--主管总指标
    private Double salemanimportcount;//--主管进口网点数
    private Double salemanchmancount;//--主管国产成人网点数
    private Double salemanchbabycount;//--主管国产婴儿网点数
    private Double salemantotalcount;//--主管总网点数
    
	public String getRegionpid() {
		return regionpid;
	}
	public void setRegionpid(String regionpid) {
		this.regionpid = regionpid;
	}
	public String getRegionpname() {
		return regionpname;
	}
	public void setRegionpname(String regionpname) {
		this.regionpname = regionpname;
	}
	public String getRegionpuserid() {
		return regionpuserid;
	}
	public void setRegionpuserid(String regionpuserid) {
		this.regionpuserid = regionpuserid;
	}
	public String getRegionpusername() {
		return regionpusername;
	}
	public void setRegionpusername(String regionpusername) {
		this.regionpusername = regionpusername;
	}
	public Integer getRegionid() {
		return regionid;
	}
	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public String getRegionuid() {
		return regionuid;
	}
	public void setRegionuid(String regionuid) {
		this.regionuid = regionuid;
	}
	public String getRegionuname() {
		return regionuname;
	}
	public void setRegionuname(String regionuname) {
		this.regionuname = regionuname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getOutorganid() {
		return outorganid;
	}
	public void setOutorganid(String outorganid) {
		this.outorganid = outorganid;
	}
	public String getOutorganoecode() {
		return outorganoecode;
	}
	public void setOutorganoecode(String outorganoecode) {
		this.outorganoecode = outorganoecode;
	}
	public String getOutorganname() {
		return outorganname;
	}
	public void setOutorganname(String outorganname) {
		this.outorganname = outorganname;
	}
	public String getInorganid() {
		return inorganid;
	}
	public void setInorganid(String inorganid) {
		this.inorganid = inorganid;
	}
	public String getInparentorganid() {
		return inparentorganid;
	}
	public void setInparentorganid(String inparentorganid) {
		this.inparentorganid = inparentorganid;
	}
	public String getInparentorganname() {
		return inparentorganname;
	}
	public void setInparentorganname(String inparentorganname) {
		this.inparentorganname = inparentorganname;
	}
	public String getInorganoecode() {
		return inorganoecode;
	}
	public void setInorganoecode(String inorganoecode) {
		this.inorganoecode = inorganoecode;
	}
	public String getInorganname() {
		return inorganname;
	}
	public void setInorganname(String inorganname) {
		this.inorganname = inorganname;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}
	public Integer getUnitid() {
		return unitid;
	}
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getBoxnum() {
		return boxnum;
	}
	public void setBoxnum(Double boxnum) {
		this.boxnum = boxnum;
	}
	public Double getScatternum() {
		return scatternum;
	}
	public void setScatternum(Double scatternum) {
		this.scatternum = scatternum;
	}
	public String getSalemanid() {
		return salemanid;
	}
	public void setSalemanid(String salemanid) {
		this.salemanid = salemanid;
	}
	public String getOfficeid() {
		return officeid;
	}
	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	public Date getMakedate() {
		return makedate;
	}
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	public String getProductStruct() {
		return productStruct;
	}
	public void setProductStruct(String productStruct) {
		this.productStruct = productStruct;
	}
	public String getRegionusercode() {
		return regionusercode;
	}
	public void setRegionusercode(String regionusercode) {
		this.regionusercode = regionusercode;
	}
	public String getSalemancode() {
		return salemancode;
	}
	public void setSalemancode(String salemancode) {
		this.salemancode = salemancode;
	}
	public String getSalemanbelong() {
		return salemanbelong;
	}
	public void setSalemanbelong(String salemanbelong) {
		this.salemanbelong = salemanbelong;
	}
	public String getSalemanrank() {
		return salemanrank;
	}
	public void setSalemanrank(String salemanrank) {
		this.salemanrank = salemanrank;
	}
	public Double getRegionpimporttarget() {
		return regionpimporttarget;
	}
	public void setRegionpimporttarget(Double regionpimporttarget) {
		this.regionpimporttarget = regionpimporttarget;
	}
	public Double getRegionpchmantarget() {
		return regionpchmantarget;
	}
	public void setRegionpchmantarget(Double regionpchmantarget) {
		this.regionpchmantarget = regionpchmantarget;
	}
	public Double getRegionpchbabytarget() {
		return regionpchbabytarget;
	}
	public void setRegionpchbabytarget(Double regionpchbabytarget) {
		this.regionpchbabytarget = regionpchbabytarget;
	}
	public Double getRegionptotaltarget() {
		return regionptotaltarget;
	}
	public void setRegionptotaltarget(Double regionptotaltarget) {
		this.regionptotaltarget = regionptotaltarget;
	}
	public Double getRegionimporttarget() {
		return regionimporttarget;
	}
	public void setRegionimporttarget(Double regionimporttarget) {
		this.regionimporttarget = regionimporttarget;
	}
	public Double getRegionchmantarget() {
		return regionchmantarget;
	}
	public void setRegionchmantarget(Double regionchmantarget) {
		this.regionchmantarget = regionchmantarget;
	}
	public Double getRegionchbabytarget() {
		return regionchbabytarget;
	}
	public void setRegionchbabytarget(Double regionchbabytarget) {
		this.regionchbabytarget = regionchbabytarget;
	}
	public Double getRegiontotaltarget() {
		return regiontotaltarget;
	}
	public void setRegiontotaltarget(Double regiontotaltarget) {
		this.regiontotaltarget = regiontotaltarget;
	}
	public Double getDealerimporttarget() {
		return dealerimporttarget;
	}
	public void setDealerimporttarget(Double dealerimporttarget) {
		this.dealerimporttarget = dealerimporttarget;
	}
	public Double getDealerchmantarget() {
		return dealerchmantarget;
	}
	public void setDealerchmantarget(Double dealerchmantarget) {
		this.dealerchmantarget = dealerchmantarget;
	}
	public Double getDealerchbabytarget() {
		return dealerchbabytarget;
	}
	public void setDealerchbabytarget(Double dealerchbabytarget) {
		this.dealerchbabytarget = dealerchbabytarget;
	}
	public Double getDealertotaltarget() {
		return dealertotaltarget;
	}
	public void setDealertotaltarget(Double dealertotaltarget) {
		this.dealertotaltarget = dealertotaltarget;
	}
	public Double getFrontimporttarget() {
		return frontimporttarget;
	}
	public void setFrontimporttarget(Double frontimporttarget) {
		this.frontimporttarget = frontimporttarget;
	}
	public Double getFrontchmantarget() {
		return frontchmantarget;
	}
	public void setFrontchmantarget(Double frontchmantarget) {
		this.frontchmantarget = frontchmantarget;
	}
	public Double getFrontchbabytarget() {
		return frontchbabytarget;
	}
	public void setFrontchbabytarget(Double frontchbabytarget) {
		this.frontchbabytarget = frontchbabytarget;
	}
	public Double getFronttotaltarget() {
		return fronttotaltarget;
	}
	public void setFronttotaltarget(Double fronttotaltarget) {
		this.fronttotaltarget = fronttotaltarget;
	}
	public Double getSalemanimporttarget() {
		return salemanimporttarget;
	}
	public void setSalemanimporttarget(Double salemanimporttarget) {
		this.salemanimporttarget = salemanimporttarget;
	}
	public Double getSalemanchmantarget() {
		return salemanchmantarget;
	}
	public void setSalemanchmantarget(Double salemanchmantarget) {
		this.salemanchmantarget = salemanchmantarget;
	}
	public Double getSalemanchbabytarget() {
		return salemanchbabytarget;
	}
	public void setSalemanchbabytarget(Double salemanchbabytarget) {
		this.salemanchbabytarget = salemanchbabytarget;
	}
	public Double getSalemantotaltarget() {
		return salemantotaltarget;
	}
	public void setSalemantotaltarget(Double salemantotaltarget) {
		this.salemantotaltarget = salemantotaltarget;
	}
	public Double getSalemanimportcount() {
		return salemanimportcount;
	}
	public void setSalemanimportcount(Double salemanimportcount) {
		this.salemanimportcount = salemanimportcount;
	}
	public Double getSalemanchmancount() {
		return salemanchmancount;
	}
	public void setSalemanchmancount(Double salemanchmancount) {
		this.salemanchmancount = salemanchmancount;
	}
	public Double getSalemanchbabycount() {
		return salemanchbabycount;
	}
	public void setSalemanchbabycount(Double salemanchbabycount) {
		this.salemanchbabycount = salemanchbabycount;
	}
	public Double getSalemantotalcount() {
		return salemantotalcount;
	}
	public void setSalemantotalcount(Double salemantotalcount) {
		this.salemantotalcount = salemantotalcount;
	}
	public String getSalemanname() {
		return salemanname;
	}
	public void setSalemanname(String salemanname) {
		this.salemanname = salemanname;
	}
	public String getInparentorganoecode() {
		return inparentorganoecode;
	}
	public void setInparentorganoecode(String inparentorganoecode) {
		this.inparentorganoecode = inparentorganoecode;
	}
	public String getRegionpcode() {
		return regionpcode;
	}
	public void setRegionpcode(String regionpcode) {
		this.regionpcode = regionpcode;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	
    
}
