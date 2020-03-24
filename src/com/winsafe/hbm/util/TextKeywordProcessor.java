package com.winsafe.hbm.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hankcs.hanlp.seg.common.Term;

public class TextKeywordProcessor {
	
	static {
		CustomDictionaryFacade.INSTANCE.loadCustomDictionary();
	}
	
	public static String process(String content, String[] ignoreWords) {
		if (StringUtils.isBlank(content)) return null;
		
		//分词器分词
		List<Term> terms = KeywordExtractor.extractKeyword(content);
		boolean isHtmlTag = false;
		boolean isHtmlLink = false; //是否A标签链接内容，如果是则不会进行再次链接添加
		StringBuilder sbContent = new StringBuilder();
		if (terms != null && !terms.isEmpty()) {
//			System.out.println(terms);
			for (Term term : terms) {
				//获取关键词的url
				ICustomDictionary.KeywordUrl keywordUrl = CustomDictionaryFacade.INSTANCE.getUrl(term.word);
				isHtmlTag = validHtmlTag(term.word, isHtmlTag);
				isHtmlLink = validHtmlLink(term.word, isHtmlLink);
				if (keywordUrl == null || isIgnoreWords(term.word, ignoreWords)) {
					//关键词为null，或者需要忽略则不添加链接
					sbContent.append(term.word);
				} else {
					StringBuilder sb = new StringBuilder();
					if (keywordUrl.type == 1 && !isHtmlTag && !isHtmlLink) {
						//关键词类型为baike且不是html标签属性值或A标签，则添加baike的链接
						sb.append("<a href=\"").append(keywordUrl.url).append("\" class=\"baike-link\">").append(term.word).append("</a>");
					} else if (keywordUrl.type == 2 && !isHtmlTag && !isHtmlLink) {
						//关键词类型为产品且不是html标签属性值或A标签，则添加产品的链接
						sb.append("<a href=\"").append(keywordUrl.url).append("\" class=\"product-link\">").append(term.word).append("</a>");
					} else {
						sb.append(term.word);
					}
					sbContent.append(sb.toString());
				}
				
			}
		}
		
		return sbContent.toString();
	}
	
	/**
	 * 判断关键词是否为A标签链接内容
	 * @param word
	 * @param flag
	 * @return
	 */
	private static boolean validHtmlLink(String word, boolean isHtmlLink) {
		if (word != null && word.trim().equals("<a")) {
			isHtmlLink = true;
		} else if (word != null && word.trim().equals("a>")){
			isHtmlLink = false;
		}
		return isHtmlLink;
	}
	
	/**
	 * 判断关键词是否为html标签的属性值
	 * @param word
	 * @param flag
	 * @return
	 */
	private static boolean validHtmlTag(String word, boolean flag) {
		char[] words = word.toCharArray();
		for (char c : words) {
			if (c == '<') {
				flag = true;
			}else if (c == '>') {
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 判断关键词是否在需要忽略的关键词列表中
	 * @param word
	 * @param ignoreWords
	 * @return
	 */
	private static boolean isIgnoreWords(String word, String[] ignoreWords) {
		if (ignoreWords == null || ignoreWords.length == 0) return false;
		
		for (String ignoreWord : ignoreWords) {
			if (ignoreWord.equals(word)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) throws Exception, IOException {
//		CustomDictionaryFacade.INSTANCE.loadCustomDictionary();
		
		String content = "    ●特效防治同翅目刺吸式害虫<a href=\"/test/fs.jsp\">飞虱</a>、<a href=\"/test/lala.jsp\">拉拉粉虱</a>、粉虱蚜虫、叶蝉等，如近年来<a href=\"http://www.baidu.com\">频繁</a>暴发对部分药剂产生抗性的的稻飞虱、大棚蔬菜烟粉虱、棉花蚜虫等。";
		
//		CustomDictionary.remove("稻象甲");
//		CustomDictionary.add("甲虫线", "nz 100");
////		CustomDictionary.add("线甲虫");
//		CustomDictionary.add("稻象甲（测试）", "b 1");
//		CustomDictionary.add("苞虫等");
//		System.out.println(CustomDictionary.get("苞虫等"));
		
		content = TextKeywordProcessor.process(content, null);
		System.out.println(content);
		
	}
}
