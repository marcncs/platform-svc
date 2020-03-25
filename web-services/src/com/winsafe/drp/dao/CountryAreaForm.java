package com.winsafe.drp.dao;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CountryAreaForm {

    private Long id;
    private String areaname;
    private Long parentid;
    private String parentname;
    private Integer rank;
    private String rankname;
  public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

public String getAreaname() {
    return areaname;
  }

  public void setAreaname(String areaname) {
    this.areaname = areaname;
  }

  public void setParentname(String parentname) {
    this.parentname = parentname;
  }

  public void setParentid(Long parentid) {
    this.parentid = parentid;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Long getParentid() {
    return parentid;
  }

  public String getParentname() {
    return parentname;
  }

}
