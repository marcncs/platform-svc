package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class EquipForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6758521313741590234L;

	/** identifier field */
	private String id;

	/** nullable persistent field */
	private String cid;

	/** nullable persistent field */
	private String cname;

	/** nullable persistent field */
	private String clinkman;

	/** nullable persistent field */
	private String tel;

	/** nullable persistent field */
	private String addr;

	/** nullable persistent field */
	private Integer transportmode;

	private String transportmodename;

	/** nullable persistent field */
	private String transportnum;

	/** nullable persistent field */
	private Integer transit;

	private String transitname;

	/** nullable persistent field */
	private Double piece;

	private String piecename;

	/** nullable persistent field */
	private Double eratotalsum;

	private String eratotalsumname;

	/** nullable persistent field */
	private Date equipdate;

	private String equipdatename;

	/** nullable persistent field */
	private Integer motorman;

	private String motormanname;

	/** nullable persistent field */
	private String carbrand;

	private Integer paymentmode;

	private String paymentmodename;

	private String rushdesc;

	private Double rushsum;

	/** full constructor */

	/** default constructor */
	public EquipForm() {
	}

	/** minimal constructor */
	public EquipForm(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getClinkman() {
		return this.clinkman;
	}

	public void setClinkman(String clinkman) {
		this.clinkman = clinkman;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getTransportmode() {
		return this.transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public String getTransportnum() {
		return this.transportnum;
	}

	public void setTransportnum(String transportnum) {
		this.transportnum = transportnum;
	}

	public Integer getTransit() {
		return this.transit;
	}

	public void setTransit(Integer transit) {
		this.transit = transit;
	}

	public Double getPiece() {
		return this.piece;
	}

	public void setPiece(Double piece) {
		this.piece = piece;
	}

	public Double getEratotalsum() {
		return this.eratotalsum;
	}

	public void setEratotalsum(Double eratotalsum) {
		this.eratotalsum = eratotalsum;
	}

	public Date getEquipdate() {
		return this.equipdate;
	}

	public void setEquipdate(Date equipdate) {
		this.equipdate = equipdate;
	}

	public Integer getMotorman() {
		return this.motorman;
	}

	public void setMotorman(Integer motorman) {
		this.motorman = motorman;
	}

	public String getCarbrand() {
		return this.carbrand;
	}

	public void setCarbrand(String carbrand) {
		this.carbrand = carbrand;
	}

	public String getEquipdatename() {
		return equipdatename;
	}

	public void setEquipdatename(String equipdatename) {
		this.equipdatename = equipdatename;
	}

	public String getEratotalsumname() {
		return eratotalsumname;
	}

	public void setEratotalsumname(String eratotalsumname) {
		this.eratotalsumname = eratotalsumname;
	}

	public String getMotormanname() {
		return motormanname;
	}

	public void setMotormanname(String motormanname) {
		this.motormanname = motormanname;
	}

	public String getPiecename() {
		return piecename;
	}

	public void setPiecename(String piecename) {
		this.piecename = piecename;
	}

	public String getTransitname() {
		return transitname;
	}

	public void setTransitname(String transitname) {
		this.transitname = transitname;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
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

	public String getRushdesc() {
		return rushdesc;
	}

	public void setRushdesc(String rushdesc) {
		this.rushdesc = rushdesc;
	}

	public Double getRushsum() {
		return rushsum;
	}

	public void setRushsum(Double rushsum) {
		this.rushsum = rushsum;
	}

}
