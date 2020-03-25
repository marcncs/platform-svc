package com.winsafe.hbm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class TextUtil {

	/** 只获取html代码中的文字内容，过滤html标签效果和处理转义字符 */
	public static String filterHtmlElement(String htmlStr) {
		if (htmlStr == null) {
			return null;
		}

		String regEx = "<.+?>"; // 表示标签
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(htmlStr);
		String s = m.replaceAll("");
		
		s = StringUtils.replace(s, "&nbsp;", "");
		s = StringUtils.replace(s, "&quot;", "");
		s = StringUtils.replace(s, "&lt;", "<");
		s = StringUtils.replace(s, "&gt;", ">");
		s = StringUtils.replace(s, "&amp;", "&");

		return s;
	}
	
	/** 把textarea输入的换行去除 */
	public static final String disableBreakLineTag(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 10) {
				continue;
			}
			if (c == 13) {
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * 把以逗号分割的字符串转化为int数组
	 * @param str	字符串，例如："112,323,34333"
	 * @return
	 */
	public static final int[] toIntArray(String str) {
		if (str == null || str.length() == 0) return null;
		
		String[] strArray = str.split(",");
		int[] intArray = new int[strArray.length];
		for (int i=0; i< strArray.length; i++) {
			intArray[i] = Integer.valueOf(strArray[i]);
		}
		
		return intArray;
	}
	
	/**
	 * 过滤掉字符串中的html中的a标签元素
	 * @param htmlStr	html字符串
	 * @return
	 */
	public static final String filterHtmlElementA(String htmlStr) {
		if (htmlStr == null || htmlStr.length() == 0) return null;
		
		htmlStr = htmlStr.replaceAll("<a[^>]*>", "");
		htmlStr = htmlStr.replaceAll("</a>", "");
		
		return htmlStr;
	}
	
	/**
	 * 为html中的img标签的src属性值添加后缀
	 * @return
	 */
	public static final String appendSuffix4ImgSrcAttribute(String htmlStr, String suffix) {
		if (htmlStr == null || htmlStr.length() == 0) return null;
		if (suffix == null || suffix.length() == 0) return htmlStr;
		
		//用正则表达式匹配img标签
		String strRegx = "<img.*?(?:>|/>)";
		Pattern imgTag = Pattern.compile (strRegx, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher imgMatcher = imgTag.matcher(htmlStr);
		StringBuffer sb = new StringBuffer();
		while (imgMatcher.find()) {
			//匹配img标签的src属性
			Pattern imgTagSrc = Pattern.compile ("src\\s*=\\s*[\'\"]?([^\'\"]*)[\'\"]?", Pattern.CASE_INSENSITIVE );
			Matcher matcher = imgTagSrc.matcher(imgMatcher.group());
			StringBuffer srcSb = new StringBuffer();
			while (matcher.find()) {
				String group = matcher.group();
				if (group != null) {
					int index = group.indexOf("=");
					if (index >= 0) {
						//去掉“src=”这个前缀
						String url = group.trim().substring(index + 1, group.length());
						
						if (url.indexOf("'") >= 0) {	//如果src url使用单引号
							String srcValue = url.substring(1, url.indexOf("'", 1));
							matcher.appendReplacement(srcSb, "src=\"" + srcValue + suffix + "\"");
						} else if (url.startsWith("\"")) {	//双引号
							String srcValue = url.substring(1, url.indexOf("\"", 1));
							matcher.appendReplacement(srcSb, "src=\"" + srcValue + suffix + "\"");
						} else {
//						String[] values = url.split("\\s");
//						String srcValue = url.split("\\s")[0];
//						matcher.appendReplacement(sb, "src=\"" + srcValue + "?imageView2/2/w/960" + "\"");
						}
					}
				} else {
					
				}
			}
			matcher.appendTail(srcSb);
			imgMatcher.appendReplacement(sb, srcSb.toString());
		}
		imgMatcher.appendTail(sb);
		
		return sb.toString();
	}
	
	/** 抽取html内容中的img标签的图片url地址 */
	public static List<String> extractImgUrls(String content) {
		List<String> imgUrlList = new ArrayList<String>();
        NodeList nodeList;
		try {
	        Parser myParser = new Parser(content);
			nodeList = myParser.extractAllNodesThatMatch(new TagNameFilter("img"));
		} catch (ParserException e) {
			return imgUrlList;
		}
        SimpleNodeIterator it = nodeList.elements();
        while (it.hasMoreNodes()) {
        	ImageTag node = (ImageTag)it.nextNode();
        	imgUrlList.add(node.getImageURL());
        }
        return imgUrlList;
    }
	
	public static void main(String[] args) {
		String ssssString = "<p> <span style='color: rgb(192, 0, 0); font-size: 18px;'><strong>&nbsp; &nbsp;<a href='swe'>产品简介</a>：</strong></span></p>"
				+ "<p>特福力®是美国陶氏益农公司研发的全新专利杀虫剂，其有效成分爱收乐™拥有全新独特的作用机制。特福力®具有高效、广谱、持效期长等特点，对蚧壳虫、飞虱、烟粉虱等刺吸式口器害虫具有很好的防治效果，是害虫抗性管理的理想工具。/p>";
		System.out.println(filterHtmlElementA(ssssString));
		String imgHtmlContent = "<p><span style=\"font-size: 14px; color: rgb(146, 208, 80);\">前言：陶氏益农®的中国好柑橘活动，除了旨在把祖国好山好水种植出的优秀柑橘品种介绍给大家，同时也期望借这么一个机会帮助我们的柑橘种植户生产出更优质的柑橘，提高他们的收入。下面就和大家汇报一下陶氏益农®中国好柑橘第二季的工作情况。这一站，我们来到柑橘种植大省福建。</span></p><p>在福建平和蜜柚的生产过程中，柚子<a href=\"http://m.n369.com/elabel/baike/pest/1413\" class=\"baike-link\">砂皮病</a>以及世代重叠危害的褐圆介一直是果农头疼的主要问题。为了帮助福建平和果农生产出更优质的柚子，特别在去年柚子销售行情低迷的情况下恢复果农的种植及管理信心，陶氏益农®中国好柑橘第二季的项目与当地的合作伙伴紧密结合，于今年4月25日，“中国好柑橘第二季”项目在漳州平和拉开了序幕。<br/></p><p><img src=\"http://cdn.n369.com/FikSNofXTuFWpzmk4KP-hjtQKAg9?imageView2/2/w/1440\"/></p><p>我们邀请了来自平和县各个乡镇的农技骨干和核心种植户参加启动会议。会上，通过对“中国好柑橘”项目以及陶氏益农®大生®+爱收乐®等系列技术方案的了解，让大家更清晰地理解这些方案在柚子种植当中提供的不可或缺的核心价值。随着工作的开展，报名参加“中国好柑橘”项目的农户不断增多。通过实地走访，收集到了不少大生®及<a href=\"/elabel/155\" class=\"product-link\">特福力</a>®忠实拥趸的反馈信息，并帮助他们建立柑橘示范园。</p><p><img src=\"http://cdn.n369.com/FnZt2AKkNPMChMoHmqAI27z4Q3im?imageView2/2/w/1440\"/></p><p>据果农们反应，在套袋前一年使用大生®4次-5次，蜜柚着色均匀，外观油亮，货架期长，生产出的柚子受到多地客商的追棒。一般使用大生®5次的柚子比一般果农生产的柚子可以多3-5毛一斤的收购价。而另外，坚持从第一代<a href=\"http://m.n369.com/elabel/baike/pest/392\" class=\"baike-link\">介壳虫</a>就相隔20-25天，从4月底到6月中，连续使用<a href=\"/elabel/155\" class=\"product-link\">特福力</a>®+<a href=\"/elabel/187\" class=\"product-link\">乐斯本</a>®对难打<a href=\"http://m.n369.com/elabel/baike/pest/392\" class=\"baike-link\">介壳虫</a>进行防治的，后期当其他果园树干爬满<a href=\"http://m.n369.com/elabel/baike/pest/392\" class=\"baike-link\">介壳虫</a>时，使用陶氏益农®方案的果园几乎难觅<a href=\"http://m.n369.com/elabel/baike/pest/392\" class=\"baike-link\">介壳虫</a>的踪影，树势非常好。</p><p><img src=\"http://cdn.n369.com/FhA-JfBUdevSOsYOXMSVH7LMDh7y?imageView2/2/w/1440\"/></p><p>因此，从6月1日，漳州第一场“中国好柑橘”系列现场观摩会在平和县骑岭乡种植户石建南的蜜柚园召开。到场观摩的农户和零售商也在此次观摩会中，提高了自身的用药技术，对后期柚子的管理及销售更有信心。</p><p style=\"text-align: center;\"><strong>中国好柑橘第二季，为生产更高品质，更有“柚”惑的柚子出力！</strong></p>";
		System.out.println(extractImgUrls(imgHtmlContent));
	}

}
