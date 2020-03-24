/**
 *	商家后台管理平台的api访问js封装
 */

function OpsAdminJsSDK() {
	
	//url默认为本地调试
	this.baseUrl = "/";
	
	function ajax(url, type, async, params) {
		var data = null;
		jQuery.ajax({url: url, type: type, dataType: "json", async: async, data: params, 
			success: function(result) {
				data = result.data;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				var result = jQuery.parseJSON(XMLHttpRequest.responseText);
				if (result) {
					throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
				} else {
					throw {status: XMLHttpRequest.status};
				}
			}
		});
		
		return data;
	}
	
	//---------------------平台新闻管理---------------------
	/** 新增新闻 */
	this.addNews = function(title, summary, content, picUrl, isShowPic) {
		var url = this.baseUrl + "ops_admin/news";
		var paramData = {title: title, summary: summary, content: content, 
				pic_url: picUrl, is_show_pic: isShowPic};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	/** 修改新闻 */
	this.updateNews = function(newsId, title, summary, content, picUrl, isShowPic) {
		var url = this.baseUrl + "ops_admin/news/" + newsId;
		var paramData = {title: title, summary: summary, content: content,
				pic_url: picUrl, is_show_pic: isShowPic};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	/** 获取新闻列表，分页 */
	this.getNewsByPage = function(pageContainerClassName, callback, keyword, startDate, endDate) {
		var url = this.baseUrl + "ops_admin/news/search";
		var params = {keyword: keyword, start_date: startDate, end_date: endDate};
		ajaxQueryByPage({url:url, pageSize: 10, container: pageContainerClassName, 
			data:params, successCallback:callback});
	}
	
	/** 获取新闻详细信息 */
	this.getNews = function(newsId) {
		var url = this.baseUrl + "ops_admin/news/" + newsId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 删除新闻 */
	this.deleteNews = function(newsId) {
		var url = this.baseUrl + "ops_admin/news/" + newsId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	//---------------------平台新闻管理--结束-------------------
}

var opsAdmin = new OpsAdminJsSDK();
