/**
 * 该js文件主要是提供厂家各种操作功能
 */

function getManufacturerByPage(pageContainerClassName, callback, keyword, auditStatus, level, startTime, endTime, certifyStatus) {
	var data = {keyword:keyword, audit_status:auditStatus, certify_status:certifyStatus, level:level, start_time:startTime, end_time:endTime}
	ajaxQueryByPage({url:"/ops_admin/manufacturer/list", pageSize:10, container: pageContainerClassName, 
		data:data, successCallback:callback});
}

function getManufacturerById(manufacturerId) {
	var manufacturer = null;
	jQuery.ajax({url: "/api/manufacturers/" + manufacturerId, type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			manufacturer = result.data;
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
	
	return manufacturer;
}

/** 获取所有审核通过的厂家 */
function getManufacturerAll() {
	var manufacturers = null;
	jQuery.ajax({url: "/api/manufacturers", type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			manufacturers = result.data;
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
	
	return manufacturers;
}

function setManufacturerLevel(manufacturerId, levelStatus) {
	var data = {level_status:levelStatus};
	jQuery.ajax({url: "/ops_admin/manufacturer/" + manufacturerId + "/settings/level", type: "POST", dataType: "json",
		async: true, data: data, success: function(result) {
			//console.log(result);
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
}

function setManufacturerExpiredTime(manufacturerId, serviceExpiredTime) {
	var data = {service_expired_time:serviceExpiredTime};
	jQuery.ajax({url: "/ops_admin/manufacturer/" + manufacturerId + "/settings/expired_time", type: "POST", dataType: "json",
		async: true, data: data, success: function(result) {
			//console.log(result);
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
}

function getAccountByManufacturerId(manufacturerId) {
	var account = null;
	jQuery.ajax({url: "/api/manufacturer/" + manufacturerId + "/account", type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			account = result.data;
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
	return account;
}

function manufacturerCertify(manufacturerId, certifyStatus) {
	var data = {certify_status: certifyStatus};
	$.ajax({url: "/api/manufacturers/" + manufacturerId + "/certification", type: "PUT", dataType: "json",
		async: false, data: data, success: function(d) {
			console.log(d);
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
}