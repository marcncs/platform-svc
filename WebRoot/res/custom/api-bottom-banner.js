/**
 * 该js文件主要是提供各种广告信息的查询功能
 */


function getAdBannerByPage(productId, keyword, bannerType, pageContainerClassName, callback) {
	var data = {product_id:productId, keyword:keyword, banner_type:bannerType}
	ajaxQueryByPage({url:"/mfr_admin/banners/list", pageSize:10, container: pageContainerClassName, 
		data:data, successCallback:callback});
}

function addPicBanner(picUrl, relatedProductIds, relatedUrl) {
	var resultData = null;
	var data = {banner_pic_url:picUrl, related_product_ids: relatedProductIds, related_url:relatedUrl};
	jQuery.ajax({url: "/mfr_admin/banners/pic", type: "POST", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
	
	return resultData;
}

function updatePicBanner(bannerId, picUrl, relatedProductIds, relatedUrl) {
	var resultData = null; 
	var data = {banner_pic_url:picUrl, related_product_ids: relatedProductIds, related_url:relatedUrl};
	jQuery.ajax({url: "/mfr_admin/banners/" + bannerId + "/pic", type: "PUT", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
	
	return resultData;
}

function addTextBanner(title, subTitle, relatedProductIds, relatedUrl) {
	var data = {title:title, sub_title:subTitle, related_product_ids: relatedProductIds, related_url:relatedUrl};
	var resultData = null;
	jQuery.ajax({url: "/mfr_admin/banners/text", type: "POST", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
	
	return resultData;
}

function updateTextBanner(bannerId, title, subTitle, relatedProductIds, relatedUrl) {
	var data = {title:title, sub_title:subTitle, related_product_ids: relatedProductIds, related_url:relatedUrl};
	var resultData = null;
	jQuery.ajax({url: "/mfr_admin/banners/" + bannerId + "/text", type: "PUT", dataType: "json",
		async: false, data: data, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
	
	return resultData;
}

function getAdBanner(bannerId) {
	var resultData = null;
	jQuery.ajax({url: "/mfr_admin/banners/" + bannerId, type: "GET", dataType: "json",
		async: false, data: null, success: function(result) {
			resultData = result.data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
	
	return resultData;
}

function getAdBannerByProductId(productId, callback) {
	jQuery.ajax({url: "/api/products/" + productId + "/banners", type: "GET", dataType: "json",
		async: true, data: null, success: function(result) {
			callback(result.data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw new Error(result.code, result.msg)
			}
		}
	});
}

function deleteAdBanner(bannerId) {
	jQuery.ajax({url: "/mfr_admin/banners/" + bannerId, type: "DELETE", dataType: "json",
		async: false, data: null, success: function(result) {
			return true;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
}