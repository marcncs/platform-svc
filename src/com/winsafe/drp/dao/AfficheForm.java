package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

public class AfficheForm extends ActionForm{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -8197827352176965418L;

		private Integer id;

		private String browsestatus;
	    /** nullable persistent field */
	    private String affichetitle;

	    /** nullable persistent field */
	    private String affichecontent;

	    /** nullable persistent field */
	    private String affichetype;

	    /** nullable persistent field */
	    private String ponderance;
	    
	    private Integer affichedept;

	    /** nullable persistent field */
	    private String publishdate;

	    /** persistent field */
	    private Integer publishman;
	    
	    private String publishmanname;

		public String getPublishmanname() {
			return publishmanname;
		}

		public void setPublishmanname(String publishmanname) {
			this.publishmanname = publishmanname;
		}

		public Integer getPublishman() {
			return publishman;
		}

		public String getAffichecontent() {
			return affichecontent;
		}

		public void setAffichecontent(String affichecontent) {
			this.affichecontent = affichecontent;
		}

		public String getAffichetitle() {
			return affichetitle;
		}

		public void setAffichetitle(String affichetitle) {
			this.affichetitle = affichetitle;
		}

		public String getAffichetype() {
			return affichetype;
		}

		public void setAffichetype(String affichetype) {
			this.affichetype = affichetype;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPonderance() {
			return ponderance;
		}

		public void setPonderance(String ponderance) {
			this.ponderance = ponderance;
		}

		public String getPublishdate() {
			return publishdate;
		}

		public void setPublishdate(String publishdate) {
			this.publishdate = publishdate;
		}

		public String getBrowsestatus() {
			return browsestatus;
		}

		public void setBrowsestatus(String browsestatus) {
			this.browsestatus = browsestatus;
		}

		public Integer getAffichedept() {
			return affichedept;
		}

		public void setAffichedept(Integer affichedept) {
			this.affichedept = affichedept;
		}

		public void setPublishman(Integer publishman) {
			this.publishman = publishman;
		}


}
