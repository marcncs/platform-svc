package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Equip implements Serializable {

    /** identifier field */
    private String id;

    private Integer objectsort;
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

    /** nullable persistent field */
    private String transportnum;

    /** nullable persistent field */
    private Integer transit;

    /** nullable persistent field */
    private Double piece;

    /** nullable persistent field */
    private Double eratotalsum;

    /** nullable persistent field */
    private Date equipdate;

    /** nullable persistent field */
    private Integer motorman;

    /** nullable persistent field */
    private String carbrand;

    /** nullable persistent field */
    private String rushdesc;

    /** nullable persistent field */
    private Double rushsum;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public Equip(String id, String cid, String cname, String clinkman, String tel, String addr, Integer transportmode, String transportnum, Integer transit, Double piece, Double eratotalsum, Date equipdate, Integer motorman, String carbrand, String rushdesc, Double rushsum, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String keyscontent) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.clinkman = clinkman;
        this.tel = tel;
        this.addr = addr;
        this.transportmode = transportmode;
        this.transportnum = transportnum;
        this.transit = transit;
        this.piece = piece;
        this.eratotalsum = eratotalsum;
        this.equipdate = equipdate;
        this.motorman = motorman;
        this.carbrand = carbrand;
        this.rushdesc = rushdesc;
        this.rushsum = rushsum;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public Equip() {
    }

    /** minimal constructor */
    public Equip(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public Integer getObjectsort() {
		return objectsort;
	}

	public void setObjectsort(Integer objectsort) {
		this.objectsort = objectsort;
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

    public String getRushdesc() {
        return this.rushdesc;
    }

    public void setRushdesc(String rushdesc) {
        this.rushdesc = rushdesc;
    }

    public Double getRushsum() {
        return this.rushsum;
    }

    public void setRushsum(Double rushsum) {
        this.rushsum = rushsum;
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

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Equip) ) return false;
        Equip castOther = (Equip) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
