/**
 * 该js文件主要是提供功能的各种操作
 */

//所有收费的功能
function getAllFunctions() {
	var functions = null;
	jQuery.ajax({url: "/ops_admin/functions/all", type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			functions = result.data;
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
	return functions;
}

//厂家所拥有的所有功能
function getManufacturerFunctions(manufacturerId) {
	var functions = null;
	jQuery.ajax({url: "/ops_admin/manufacturer/" + manufacturerId + "/functions", type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			functions = result.data;
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
	return functions;
}

//判断厂家是否能够使用某一个功能
function canUseFunction(manufacturerId, functionId) {
	var isCan = null;
	jQuery.ajax({url: "/api/manufacturer/" + manufacturerId + "/functions/" + functionId, type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			//接口直接返回true和false
			isCan = result.data;
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
	return isCan;
}

//设置厂家的功能
function setManufacturerFunction(manufacturerId, functionId, usageStatus) {
	var data = {function_id: functionId, usage_status:usageStatus}
	jQuery.ajax({url: "/ops_admin/manufacturer/" + manufacturerId + "/functions/settings", type: "POST", dataType: "json", async: false, data: data,
		success: function(result) {
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