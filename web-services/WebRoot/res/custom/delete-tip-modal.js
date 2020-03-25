/**
 * 该js文件主要是提供删除提示框功能
 * 使用bootstrap的popover插件实现，依赖jquery
 * 
 */

/**
 * 绑定删除提示对话框到删除按钮
 * 使用方法：删除按钮元素必须data-id属性（值为需要删除资源的id，例如：data-id="123"）
 * @param	confirmDelCallback	确定删除的回调函数，required
 * @param	弹出位置，取值：left，top，right，bottom
 * @param	绑定的删除按钮，btn不传的话，则删除按钮元素必须有data-toggle='popover'属性
 * @returns	
 */
function bindDeleteModal(confirmDelCallback, placement, btn, text) {
	var callback = confirmDelCallback || function() {alert("请绑定回调函数");}
	
	var placement = placement || "left";
	btn = btn || jQuery("[data-toggle='popover']");
	text = text || "确定删除吗？";
	
	btn.off();
	btn.on("click", function(e) {
		//获取要删除资源的id
	  	var dataId = $(this).attr("data-id");
	  	//生成删除提示框
	  	var wrapper = jQuery('<div></div>');
	  	var tip = jQuery('<span/>');
	  	tip.css("padding-left", "20px").css("padding-right", "20px").css("text-align", "center").text(text);
	  	//生成确定按钮
	  	var confirmBtn = jQuery('<button/>').addClass("btn btn-primary btn-sm").text("确 定");
	  	//为确定按钮绑定事件
	  	confirmBtn.on("click", function() {
	  		callback && callback(dataId);
	  	});
	  	//生成取消按钮，并绑定事件
	  	var cancelBtn = jQuery('<button/>').addClass("btn btn-white btn-sm").text("取 消");
	  	cancelBtn.on("click", function() {
	  		btn.popover('destroy');
	  	});
	  	//加入元素
	  	wrapper.append(tip);
	  	wrapper.append(confirmBtn);
	  	wrapper.append(cancelBtn);
	  	
	  	//调用popover插件显示
	  	btn.popover("destroy");
	  	jQuery(this).popover({html: true, trigger: "focus", container: "body", placement: placement, content: wrapper});
	  	jQuery(this).popover("show");
    });
}

function bindShowModal(btn, html, placement) {
	var placement = placement || "left";
	
	btn.off();
	btn.on("click", function(e) {
	  	//调用popover插件显示
		btn.popover("destroy");
	  	jQuery(this).popover({html: true, trigger: "click", container: "body", placement: placement, content: html});
	  	jQuery(this).popover("show");
	});
}
