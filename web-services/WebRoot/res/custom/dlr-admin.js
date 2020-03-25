/**
 *	商家后台管理平台的api访问js封装
 */

function DlrAdminJsSDK() {
	
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
	
	/** 商家注册发送手机验证码短信 */
	this.sendVerifyCodeSMS = function(mobile) {
		var url = this.baseUrl + "dlr_admin/signup/sms/verify_code";
		var params = {mobile : mobile};
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 商家注册 */
	this.signup = function(mobile, md5pwd, smsVerifyCode, fullName) {
		var url = this.baseUrl + "dlr_admin/signup";
		var params = {mobile : mobile, password : md5pwd, sms_verify_code : smsVerifyCode, full_name: fullName};
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 获取商家详细信息 */
	this.getDealer = function() {
		var url = this.baseUrl + "dlr_admin/dealers";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 重置密码 */
	this.resetPassword = function(mobile, password, smsVerifyCode) {
		var url = this.baseUrl + "dlr_admin/accounts/password/retrieve/" + mobile;
		var paramData = {"password" : password, "sms_verify_code" : smsVerifyCode};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	//-------------------商家分店管理--------------------
	/** 新增分店 */
	this.addDealerStore = function(name, tel, province, city, district, address, latlng) {
		var url = this.baseUrl + "dlr_admin/dealers/stores";
		var paramData = {"name" : name, "tel" : tel, "province" : province, "city" : city, 
				"district" : district, "address" : address, "latlng" : latlng};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	/** 编辑分店 */
	this.updateDealerStore = function(id, name, tel, province, city, district, address, latlng) {
		var url = this.baseUrl + "dlr_admin/dealers/stores/" + id;
		var paramData = {"name" : name, "tel" : tel, "province" : province, "city" : city, 
				"district" : district, "address" : address, "latlng" : latlng};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	/** 删除分店 */
	this.deleteStore = function(storeId) {
		var url = this.baseUrl + "dlr_admin/dealers/stores/" + storeId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 获取分店列表，分页 */
	this.getStoresByPage = function(pageContainerClassName, callback) {
		var url = this.baseUrl + "dlr_admin/dealers/stores";
		ajaxQueryByPage({url:url, pageSize:20, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	/** 获取分店详细信息 */
	this.getStore = function(storeId) {
		var url = this.baseUrl + "dlr_admin/dealers/stores/" + storeId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 设置分店排序序号 */
	this.setStoreRank = function(storeId, rank) {
		var url = this.baseUrl + "dlr_admin/dealers/stores/" + storeId + "/rank/" + rank;
		var data = ajax(url, "PUT", false);
		
		return data;
	}
	//-------------------商家分店管理--------------------
	
	/** 编辑关于我们 */
	this.updateAboutUs = function(content) {
		var url = this.baseUrl + "dlr_admin/dealers/aboutus";
		var paramData = {content : content};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	//---------------------方案管理---------------------
	//新增方案分类
	this.addSolutionGroup = function(name, rank) {
		var url = this.baseUrl + "dlr_admin/solutions/groups";
		var paramData = {name : name, rank: rank};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	//修改方案分类
	this.updateSolutionGroup = function(groupId, name, rank) {
		var url = this.baseUrl + "dlr_admin/solutions/groups/" + groupId;
		var paramData = {name : name, rank: rank};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	//删除方案分类
	this.deleteSolutionGroup = function(groupId) {
		var url = this.baseUrl + "dlr_admin/solutions/groups/" + groupId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	//获取用户拥有的所有分类
	this.getOwnSolutionGroups = function() {
		var url = this.baseUrl + "dlr_admin/solutions/groups/own";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	//获取商家的方案列表，分页
	this.getSolutionGroupsByPage = function(pageContainerClassName, callback) {
		var url = this.baseUrl + "dlr_admin/solutions/groups/search";
		ajaxQueryByPage({url:url, pageSize:20, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	//根据分类ID查询分类信息
	this.getSolutionGroup = function(groupId) {
		var url = this.baseUrl + "dlr_admin/solutions/groups/" + groupId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	//设置分店排序序号
	this.setSolutionGroupRank = function(groupId, rank) {
		var url = this.baseUrl + "dlr_admin/solutions/groups/" + groupId + "/rank/" + rank;
		var data = ajax(url, "PUT", false);
		
		return data;
	}
	
	//新增方案
	this.addSolution = function(cropIds, productIds, name, groupId, slogan, relatedSolutionId, content, picUrl) {
		var url = this.baseUrl + "dlr_admin/solutions";
		var paramData = {name: name, crop_ids: cropIds, product_ids: productIds, group_id: groupId, 
				slogan: slogan, related_solution_ids: relatedSolutionId, content: content, pic_url: picUrl};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	/** 修改方案 */
	this.updateSolution = function(solutionId, cropIds, productIds, name, groupId, slogan, relatedSolutionId, content, picUrl) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId;
		var paramData = {name: name, crop_ids: cropIds, product_ids: productIds, group_id: groupId, 
				slogan: slogan, related_solution_ids: relatedSolutionId, content: content, pic_url: picUrl};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	/** 获取商家公开的方案列表 */
	this.getPublicSolutions = function() {
		var url = this.baseUrl + "dlr_admin/solutions";
		var paramData = {page_size : 9999};
		var data = ajax(url, "GET", false, paramData);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 获取商家所有方案列表，不分页 */
	this.getAllSolutions = function() {
		var url = this.baseUrl + "dlr_admin/solutions";
		var paramData = {query_all: true, page_size : 9999};
		var data = ajax(url, "GET", false, paramData);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 获取商家的方案列表，分页 */
	this.getSolutionsByPage = function(pageContainerClassName, callback, options) {
		var url = this.baseUrl + "dlr_admin/solutions";
		var params = {query_all: true};
		params.keyword = options && options.keyword;
		params.audit_status = options && options.auditStatus;
		params.listed_status = options && options.listedStatus;
		params.group_id = options && options.groupId;
		params.recommend = options && options.recommend;
		ajaxQueryByPage({url:url, pageSize:20, container: pageContainerClassName, 
			data:params, successCallback:callback});
	}
	
	/** 获取方案详细信息 */
	this.getSolution = function(solutionId) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 删除方案 */
	this.deleteSolution = function(solutionId) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 设置方案排序序号 */
	this.setSolutionRank = function(solutionId, rank) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId + "/rank/" + rank;
		var data = ajax(url, "PUT", false);
		
		return data;
	}
	
	/** 设置方案的上线状态 */
	this.setSolutionListedStatus = function(solutionId, status) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId + "/listed_status/" + status;
		var data = ajax(url, "PUT", false);
		
		return data;
	}
	
	/** 推荐方案 */
	this.recommendSolution = function(solutionId) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId + "/recommend";
		var data = ajax(url, "POST", false);
		
		return data;
	}
	
	/** 取消推荐方案 */
	this.cancelRecommendSolution = function(solutionId) {
		var url = this.baseUrl + "dlr_admin/solutions/" + solutionId + "/recommend";
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	//---------------------方案管理--结束-------------------
	
	//---------------------商家动态管理---------------------
	/** 新增商家动态 */
	this.addNews = function(title, summary, content, type, picUrl, isShowPic) {
		var url = this.baseUrl + "dlr_admin/news";
		var paramData = {title: title, summary: summary, content: content, type: type, 
				pic_url: picUrl, is_show_pic: isShowPic};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	/** 修改商家动态 */
	this.updateNews = function(newsId, title, summary, content, type, picUrl, isShowPic) {
		var url = this.baseUrl + "dlr_admin/news/" + newsId;
		var paramData = {title: title, summary: summary, content: content, type: type, 
				pic_url: picUrl, is_show_pic: isShowPic};
		var data = ajax(url, "PUT", false, paramData);
		
		return data;
	}
	
	/** 获取商家的新闻列表，分页 */
	this.getNewsByPage = function(pageContainerClassName, callback, keyword, type) {
		var url = this.baseUrl + "dlr_admin/news/search";
		var params = {query_all: true};
		params.keyword = keyword;
		params.type = type;
		ajaxQueryByPage({url:url, pageSize: 20, container: pageContainerClassName, 
			data:params, successCallback:callback});
	}
	
	/** 获取动态详细信息 */
	this.getNews = function(newsId) {
		var url = this.baseUrl + "dlr_admin/news/" + newsId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 删除商家动态 */
	this.deleteNews = function(newsId) {
		var url = this.baseUrl + "dlr_admin/news/" + newsId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 获取动态分类列表 */
	this.getNewsTypes = function() {
		var url = this.baseUrl + "dlr_admin/news/types";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	//---------------------商家动态管理--结束-------------------
	
	//---------------------评论管理------------------------
	/** 获取方案评论列表，分页 */
	this.getFeedbacksByPage = function(pageContainerClassName, callback, options) {
		var url = this.baseUrl + "dlr_admin/solutions/feedbacks/search";
		var params = {query_all: true};
		params.keyword = options && options.keyword;
		params.audit_status = options && options.auditStatus;
		params.solution_id = options && options.solutionId;
		ajaxQueryByPage({url:url, pageSize:20, container: pageContainerClassName, 
			data:params, successCallback:callback});
	}
	
	/** 处理方案评论的审核状态 */
	this.setFeedbackAuditStatus = function(feedbackId, status) {
		var url = this.baseUrl + "dlr_admin/solutions/feedbacks/" + feedbackId + "/audit_status/" + status;
		var data = ajax(url, "PUT", false);
		
		return data;
	}
	
	/** 商家回复用户评价 */
	this.replyFeedback = function(feedbackId, content) {
		var url = this.baseUrl + "dlr_admin/solutions/feedbacks/" + feedbackId + "/reply/";
		var params = {content : content};
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 删除用户评论 */
	this.deleteFeedback = function(feedbackId) {
		var url = this.baseUrl + "dlr_admin/solutions/feedbacks/" + feedbackId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	//---------------------评论管理--结束-------------------
}

var dlrAdmin = new DlrAdminJsSDK();
