/**
 * 该js文件主要是提供各种产品信息的查询功能
 */
function getDomain() {
var str = window.location.href;
var first = str.indexOf('/')+1;
var second = str.indexOf('/',first)+1;
var third = str.indexOf('/',second)+1;
var forth = str.indexOf('/',third)+1;
return str.slice(0,forth);
}

function getContextPath() {
	var str = window.location.href;
	var first = str.indexOf('/')+1;
	var second = str.indexOf('/',first)+1;
	var third = str.indexOf('/',second)+1;
	var forth = str.indexOf('/',third)+1;
	return str.slice(third-1,forth);
}

/** 获取某一厂家的所有产品，包括没有通过审核和上架的产品 */
function getManufacturerProductsForAll(manufacturerId) {
	var data = {query_all: "true", page_size:9999};
	var products = null;
	$.ajax({url: "/api/manufacturer/" + manufacturerId + "/products", type: "GET", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				products = result.data.list;
			}
		}
	});
	
	return products;
}

/** 获取某一厂家的所有公开产品 */
function getManufacturerPublicProducts(manufacturerId) {
	var data = {page_size:9999};
	var products = null;
	$.ajax({url: "/api/manufacturer/" + manufacturerId + "/products", type: "GET", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				products = result.data.list;
			}
		}
	});
	
	return products;
}

/** 获取某一厂家的所有公开产品 */
function getMyPublicProductsByPage(pageContainerClassName, callback, pageSize) {
	var url = "/mfr_admin/product/public";
	ajaxQueryByPage({url:url, pageSize: pageSize || 12, container: pageContainerClassName, 
		data:null, successCallback:callback});
}

/** 获取产品详细信息 */
function getProductById(productId) {
	var product = null;
	$.ajax({url: "/api/product/" + productId, type: "GET", dataType: "json", async: false, data: null,
		success: function(result) {
			if (result.data) {
				product = result.data;
			}
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
	
	return product;
}

function editProductContent(productId, posterUrl, content) {
	var data = {poster_url: posterUrl, content:content};
	var product = null;
	$.ajax({url: getContextPath()+"qr/api/product/" + productId + "/content", type: "POST", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				product = result.data;
			}
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
	
	return product;
}

function addProductVideo(productId, title, videoUrl, videoImgUrl, fsize, shootTime) {
	var data = {product_id: productId, title: title, video_url: videoUrl, video_img_url:videoImgUrl, fsize: fsize, shoot_time: shootTime};
	var productVideo = null;
	$.ajax({url: "/mfr_admin/product/videos", type: "POST", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				product = result.data;
			}
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
	
	return productVideo;
}

function addProductCases(productId, title, content, picUrl, isShowPic, summary, province, city, district, publishTime) {
	var data = {product_id: productId, title: title, content: content, pic_url: picUrl, is_show_pic: isShowPic, summary: summary,
			province: province, city: city, district: district, publish_time: publishTime};
	var respData = null;
	$.ajax({url: "/mfr_admin/product/cases", type: "POST", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				respData = result.data;
			}
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
	
	return respData;
}

function editProductCases(productCaseId, title, content, picUrl, isShowPic, summary, province, city, district, publishTime) {
	var data = {title: title, content: content, pic_url: picUrl, is_show_pic: isShowPic, summary: summary,
			province: province, city: city, district: district, publish_time: publishTime};
	var respData = null;
	$.ajax({url: "/mfr_admin/product/cases/" + productCaseId, type: "PUT", dataType: "json", async: false, data: data,
		success: function(result) {
			if (result.data) {
				respData = result.data;
			}
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
	
	return respData;
}

function setProductLabel(productId, labelUrl) {
	var data = {label_url: labelUrl};
	$.ajax({url: "/api/product/" + productId + "/label", type: "PUT", dataType: "json", async: false, data: data,
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

function updateProductCoverPic(productId, coverPicUrl) {
	var data = {pic_url: coverPicUrl};
	$.ajax({url: getContextPath()+"qr/api/product/" + productId + "/pic", type: "POST", dataType: "json", async: false, data: data,
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