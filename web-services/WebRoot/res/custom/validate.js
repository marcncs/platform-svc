//是否纯数字
function isNumber(str) {
    var regex = /^[0-9]*$/;
    return regex.test(str);
}

//验证手机号  
function isMobile(str) {
	if (str == "") {
		return false;
	} else {
	    var regex = /^1[34578]\d{9}$/;
	    return regex.test(str);
	}
}

//验证固定电话号码,只能是数字和-()*这4种符号
function isPhone(str) {
	if (str == "") {
		return false;
	} else {
	    var regex = /^[0-9-()*]{5,30}$/;
	    return regex.test(str);
	}
}

// 验证email
function isEmail(str) {
	if (str == "") {
		return false;
	} else {
	    var regex = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
	    return regex.test(str);
	}
}