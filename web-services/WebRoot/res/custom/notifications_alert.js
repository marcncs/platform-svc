/**
 * 该js文件主要是提供通知提示功能
 */

//引入Growl Notifications插件jquery.gritter
document.write('<script src="/res/bracket/js/jquery.gritter.min.js"></script>')
//引入Growl Notifications插件jquery.gritter的css文件
var growlCss = document.createElement('link');
growlCss.href="/res/bracket/css/jquery.gritter.css";
growlCss.rel="stylesheet";
growlCss.type="text/css";
document.getElementsByTagName('head')[0].appendChild(growlCss);

/**
 * 显示提示内容
 * @param	content	提示内容，required
 * @param	status	状态，optional， 分4种状态，success, error, warning, info，默认为error
 * @param	displayTime	显示时间（显示多长时间后关闭），optional
 * @returns	
 */
function notification(content, status, displayTime) {
	var time = displayTime || "20000";
	var state = status || "error";
	var className = "";
	if (state === 'success') {
		className = "growl-success";
	} else if (state === 'error') {
		className = "growl-danger";
	} else if (state === 'warning') {
		className = "growl-warning";
	} else if (state === 'info') {
		className = "growl-info";
	} else {
		className = "growl-danger";
	}
	
	jQuery.gritter.add({
		title: '<i class="fa fa-exclamation-triangle"></i> 提示',
		text: content,
		class_name: className,
		sticky: false,
		time: time
	});
}