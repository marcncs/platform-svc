/**
 *	病虫害百科api访问js封装
 */

//新增统计记录

function WzBaikeJsSDK() {
	
	//url默认为本地调试
	this.baseUrl = "http://192.168.3.101:8082/";
//	this.baseUrl = "http://192.168.3.22:8080/";
	var currentLocation = location.href;
	//如果当前页面域名n369.com，则url为baike.n369.com
	if (currentLocation.indexOf("n369.com/") >= 0) {
		this.baseUrl = "http://baike.n369.com/";
	}
	
	function ajax(url, type, async, params) {
		var data = null;
		jQuery.ajax({url: url, type: type, dataType: "json", async: async, data: params, 
			success: function(result) {
				data = result.data;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				var result = jQuery.parseJSON(XMLHttpRequest.responseText);
				if (result) {
					throw {number: result.code, message: result.msg};
				}
			}
		});
		
		return data;
	}
	
	/** 获取作物所有的分类，包括二级分类 */
	this.getCropGroups = function() {
		var url = this.baseUrl + "api/baike/crops/groups";
		var data = ajax(url, "GET", false);
		
		return data;
	};
	
	/** 根据作物的二级分类查询该分类下的所有作物 */
	this.getCrops = function(group2Id) {
		var requestParams = {group2_id : group2Id};
		var url = this.baseUrl + "api/baike/crops";
		var data = ajax(url, "GET", false, requestParams);
		
		return data;
	}
	
	/** 获取某一个作物信息 */
	this.getCrop = function(cropId) {
		var url = this.baseUrl + "api/baike/crops/by_ids";
		var requestParams = {crop_ids : cropId};
		var data = ajax(url, "GET", false, requestParams);
		if (data) {
			return data[0];
		}
		return null;
	}
	
	/** 获取所有作物 */
	this.getCropsAll = function() {
		var url = this.baseUrl + "api/baike/crops";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 获取常用作物 */
	this.getTopCrops = function() {
		var url = this.baseUrl + "api/baike/crops/top";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 获取病虫害的类型列表 */
	this.getPestTypes = function() {
		var url = this.baseUrl + "api/baike/pests/types";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 根据编号查询病虫害信息 */
	this.getPest = function(pestId) {
		var url = this.baseUrl + "api/baike/pests/" + pestId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 根据病虫害名字查询病虫害列表 */
	this.getPests = function(pestName) {
		var url = this.baseUrl + "api/baike/pests/name/" + pestName;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 搜索病虫害 */
	this.searchPests = function(pageContainerClassName, callback, keyword, cropId, pestType) {
		var url = this.baseUrl + "api/baike/pests";
		
		var data = {keyword:keyword, crop_id:cropId, pest_type:pestType}
		ajaxQueryByPage({url:url, pageSize:10, container: pageContainerClassName, 
			data:data, successCallback:callback});
	}
	
	/** 新增病虫害 */
	this.addPest = function(options) {
		var params = {crop_id: options.cropId, name: options.name, type: options.type, 
				pic_url: options.url, content: options.content, rank: options.rank};
		
		var url = this.baseUrl + "api/baike/pests";
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 修改病虫害 */
	this.updatePest = function(options){
		var params = {crop_id: options.cropId, name: options.name, type: options.type, 
				pic_url: options.url, content: options.content, rank: options.rank};
		
		var url = this.baseUrl + "api/baike/pests/" + options.pestId;
		var data = ajax(url, "PUT", false, params);
		
		return data;
	}
	
	this.updatePestCoverPic = function(pestId, coverPicUrl) {
		var params = {pic_url: coverPicUrl};
		
		var url = this.baseUrl + "api/baike/pests/" + pestId + "/pics";
		var data = ajax(url, "PUT", false, params);
		
		return data;
	}
	
	/** 删除病虫害 */
	this.deletePest = function(pestId) {
		var url = this.baseUrl + "api/baike/pests/" + pestId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 根据id获取病虫害图片 */
	this.getPestPic = function(pestPicId) {
		var url = this.baseUrl + "api/baike/pests/pics/" + pestPicId;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 获取病虫害的所有图片 */
	this.getPestPics = function(pestId) {
		var url = this.baseUrl + "api/baike/pests/pics";
		var params = {pest_id: pestId};
		var data = ajax(url, "GET", false, params);
		
		return data;
	}
	
	/** 为病虫害添加图片 */
	this.addPestPic = function(pestId, title, url, rank) {
		var reqUrl = this.baseUrl + "api/baike/pests/pics";
		var params = {pest_id: pestId, pic_title: title, pic_url: url, rank:rank};
		var data = ajax(reqUrl, "POST", false, params);
		
		return data;
	}
	
	/** 修改病虫害图片 */
	this.updatePestPic = function(pestPicId, title, rank) {
		var reqUrl = this.baseUrl + "api/baike/pests/pics/" + pestPicId;
		var params = {pic_title: title, rank:rank};
		var data = ajax(reqUrl, "PUT", false, params);
		
		return data;
	}
	
	/** 删除病虫害图片 */
	this.deletePestPic = function(pestPicId) {
		var url = this.baseUrl + "api/baike/pests/pics/" + pestPicId;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
}

var wzBaike = new WzBaikeJsSDK();
