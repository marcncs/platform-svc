/**
 * 资源阅读量的统计和显示功能
 * 
 * 资源包括产品图文，视频、经验、评价和新闻
 * 
 * @param $ 
 */

function viewResource(manufacturerId, resourceId, resourceType) {
	var data = {manufacturer_id:manufacturerId, resource_id:resourceId, resource_type:resourceType};
	jQuery.ajax({
		url: "/api/stat/resource/views",
		type: "POST",
		data: data,
		dataType: "json",
		async: true,
		success:function(result) {
		}
	});
}

function getViews(manufacturerId, resourceIds, resourceType, successCallback) {
	var data = {manufacturer_id:manufacturerId, resource_ids:resourceIds, resource_type:resourceType};
	jQuery.ajax({
		url: "/api/stat/resource/views",
		type: "GET",
		data: data,
		dataType: "json",
		async: true,
		success:successCallback
	});
}