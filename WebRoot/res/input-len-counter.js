/**
 * 实时计算输入框中输入的字符个数，并高亮数字警示用户
 * 
 * 使用时，必须在input或者textarea元素中加入自定义"data-max-len"属性，例如：data-max-len="20"
 * 完整input实例：<input type="text" data-max-len="20" required>
 */

jQuery().ready(function(){
	
	jQuery("[data-max-len]").each(function() {
		var tagName = jQuery(this)[0].tagName;
		var maxLen = jQuery(this).attr("data-max-len");
		if (tagName === 'INPUT') {
			jQuery(this).after('<span class="input-group-addon">0/' + maxLen + '</span>');
		} else if (tagName === 'TEXTAREA') {
			jQuery(this).after('<span class="pull-right">0/' + maxLen + '</span>');
		} else {
			console.log(tagName);
		}
	});
});

jQuery(document).keydown(function() {
	//计算输入字符长度必须在1毫秒或更多毫秒后执行，不然当前按键不计算在类
	var mytime = setInterval(function(){characterLenCounter(jQuery(':focus')); clearInterval(mytime);}, 1);
});

//输入字符串长度计算器，elementObj当前输入框
function characterLenCounter(elementObj) {
	//只有加入"data-max-len" 属性的输入框才计算长度
	if(typeof(elementObj.attr("data-max-len")) === "undefined") return ;
	
	//获取焦点输入框中字符串长度
	var inputText = elementObj.val();
	var inputTextLen = inputText.length;
	
	//获取长度提示容器（span）
	var tipContainer = elementObj.next('span').first();
	//获取长度提示容器的字符串
	var tipText = tipContainer.text();
	//获取该输入框最大的长度值
	var tipTextArray = tipText.split("/",2);
	var maxTextLen = tipTextArray[1];
	
	//显示当前输入的字符串长度
	tipContainer.text(inputTextLen + "/" + maxTextLen);
	if (inputTextLen > maxTextLen) {
		//当输入长度大于要求最大长度时，高亮显示
		tipContainer.css("color","#a94442");
	} else {
		//当输入长度小于等于要求最大长度时，去掉高亮
		tipContainer.removeAttr("style")
	}
}