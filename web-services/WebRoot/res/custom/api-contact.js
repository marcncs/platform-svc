/** 
 * 该js文件主要是提供企业联系电话的各种操作功能 
 */

/** 获取单个联系电话 */
function getContactById(contactId) {
	var contact = null;
	jQuery.ajax({url: "/mfr_admin/contacts/" + contactId, type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			contact = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
			} else {
				throw {status: XMLHttpRequest.status};
			}
		}
	});
	return contact;
}

/** 获取某个企业的所有联系电话 */
function getContactsByManufacturerId(successCallback) {
	var data = {};
	jQuery.ajax({url: "/mfr_admin/contacts/list", type: "GET", dataType: "json",
		async: true, data: data, success: function(result) {
			successCallback(result.data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
			} else {
				throw {status: XMLHttpRequest.status};
			}
		}
	});
}

/** 新增一个联系电话 */
function addContact(title, tel) {
	var resultData = null;
	var data = {title: title, tel: tel};
	jQuery.ajax({url: "/mfr_admin/contacts", type: "POST", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
			} else {
				throw {status: XMLHttpRequest.status};
			}
		}
	});
	return resultData;
}

/** 修改一个联系电话 */
function updateContact(contactId, title, tel) {
	var resultData = null;
	var data = {title: title, tel: tel};
	jQuery.ajax({url: "/mfr_admin/contacts/" + contactId, type: "PUT", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
			} else {
				throw {status: XMLHttpRequest.status};
			}
		}
	});
	return resultData;
}

/** 删除一个联系电话 */
function deleteContact(contactId) {
	jQuery.ajax({url: "/mfr_admin/contacts/" + contactId, type: "DELETE", dataType: "json",
		async: false, data: null, success: function(result) {
			return true;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw {status: XMLHttpRequest.status, code: result.code, msg: result.msg};
			} else {
				throw {status: XMLHttpRequest.status};
			}
		}
	});
}