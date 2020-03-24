/**
 * 该js文件主要是提供img标签处理功能
 */

/** 给文本字符串中的img标签的src添加后缀 */
function addSuffixForSrc(text, suffix) {
	//匹配图片（g表示匹配所有结果i表示区分大小写）
	var imgReg = /<img.*?(?:>|\/>)/gi;
	//匹配src属性 
	var srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i;
	return text.replace(imgReg, function(matchStr){
		return matchStr.replace(srcReg, function(src){
			var strArray = src.split("=");
			var url = strArray[1];
			if (url.indexOf("'") >= 0 || url.indexOf('"') >= 0) {
				return 'src="' + url.substring(1, url.length-1) + suffix + '"';
			} else {
				return 'src="' + url + suffix + '"';
			}
		});
	});
}
