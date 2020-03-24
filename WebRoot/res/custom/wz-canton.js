/**
 *	全国行政区划api访问js封装，以及省市区显示在select元素中的代码
 */

function WzCantonSDK() {
	
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
					throw {status: XMLHttpRequest.status, code: result.code, message: result.msg};
				} else {
					console.log(XMLHttpRequest + "; textStatus: " + textStatus + "; errorThrown: " + errorThrown);
				}
			}
		});
		
		return data;
	}
	
	/** 获取全国省份 */
	this.getProvince = function() {
		var url = this.baseUrl + "api/canton/provinces";
		var data = ajax(url, "GET", false);
		
		return data;
	};
	
	/** 根据省份获取城市 */
	this.getCities = function(province) {
		var url = this.baseUrl + "api/canton/cities";
		var paramData = {"province": province};
		var data = ajax(url, "GET", false, paramData);
		
		return data;
	}
	
	/** 获取区县 */
	this.getDistricts = function(province, city) {
		var url = this.baseUrl + "api/canton/districts";
		var paramData = {province: province, city: city};
		var data = ajax(url, "GET", false, paramData);
		
		return data;
	}
	
}

var wzCanton = new WzCantonSDK();

//显示省份选择框
//selElement select元素对对象
//selected 选中的元素option的value值
function showProvincesSelect(selElement, selected) {
	selElement.html("<option>选择省份</option>");
	//获取省份
	var provinces = wzCanton.getProvince();
	if (provinces) {
		for (var i = 0; i < provinces.length; i++) {
			var province = provinces[i];
			selElement.append("<option value='" + province.province + "'>" + province.province + "</option>");
		}
		
		//自动选中经验所在省份
		if (selected) {
			selElement.find("option[value='" + selected + "']").attr("selected", true);
		}
	}
}

//显示城市选择框
//province 省份
//selElement select元素对对象
//selected 选中的元素option的value值
function showCitiesSelect(province, selElement, selected) {
	selElement.html('<option selected>选择城市</option>');
	var cities = wzCanton.getCities(province);
	if (cities) {
		for (var i = 0; i < cities.length; i++) {
			selElement.append("<option value='" + cities[i].city + "'>" + cities[i].city + "</option>");
		}
		//自动选中经验所在城市
		if (selected) {
			selElement.find("option[value='" + selected + "']").attr("selected", true);
		}
	}
}

//选择区县选择框
//province 省份
//city	城市
//selElement select元素对对象
//selected 选中的元素option的value值
function showDistrictsSelect(province, city, selElement, selected) {
	selElement.html('<option selected>选择区县</option>');
	var districts = wzCanton.getDistricts(province, city);
	if (districts) {
		for (var i = 0; i < districts.length; i++) {
			selElement.append("<option value='" + districts[i].district + "'>" + districts[i].district + "</option>");
		}
		
		//自动选中经验所在区县
		if (selected) {
			selElement.find("option[value='" + selected + "']").attr("selected",true);
		}
	}
}

jQuery().ready(function() {
	
	//显示省份的select选择框
	showProvincesSelect($('#sel_provinces'));
	
	//绑定省份select选择框的change事件
	$('#sel_provinces').off('change');
	$('#sel_provinces').on('change', function(){
		var province = $("#sel_provinces").val();
		//显示城市的select选择框
		showCitiesSelect(province, $('#sel_cities'));
		
		$('#sel_districts').html("<option>选择区县</option>");
	});
	
	//绑定城市select选择框的change事件
	$('#sel_cities').off('change');
	$('#sel_cities').on('change', function() {
		var province = $("#sel_provinces").val();
		var city = $("#sel_cities").val();
		//显示区县的select选择框
		showDistrictsSelect(province, city, $('#sel_districts'));
	});
	
}); //jquery ready end....
