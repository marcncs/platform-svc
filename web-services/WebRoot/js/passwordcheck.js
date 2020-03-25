function passwordLevel(password, username) {
	if(password == username) {
		return -3;
	}
    if(password.indexOf(" ") != -1) {
      return -1;
    }
    if(password.length < 8) { 
      return -2;
    }
    var Modes = 0;
    for (i = 0; i < password.length; i++) {
        Modes |= CharMode(password.charCodeAt(i));
    }
    return bitTotal(Modes);
 
    //CharMode函数
    function CharMode(iN) {
        if (iN >= 48 && iN <= 57)//数字
            return 1;
        if (iN >= 65 && iN <= 90) //大写字母
            return 2;
        if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90)) //大小写
            return 4;
        else
            return 8; //特殊字符
    }
 
    //bitTotal函数
    function bitTotal(num) {
        modes = 0;
        for (i = 0; i < 4; i++) {
            if (num & 1) modes++;
            num >>>= 1;
        }
        return modes;
    }
}

function checkPwd(pwd, username) {
	var result= passwordLevel(pwd, username);
	if(result < 3) {
		alert("密码不符合要求,密码规则如下：\n1.至少8位\n2.大写英文字母，小写英文字母，数字和特殊字符，4种类型中至少包含3种\n3.不能与用户名相同\n4.不能与最后10次的密码相同");
		return false;
	}
	return true;
}
