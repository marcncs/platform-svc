/**
 *	商家后台管理平台的api访问js封装
 */

function WzAdminJsSDK() {
	
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
	
	/** 分页获取某一厂家的所有公开产品 */
	this.getMyPublicProductsByPage = function(pageContainerClassName, callback, pageSize) {
		var url = this.baseUrl + "mfr_admin/product/public";
		ajaxQueryByPage({url:url, pageSize: pageSize || 20, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	/** 分页获取某一厂家的所有公开产品的经验 */
	this.getMyProductCaseByPage = function(pageContainerClassName, callback, pageSize) {
		var url = this.baseUrl + "mfr_admin/product/cases/me";
		ajaxQueryByPage({url:url, pageSize:pageSize || 20, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	/** 分页获取某一厂家的所有新闻 */
	this.getMyNewsByPage = function(pageContainerClassName, callback, pageSize) {
		var url = this.baseUrl + "mfr_admin/news/me";
		ajaxQueryByPage({url:url, pageSize:pageSize || 20, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	/** 群发消息， chooseMaterialIds格式：[{"id": 14, "type": "product_content"},{"id": 65, "type": "product_case"}]*/
	this.massMessage = function(chooseMaterialIds) {
		var url = this.baseUrl + "mfr_admin/weixin/message/mass";
		var param = {"material_ids" : chooseMaterialIds};
		var data = ajax(url, "POST", false, param);
		
		return data;
	}
}

var wzAdmin = new WzAdminJsSDK();
