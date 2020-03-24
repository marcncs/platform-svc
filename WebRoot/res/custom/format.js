/**
 * 该js文件主要是提供各种格式化功能
 */


/**
 * 格式化地址，去掉省份的省字，并把自治区变为简称，例如：“内蒙古自治区”变为“内蒙古”
 * @param	province
 * @param	city
 * @returns	返回字符串，格式化后的地址
 */
function formatCanton(province, city) {
	if (!province) return null;
	
	province = toShortName(province);
	province = disposeProvincePostfix(province);
	
	return province + city;
	
	//长名字转为短名称
	function toShortName(province) {
		var provinces = ["内蒙古", "广西", "宁夏", "西藏", "新疆", "香港", "澳门"];
		for (var i=0; i<provinces.length; i++) {
			if (province.indexOf(provinces[i]) >= 0) {
				return provinces[i];
			}
		}
		
		return province;
	}

	//去除省字，例如：“河南省”变为“河南”
	function disposeProvincePostfix(province) {
		if (province && province.indexOf("省") >= 0) {
			province = province.substring(0, province.length-1);
		}
		
		return province;
	}

	//去除省字，例如：“上海市”变为“上海”
	function disposeCityPostfix(city) {
		if (city && city.indexOf("市") >= 0) {
			city = city.substring(0, city.length-1);
		}
		
		return city;
	}
}