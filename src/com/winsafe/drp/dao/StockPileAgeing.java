package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class StockPileAgeing extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    private Integer tagMinValue;
    
    private Integer tagMaxValue;
    
    private String tagColor;
    

    /** default constructor */
    public StockPileAgeing() {
    }

    /** minimal constructor */
    public StockPileAgeing(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagMinValue() {
		return tagMinValue;
	}

	public void setTagMinValue(Integer tagMinValue) {
		this.tagMinValue = tagMinValue;
	}

	public Integer getTagMaxValue() {
		return tagMaxValue;
	}

	public void setTagMaxValue(Integer tagMaxValue) {
		this.tagMaxValue = tagMaxValue;
	}

	public String getTagColor() {
		return tagColor;
	}

	public void setTagColor(String tagColor) {
		this.tagColor = tagColor;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StockPileAgeing) ) return false;
        StockPileAgeing castOther = (StockPileAgeing) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
    
    @Override
    public StockPileAgeing clone() throws CloneNotSupportedException {
    	StockPileAgeing  spa = null;
    	try {
    		spa = (StockPileAgeing)super.clone();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return spa;
    }

	

}
