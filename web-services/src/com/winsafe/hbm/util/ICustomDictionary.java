package com.winsafe.hbm.util;


public interface ICustomDictionary {
	
	public void loadCustomDictionary();
	
	public KeywordUrl getUrl(String keyword);
	
	class KeywordUrl {
		public int type;	//1：baike， 2：product
		public String url;
		
		public KeywordUrl(int type, String url) {
			this.type = type;
			this.url = url;
		}
	}
}
