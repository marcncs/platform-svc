/**
 *	商家后台管理平台的api访问js封装
 */

function ApiSolutionJsSDK() {
	
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
	
	/** 获取一个商家的N条动态 */
	this.getDealerNews = function(dealerId, type, limit) {
		var url = this.baseUrl + "api/dealer/news/search";
		var params = {dealer_id : dealerId, type : type, page_size : limit};
		var data = ajax(url, "GET", false, params);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 获取商家信息 */
	this.getDealer = function(dealerId) {
		var url = this.baseUrl + "api/dealers/" + dealerId;
		var data = ajax(url, "GET", false);

		return data;
	}
	
	/** 获取一个商家的分店列表 */
	this.getDealerStores = function(dealerId, limit) {
		var pageSize = limit || 99;
		var url = this.baseUrl + "api/dealers/" + dealerId + "/stores";
		var params = {page_size : pageSize};
		var data = ajax(url, "GET", false, params);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 获取一个商家的最新方案 */
	this.getLatestSolutions = function(dealerId, limit) {
		var pageSize = limit || 20;
		var url = this.baseUrl + "api/dealers/" + dealerId + "/solutions/latest";
		var params = {page_size : pageSize};
		var data = ajax(url, "GET", false, params);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 获取商家分组 */
	this.getDealerSolutionGroups = function(dealerId) {
		var url = this.baseUrl + "api/dealers/" + dealerId + "/groups";
		var data = ajax(url, "GET", false);

		return data;
	}
	
	/** 获取商家新闻分类 */
	this.getDealerNewsTypes = function() {
		var url = this.baseUrl + "api/dealer/news/types";
		var data = ajax(url, "GET", false);

		return data;
	}
	
	/** 获取一个方案评价 */
	this.getSolutionFeedbacks = function(solutionId) {
		var url = this.baseUrl + "api/solutions/" + solutionId + "/feedbacks";
		var params = {queryAll : false};
		var data = ajax(url, "GET", false, params);
		if (data) {
			return data.list;
		} else {
			return null;
		}
	}
	
	/** 对方案进行评价 */
	this.commentSolution = function(solutionId, score, content, picUrls) {
		var url = this.baseUrl + "api/solutions/" + solutionId + "/feedbacks";
		var params = {solution_id : solutionId, score : score, content : content, pic_urls : picUrls};
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 获取热门方案列表 */
	this.getRecommendSolutions = function(dealerId) {
		var url = this.baseUrl + "api/dealers/" + dealerId + "/solutions/recommends";
		var data = ajax(url, "GET", false);

		return data;
	}
}

var apiSolution = new ApiSolutionJsSDK();
