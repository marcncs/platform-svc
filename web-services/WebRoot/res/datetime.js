Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute<br>
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) 
		format = format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(format))
			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};

/**
 * 把时间格式化为近人类语言，
 * 例如：当前天 16:48格式化为："今天 16时48分"
 * 当年："11月29日 16时48分"
 * 2012年："2012年11月29日 16时48分";
 * @param dateTime
 * @return
 */
Date.prototype.toHumanLanguageFromDate = function(){
    var formateDate = "";
	var year = this.getFullYear();
	var month = this.getMonth()+1;
	var date = this.getDate();
	var hour = this.getHours();
	var minutes = this.getMinutes();
    
	if (this.isSameDay(new Date())){
        formateDate += "今天";
	} else if (this.isSameDay(new Date(new Date().getTime() - 1000*60*60*24))) {
        formateDate += "昨天";
	} else if (this.isSameDay(new Date(new Date().getTime() - 1000*60*60*24*2))) {
        formateDate += "前天";
    } else {
        var currentYear = new Date().getFullYear();
		if (currentYear != year) {
            formateDate += (year + "年");
		}
        formateDate += (month + "月" + date + "日");
    }
    formateDate += (" " + hour + "时" + minutes + "分");
    return formateDate;
}

Date.prototype.isSameDay = function(otherDate){
	if(typeof(otherDate) == "object" && otherDate instanceof Date){
		if (this.getFullYear() == otherDate.getFullYear() && this.getMonth() == otherDate.getMonth() && this.getDate() == otherDate.getDate()){
			return true;
		} else {
			return false;
		}
	}
	alert("illegal date type");
}

function getWeek(date) {
	var day = new Date(Date.parse(date));   //需要正则转换的则 此处为 ： var day = new Date(Date.parse(date.replace(/-/g, '/')));  
	 var today = new Array('周一','周二','周三','周四','周五','周六','周日');  
	 var week = today[day.getDay()];
}

//格式化日期：yyyy-MM-dd  
function formatDate(date) {
	return date.format("yyyy-MM-dd");
}

//格式化日期：yyyy-MM-dd HH:mm:ss
function formatDateTime(date) {
	return date.format("yyyy-MM-dd hh:mm:ss");  
}

function formatDateMD(date) {
	var mymonth = date.getMonth()+1;  
    var myweekday = date.getDate();   
      
    return (mymonth + "月" + myweekday + "日");
}

function formatDateMD2(date) {
	var mymonth = date.getMonth()+1;  
    var myday = date.getDate();
    
    if (mymonth <= 9) {
    	mymonth = "0" + mymonth;
    }
    if (myday <= 9) {
    	myday = "0" + myday;
    }
    
    return mymonth + "/" + myday;
}

//获得某月的天数  
function getMonthDays(year, month){  
    var monthStartDate = new Date(year, month, 1);   
    var monthEndDate = new Date(month, month + 1, 1);   
    var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
    return days;
}

//获得本周的开始日期  
function getWeekStartDate(date) {
	var firstDateOfWeek = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	if (date.getDay() == 0) {
		//周日,需要特殊处理
		firstDateOfWeek.setDate(date.getDate() - 6);
	} else {
		firstDateOfWeek.setDate(date.getDate() - date.getDay() + 1);
	}
    return firstDateOfWeek;
}   
 
//获得本周的结束日期  
function getWeekEndDate(date) {
	var lastDateOfWeek = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	if (date.getDay() == 0) {
		//周日,需要特殊处理
		//date.setDate(date.getDate());
	} else {
		lastDateOfWeek.setDate(date.getDate() + 7 - date.getDay());
	}
    return lastDateOfWeek 
}   
 
//获得本月的开始日期  
function getMonthStartDate(date){  
    var monthStartDate = new Date(date.getFullYear(), date.getMonth(), 1);   
    return monthStartDate;  
}  
 
//获得本月的结束日期  
function getMonthEndDate(date){
    var monthEndDate = new Date(date.getFullYear(), date.getMonth(), getMonthDays(date.getFullYear, date.getMonth()));   
    return monthEndDate;
}

function getNextMonth(year, month) {
	var date = new Date(year, month, 1);
	return date;
}

function getPreMonth(year, month) {
	var date = new Date(year, month-2, 1);
	return date;
}

function getYearWeek(year, month, day) { 
    var d1 = new Date(year, month-1, day), d2 = new Date(year, 0, 1), 
    d = Math.round((d1 - d2) / 86400000); 
    return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7); 
}

function getYearWeek(date) { 
    var d1 = date;
    d2 = new Date(date.getFullYear(), 0, 1);
    d = Math.round((d1 - d2) / 86400000);
    return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7); 
}