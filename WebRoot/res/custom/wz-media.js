/**
 *	多媒体素材库api访问js封装
 */


function getContextPath() {
	var str = window.location.href;
	console.log(str);
	var first = str.indexOf('/')+1;
	var second = str.indexOf('/',first)+1;
	var third = str.indexOf('/',second)+1;
	var forth = str.indexOf('/',third)+1;
	console.log(str.slice(third,forth));
	return str.slice(third-1,forth);
}

function WzMediaJsSDK() {
	
	//url默认为本地调试
	this.baseUrl = "";
	
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
	
	/** 获取企业所有图片分组 */
	this.getImageGroups = function() {
		var url =  getContextPath()+"qr/mfr_admin/media/images/groups";
		
		var data = ajax(url, "GET", false);
		
		return data;
	};
	
	/** 根据分组id获取分组信息 */
	this.getImageGroup = function(groupId) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/groups/" + groupId;
		
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 新增分组 */
	this.addImageGroup = function(name) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/groups";
		
		var paramData = {"name" : name};
		var data = ajax(url, "POST", false, paramData);
		
		return data;
	}
	
	/** 修改分组 */
	this.updateImageGroup = function(groupId, name) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/groups/" + groupId;
		
		var data = ajax(url, "PUT", false, {"name" : name});
		
		return data;
	}
	
	/** 删除分组 */
	this.deleteImageGroup = function(groupId) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/groups/" + groupId;
		
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 获取图片 */
	this.getImage = function(id) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/" + id;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 获取图片列表 */
	this.getImagesByPage = function(pageContainerClassName, callback, groupId) {
		var url =  getContextPath()+"qr/mfr_admin/media/images/query?random="+Math.random();
		console.log(url);
		var data = {group_id:groupId}
		ajaxQueryByPage({url:url, pageSize:12, container: pageContainerClassName, 
			data:data, successCallback:callback});
	}
	
	/** 新增图片 */
	this.addImage = function(title, url, fsize, groupId) {
		var params = {title: title, url: url, fsize: fsize, group_id: groupId};
		
		var url =  getContextPath()+"qr/mfr_admin/media/images";
		var data = ajax(url, "POST", false, params);
		
		return data;
	}
	
	/** 修改图片 */
	this.updateImage = function(id, title){
		var url =  getContextPath()+"qr/mfr_admin/media/images/" + id;
		var data = ajax(url, "PUT", false, {title : title});
		
		return data;
	}
	
	/** 移动图片 */
	this.moveImages = function(ids, newGroupId) {
		var params = {ids : ids, new_group_id : newGroupId};
		
		var url =  getContextPath()+"qr/mfr_admin/media/images";
		var data = ajax(url, "PUT", false, params);
		
		return data;
	}
	
	/** 删除图片 */
	this.deleteImages = function(ids) {
		var url =  getContextPath()+"qr/mfr_admin/media/images?ids=" + ids;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 根据id获取视频 */
	this.getVideo = function(id) {
		var url =  "mfr_admin/media/videos/" + id;
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
	/** 获取视频列表 */
	this.getVideosByPage = function(pageContainerClassName, callback, pageSize) {
		var url =  "mfr_admin/media/videos/query";
		
		ajaxQueryByPage({url:url, pageSize: pageSize || 12, container: pageContainerClassName, 
			data:null, successCallback:callback});
	}
	
	/** 添加视频 */
	this.addVideo = function(title, url, digest, fsize) {
		var reqUrl =  "mfr_admin/media/videos";
		var params = {title: title, url: url, digest:digest, fsize:fsize};
		var data = ajax(reqUrl, "POST", false, params);
		
		return data;
	}
	
	/** 修改视频 */
	this.updateVideo = function(id, title, digest) {
		var reqUrl =  "mfr_admin/media/videos/" + id;
		var params = {title: title, digest:digest};
		var data = ajax(reqUrl, "PUT", false, params);
		
		return data;
	}
	
	/** 删除视频 */
	this.deleteVideo = function(id) {
		var url =  "mfr_admin/media/videos/" + id;
		var data = ajax(url, "DELETE", false);
		
		return data;
	}
	
	/** 获取视频数量 */
	this.getVideoNumber = function() {
		var url =  "mfr_admin/media/videos/number";
		var data = ajax(url, "GET", false);
		
		return data;
	}
	
}

var wzMedia = new WzMediaJsSDK();
