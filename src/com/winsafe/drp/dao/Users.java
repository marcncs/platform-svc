package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.winsafe.hbm.util.Encrypt;

/** @author Hibernate CodeGenerator */
public class Users implements Serializable {

    /** identifier field */
    private Integer userid; 

    /** nullable persistent field */
    private String loginname;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private String approvepwd;

    /** nullable persistent field */
    private String realname;

    /** nullable persistent field */
    private String nameen;

    /** nullable persistent field */
    private Integer sex;

    /** nullable persistent field */
    private Date birthday;

    /** nullable persistent field */
    private String idcard;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String officetel;

    /** nullable persistent field */
    private String hometel;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String qq;

    /** nullable persistent field */
    private String msn;
    
    private Integer province;
    
    private Integer city;
    
    private Integer areas;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private Date createdate;
    
    private Date validate;

    /** nullable persistent field */
    private Date lastlogin;

    /** nullable persistent field */
    private Integer logintimes;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer status;

    /** nullable persistent field */
    private Integer isonline;

    /** nullable persistent field */
    private Integer islogin;

    /** nullable persistent field */
    private Integer iscall;
    
    private String nccode;
    
    private String valkey;
    
    //用户类型
    private Integer UserType;
    
    private String imgurl;
    
    private String userTypeName;
    //用户类型
    private Integer isCwid; 
    
    private Integer tryCount; 
    private Integer isLocked; 
    
    /** full constructor */
    public Users(Integer userid, String loginname, String password, String approvepwd, String realname, String nameen, Integer sex, Date birthday, String idcard, String mobile, String officetel, String hometel, String email, String qq, String msn, String addr, Date createdate, Date lastlogin, Integer logintimes, String makeorganid, Integer makedeptid, Integer status, Integer isonline, Integer islogin, Integer iscall,String nccode,Date validate,String valkey) {
        this.userid = userid;
        this.loginname = loginname;
        this.password = password;
        this.approvepwd = approvepwd;
        this.realname = realname;
        this.nameen = nameen;
        this.sex = sex;
        this.birthday = birthday;
        this.idcard = idcard;
        this.mobile = mobile;
        this.officetel = officetel;
        this.hometel = hometel;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.addr = addr;
        this.createdate = createdate;
        this.lastlogin = lastlogin;
        this.logintimes = logintimes;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.status = status;
        this.isonline = isonline;
        this.islogin = islogin;
        this.iscall = iscall;
        this.nccode = nccode;
        this.validate = validate;
        this.valkey = valkey;
    }

    /** default constructor */
    public Users() {
    }

    public Integer getUserType() {
		return UserType;
	}

	public void setUserType(Integer userType) {
		UserType = userType;
	}

	/** minimal constructor */
    public Users(Integer userid) {
        this.userid = userid;
    }
    

    public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLoginname() {
        return this.loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApprovepwd() {
        return this.approvepwd;
    }

    public void setApprovepwd(String approvepwd) {
        this.approvepwd = approvepwd;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNameen() {
        return this.nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public String getDecodedMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficetel() {
        return this.officetel;
    }

    public void setOfficetel(String officetel) {
        this.officetel = officetel;
    }

    public String getHometel() {
        return this.hometel;
    }

    public void setHometel(String hometel) {
        this.hometel = hometel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getLastlogin() {
        return this.lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Integer getLogintimes() {
        return this.logintimes;
    }

    public void setLogintimes(Integer logintimes) {
        this.logintimes = logintimes;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsonline() {
        return this.isonline;
    }

    public void setIsonline(Integer isonline) {
        this.isonline = isonline;
    }

    public Integer getIslogin() {
        return this.islogin;
    }

    public void setIslogin(Integer islogin) {
        this.islogin = islogin;
    }

    public Integer getIscall() {
        return this.iscall;
    }

    public void setIscall(Integer iscall) {
        this.iscall = iscall;
    }
	public Date getValidate() {
		return validate;
	}

	public void setValidate(Date validate) {
		this.validate = validate;
	}

	public String getValkey() {
		return valkey;
	}

	public void setValkey(String valkey) {
		this.valkey = valkey;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getUserid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Users) ) return false;
        Users castOther = (Users) other;
        return new EqualsBuilder()
            .append(this.getUserid(), castOther.getUserid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserid())
            .toHashCode();
    }

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public Integer getIsCwid() {
		return isCwid;
	}

	public void setIsCwid(Integer isCwid) {
		this.isCwid = isCwid;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	
}
