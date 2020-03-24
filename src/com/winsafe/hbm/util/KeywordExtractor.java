package com.winsafe.hbm.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 关键词提取器
 * 
 * @author carson
 *
 */
public class KeywordExtractor {
	
	/**
	 * 从内容中提取关键词
	 * 
	 * @param content
	 * @return
	 */
	public static List<Term> extractKeyword(String content) {
		if (StringUtils.isBlank(content)) return null;
		
//		// 首字哈希之后二分的trie树分词
//        BaseSearcher searcher = CustomDictionary.getSearcher(content);
//        List<Keyword> words = new ArrayList<Keyword>();
//        Map.Entry<String, CoreDictionary.Attribute> entry;
//        while ((entry = searcher.next()) != null) {
//        	int offset = searcher.getOffset();
//        	words.add(new Keyword(offset, entry.getKey()));
//        }
        return HanLP.segment(content);
	}
	
}
