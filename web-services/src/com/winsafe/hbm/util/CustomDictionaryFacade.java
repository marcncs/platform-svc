package com.winsafe.hbm.util;

import java.util.ArrayList;
import java.util.List;

public enum CustomDictionaryFacade implements ICustomDictionary {
	INSTANCE;
	
	private static final List<ICustomDictionary> dictionarys = new ArrayList<ICustomDictionary>();
	
	static {
//		dictionarys.add(new BaikeDictionary());
//		dictionarys.add(new ProductDictionary());
//		dictionarys.add(new CropDictionary());
	}

	@Override
	public void loadCustomDictionary() {
		for (ICustomDictionary dictionary : dictionarys) {
			dictionary.loadCustomDictionary();
		}
	}

	@Override
	public KeywordUrl getUrl(String keyword) {
		KeywordUrl url = null;
		for (ICustomDictionary dictionary : dictionarys) {
			url = dictionary.getUrl(keyword);
			if (url != null) break;
		}
		return url;
	}

}
