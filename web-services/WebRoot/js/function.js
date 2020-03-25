function setCookie(sName, sValue)
{
	var dt=new Date();
	var y=dt.getYear();
	y=y<100?(2000+y):y;
	y++;
	dt=new Date(y,dt.getMonth(),dt.getDate());
	document.cookie = sName + "=" + escape(sValue) +
					";expires="+dt.toGMTString()+";";
}
function getCookie(sName)
{
	var aCookie = document.cookie.split("; ");
	for (var i=0; i < aCookie.length; i++)
	{
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0]) 
			return unescape(aCrumb[1]);
	}

	return null;
}

/**
 * sName 键名
 * sValue 键值
 * expireHours 小时(数字)
 */
function setCookieTime(sName, sValue, expireHours)
{
	var dt=new Date();
	dt.setTime(dt.getTime()+expireHours*3600*1000);
	document.cookie = sName + "=" + escape(sValue) +
					";expires="+dt.toGMTString()+";";
}

function delCookies(sName)
{
   var dt = new Date();
   dt.setTime(dt.getTime() - 1);
   var cval=getCookie(sName);
   if(cval!=null) document.cookie=sName +"="+null+";expire*="+dt.toGMTString();
}


function strFilter(str)
{
  var Letters = "'";
  for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) != -1)
      {
        alert(" 输入内容请不要包含字符“ ‘  ”！");
		return false;
      }
    }
    return true;
}
function account(str,strName)   //包含字母或数字

{
	var Letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    var Letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	if(str.length<2 || str.length>32)
	{
        alert(strName+" 的长度应该在2位至32位之间！");
		return false;
	}
	if(Letter.indexOf(str.charAt(0))==-1)
	{
		alert(strName+" 必须以字母开头！");
		return false;
	}
    for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(strName+" 只能包括字母、数字或“_”！");
		return false;
      }
    }
    return true;
}
function email(str,strName)   //包含字母或数字

{
	var Letters = ".@";
	if(str.length<5)
	{
        alert("请输入正确的"+strName);
		return false;
	}
    for (i=0; i < Letters.length; i++)
    {
      var CheckChar = Letters.charAt(i);
      if (str.indexOf(CheckChar) == -1)
      {
        alert("请输入正确的"+strName);
		return false;
      }
    }
    return true;
}
function id(str,strName)   //包含字母或数字

{
	var Letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-[]";
	if(str.length<1 || str.length>32)
	{
        alert(strName+" 的长度应该在1位至32位之间！");
		return false;
	}
    for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(strName+" 只能包括字母、数字或“_-[]”！");
		return false;
      }
    }
    return true;
}
function maillist(str,strName)   //包含字母或数字

{
	var Letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
	if(str.length<1 || str.length>32)
	{
        alert(strName+" 的长度应该在1位至32位之间！");
		return false;
	}
    for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(strName+" 只能包括字母、数字或“_”！");
		return false;
      }
    }
    return true;
}
function isInt(str)   //包含字母或数字

{
	var Letters = "0123456789";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(" 请输入数字！");
		return false;
      }
    }
    return true;
}
function isMoney(str)   //是否金额
{
	var Letters = "0123456789.";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(" 请输入数值！");
		return false;
      }
    }
    return true;
}
function isMoney(str,name)   //是否金额
{
	var Letters = "0123456789.";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
        alert(" 请在“"+name+"”中输入数值！");
		return false;
      }
    }
    return true;
}
function isNum(str)   //包含数字
{
	var Letters = "0123456789";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
		return false;
      }
    }
    return true;
}

function checkpsd(str)   //密码为4到8位
{
  if(str.length <4 || str.length>8)
  {
    return false;
  }
  return true;
}

function isNumNoAlert(str)   //包含数字
{
	var Letters = "0123456789";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
		return false;
      }
    }
    return true;
}
function isNum1(str,name)   //包含数字
{
	var Letters = "0123456789";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
		alert(" 请在“"+name+"”中输入数值！");
		return false;
      }
    }
    return true;
}
//判断格式：2004-03-03
function isDateLen10(str)
{
  var i=true;
  if(str.length!=10) i=false;
  if(!isNumNoAlert(str.substring(0,4))||str.substring(0,4)>2050||str.substring(0,4)<1950)
     i=false;
  if(str.substring(4,5)!="-") i=false;
  if(!isNumNoAlert(str.substring(5,7))||str.substring(5,7)>12||str.substring(5,7)<=0) i=false;
  if(str.substring(7,8)!="-") i=false;
  if(!isNumNoAlert(str.substring(8,10))||str.substring(8,10)>31||str.substring(8,10)<=0) i=false;
  return i;
}
//判断格式：20040303
function isDate(str)
{
  var i=true;
  if(str.length!=8) i=false;
  if(!isNum(str.substring(0,4))||str.substring(0,4)>2050||str.substring(0,4)<1950)
     i=false;
  //if(str.substring(4,5)!="-") i=false;
  if(!isNum(str.substring(4,6))||str.substring(4,6)>12||str.substring(4,6)<=0) i=false;
  //if(str.substring(7,8)!="-") i=false;
  if(!isNum(str.substring(6,8))||str.substring(6,8)>31||str.substring(6,8)<=0) i=false;
  return i;
}
function getStandardDate(str)
{
	return str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
}
function getDate(str)
{
	return str.substring(0,4)+str.substring(5,7)+str.substring(8,10);
}
function getHour(str)
{
  var i=str.substring(str.indexOf(" ")+1,str.indexOf(":"));
  if(i.length==1)i="0"+i;
  return i;
}
function getMinute(str)
{
	var i=str.substring(str.indexOf(":")+1,str.length);
	if(i.length==1) i="0"+i;
    return i;
}

function password(str,strName)
{
	if(str.length<2 || str.length>32)
	{
        alert(strName+" 的长度应该在2位至32位之间！");
		return false;
	}
    return true;
}

function userName(str,strName)
{
	if(str.length<1)
	{
        alert("请输入 "+strName);
		return false;
	}
	if(str.length>128)
	{
        alert(strName+" 的长度最大为128个字符或64个汉字！");
		return false;
	}
    return true;
}
function isNumber(str)   //包含数字
{
	var Letters = "0123456789";
	for (i=0; i < str.length; i++)
    {
      var CheckChar = str.charAt(i);
      if (Letters.indexOf(CheckChar) == -1)
      {
		return false;
      }
    }
    return true;
}
function isDatetime(str)
{
    var temp1;
    var temp2;
    var strhour;
    var strminute;
    var strsecond;
    var colon1;
    var colon2;
    var i;

    if(str.indexOf(" ")==-1) return false;

    temp1=str.substring(0,str.indexOf(" "));
    temp2=str.substring(str.indexOf(" ")+1,str.length);
    alert(temp1);
    alert(temp2);
    if(temp1.length!=10) return false;

    if(!isNumber(str.substring(0,4))||temp1.substring(0,4)>2050||temp1.substring(0,4)<1950)
        return false;

    if(temp1.substring(4,5)!="-") return false;

    if(!isNumber(temp1.substring(5,7))||temp1.substring(5,7)>12) return false;

    if(temp1.substring(7,8)!="-") return false;

    if(!isNumber(temp1.substring(8,10))||temp1.substring(8,10)>31) return false;

    strhour=temp2.substring(0,2);

    colon1=temp2.substring(2,3);

    strminute=temp2.substring(3,5);

    colon2=temp2.substring(5,6);

    strsecond=temp2.substring(6,8);

    if(colon1!=":")  return false;

    if(colon2!=":")  return false;

    if(!isNumber(strhour))  return false;

    if(!isNumber(strminute)) return false;

    if(!isNumber(strsecond)) return false;

    return true;
}

//打开窗口
function popWin(url,x,y) {
	var top = (window.screen.height -y)/2;
	var left = (window.screen.width -x)/2;
    var newwin=window.open(url,"PopWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,top="+top+",left="+left+",width="+x+",height="+y);
    newwin.focus();
    return false;
}
//打开固定大小位置窗口
function popWin2(url) {
	var x = 500;
	var y = 250;
	var top = (window.screen.height -y)/2;
	var left = (window.screen.width -x)/2;
  var
newwin=window.open(url,"PopWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,top="+top+",left="+left+",width="+x+",height="+y);
  newwin.focus();
  return false;
}
function popWin3(url,x,y,top,left) {
  var
newwin=window.open(url,name,"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,top="+top+",left="+left+",width="+x+",height="+y);
  newwin.focus();
  return false;
}
function popWin4(url,x,y,name) {
	var top = (window.screen.height -y)/2;
	var left = (window.screen.width -x)/2;
  var
newwin=window.open(url,name,"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,top="+top+",left="+left+" ,width="+x+",height="+y);
  newwin.focus();
  return false;
}

	function openwindow(url,name,iWidth,iHeight)
	{
	    var url;                              
	    var name;                             
	    var iWidth;                         
	    var iHeight;                       
	     
	    var iTop = (window.screen.availHeight-30-iHeight)/2;        
	 
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
	    window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
	}

//原始window.open
function popWinOpen(url,x,y){
	var top = (window.screen.height -y)/2;
	var left = (window.screen.width -x)/2;
	var newwin=window.open(url,"winsafe","toolbar=yes,status=yes,menubar=yes,scrollbars=yes,resizable=0,top="+top+",left="+left+" ,width="+x+",height="+y);
	newwin.focus();
	return false;
}


//动态计算Iframe高度
function setIframeHeight(v_obj){
	//var cwin=document.getElementById("submsg");
	var cwin=v_obj;
	if (document.getElementById){ 
		if (cwin && !window.opera){ 
			if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight){
				cwin.style.height = (cwin.contentDocument.body.offsetHeight)+"px"; 
				//cwin.style.width =(cwin.contentDocument.body.offsetWidth)+"px"; 
			}
			else if(cwin.Document && cwin.Document.body.scrollHeight){
//				if ( cwin.Document.body.scrollHeight < 400 ){
//					cwin.style.height=400+"px";
//				}else{
//					cwin.style.height = (cwin.Document.body.scrollHeight)+"px";
//				}
				cwin.style.height = (cwin.Document.body.scrollHeight+200)+"px";
				//cwin.style.width = (cwin.Document.body.scrollWidth)+"px";
			}
		}
	}
}


function setParentIframeHeight(v_obj, v_parent_iframe,v_parent_div){
	var cwin=v_obj;
	var currentIframeHeight=0;
	if (document.getElementById){ 
		if (cwin && !window.opera){ 
			if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight){
				currentIframeHeight = cwin.contentDocument.body.offsetHeight; 
			}
			else if(cwin.Document && cwin.Document.body.scrollHeight){				
			currentIframeHeight = cwin.Document.body.scrollHeight;
			}
		}
	}
	//alert(parent.document.getElementById(v_parent_div).offsetHeight+"===="+currentIframeHeight);
	parent.document.getElementById(v_parent_iframe).style.height=(parent.document.getElementById(v_parent_div).offsetHeight+currentIframeHeight)+"px";
}

//jiazai
function showloading(content){   
    var loaddiv;   
    if(loaddiv=document.getElementById("loaddiv")){   
        loaddiv.style.display = "block";   
    }else{   
        loaddiv = document.createElement("DIV");   
        loaddiv.id="loaddiv";   
        loaddiv.style.position = "absolute";   
        loaddiv.style.zIndex = 1000;   
        loaddiv.style.display="";   
        loaddiv.style.left = 0;   
        loaddiv.style.top  = 0;   
        loaddiv.style.heigth=90+"px";
        loaddiv.style.border = "1px solid gray"  
        loaddiv.style.background = "#ffffff"  
        loaddiv.style.padding = "5";   
    }   
       
    var scrollTop=0;   
    if(top.document.documentElement && top.document.documentElement.scrollTop){   
        scrollTop = top.document.documentElement.scrollTop;   
    }else if(document.body){   
        scrollTop = top.document.body.scrollTop;   
    }   
    var scrollWid = document.body.scrollWidth/2-50;   
       
    loaddiv.style.left = scrollWid+"px";   
    loaddiv.style.top  = (document.body.scrollHeight/3)+"px" ;   
    if (content != undefined && content != '') {
    	loaddiv.innerHTML = "<div><h>"+content+".....</h><br><img src='../images/loading.gif' style='width:160px;height:10px;'></div>";   
	} else {
		loaddiv.innerHTML = "<div><h>数据正在处理，请稍等.....</h><br><img src='../images/loading.gif' style='width:160px;height:10px;'></div>";
	}   
    
    document.body.style.cursor="wait";     
       
  var sWidth,sHeight;   
  sWidth=document.body.scrollWidth;//浏览器工作区域内页面宽度 或使用 screen.width//屏幕的宽度   
  sHeight=document.body.scrollHeight;//屏幕高度（垂直分辨率）   
  
  //背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色）   
  var bgObj=document.createElement("div");
  bgObj.setAttribute('id','bgDiv');   
  bgObj.style.position="absolute";   
  bgObj.style.top="0";   
  bgObj.style.background="#ffffff";   
  bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";   
  bgObj.style.opacity="0.6";   
  bgObj.style.left="0";   
  bgObj.style.width=sWidth + "px";   
  bgObj.style.height=sHeight + "px";   
  bgObj.style.zIndex = "10000";   
  document.body.appendChild(bgObj);//在body内添加该div对象    
  document.body.appendChild(loaddiv);   
}

//去掉空格
String.prototype.trim   =   function() {   
      return   this.replace(/(^\s*)|(\s*$)/g,   "");   
}  


//判断obj是否已经存在objvalue,存在返回true
function isready(obj, objvalue){
	var productid = document.all.item(obj);
	if ( productid == null){
		return false;
	}
	if ( productid.length){
		for (h=0; h<productid.length; h++){
			if ( productid[h].value == objvalue ){				
				return true;
			}
		}
	}else{
		if ( productid.value == objvalue ){				
			return true;
		}
	}
	return false;
}

function isready2(obj, objvalue,obj2,objvalue2){
	var productid = document.all.item(obj);
	var pobj2 = document.all.item(obj2);
	if ( productid == null){
		return false;
	}
	if ( productid.length){
		for (h=0; h<productid.length; h++){
			if ( productid[h].value == objvalue && pobj2[h].value== objvalue2){				
				return true;
			}
		}
	}else{
		if ( productid.value == objvalue && pobj2.value== objvalue2){				
			return true;
		}
	}
	return false;
}

//删除选择行
function deleteR(v_chebox,v_dbtable){
	chebox=document.all(v_chebox);
	if(chebox!=null){	
		if (chebox.length >=1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById(v_dbtable).deleteRow (i);
				i=i-1;
			  }
			}
		}else{
			if (chebox.checked ){
				document.all(v_dbtable).deleteRow(1);
			}
		}
 	 }
}

//得到行对象 
function getRowObj(obj) { 
	var i = 0; 
	while(obj.tagName.toLowerCase() != "tr"){ 
		obj = obj.parentNode; 
		if(obj.tagName.toLowerCase() == "table")return null; 
	} 
	return obj; 
} 

//根据得到的行对象得到所在的行数 
function getRowNo(obj){ 
	var trObj = getRowObj(obj); 
	var trArr = trObj.parentNode.children; 
	for(var trNo= 0; trNo < trArr.length; trNo++){ 
		if(trObj == trObj.parentNode.children[trNo]){ 
			return trNo+1; 
		} 
	} 
}
//只能输入数字和小数点
function clearNoNum(obj){   
	obj.value = obj.value.replace(/[^\d.]/g,"");   
	obj.value = obj.value.replace(/^\./g,"");   
	obj.value = obj.value.replace(/\.{2,}/g,".");   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");   
} 


//验证手机或电话
String.prototype.isMobileOrTel=function(){
	if(this.isMobile()||this.isTel()||this.isTel2()){
		return true;
	}
	return false;
	
}

String.prototype.isMobile = function() { 
	return (/^1\d{10}$/.test(this.trim())); 
}
String.prototype.isTel2=function(){
	 regexp=/^\d{7,8}$/;
	return  regexp.test(this.trim());
	
}
String.prototype.isTel = function()
{
    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(this.trim()));
}

String.prototype.isTel1=function(){
	if(this.isTel()||this.isTel2()){
		return true;
	}
	return false;
}

function clearNoInt(obj){   
	obj.value = obj.value.replace(/[^\d]/g,"");   
} 
//是否身份证号码
function isIdCard(str){
	var myRegExp=/^\d{17}[\d|x]$|^\d{15}$/i;	
	return myRegExp.test(str);
}

function isEamil(str){
	var regex = /^[a-zA-Z0-9]([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([\w-]+\.)+[a-zA-Z]{2,}$/;  
	return regex.test(str);
}
//只能数字
function onlyNumber(e)
{
	var isIE = false;
	var isFF = false;
	var isSa = false;

	if ((navigator.userAgent.indexOf("MSIE")>0) && (parseInt(navigator.appVersion) >=4))isIE = true;
	if(navigator.userAgent.indexOf("Firefox")>0)isFF = true;
	if(navigator.userAgent.indexOf("Safari")>0)isSa = true; 

    var key;
    iKeyCode = window.event?e.keyCode:e.which;

    if( !(((iKeyCode >= 48) && (iKeyCode <= 57)) ||((iKeyCode >= 96) && (iKeyCode <= 105))|| (iKeyCode == 13) || (iKeyCode == 46) || (iKeyCode == 45) || (iKeyCode == 37) || (iKeyCode == 39) || (iKeyCode == 8)))
    {    
        if (isIE)
        {
            e.returnValue=false;
        }
        else
        {
            e.preventDefault();
        }
    }
}

//只能数值类型
function KeyPress(objTR)
{
   var txtval = objTR.value;
   var key = event.keyCode;
   if((key <48 || key >57)&&key !=46)
   {
         event.keyCode = 0;
   }
   else
   {
         if(key == 46)
         {
               if(txtval.indexOf(".") != -1 || txtval.length == 0)
                     event.keyCode = 0;
         }
   }

}


function formatCurrency(num) {
 num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   cents = num%100;
   num = Math.floor(num/100).toString(); 
   if(cents<10)
   cents = "0" + cents;
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num + '.' + cents);
}

function setAddr(obj){
	var tv= "";
	if ( $F('province') != ""){
		tv=tv+$('province').options[$('province').selectedIndex].text;
	}
	if ( $F('city') != ""){
		tv=tv+" "+$('city').options[$('city').selectedIndex].text;
	}
	if ( $F('areas') != ""){
		tv=tv+" "+$('areas').options[$('areas').selectedIndex].text;
	}
	if ( obj.value.indexOf(tv) == -1 ){
		obj.value=tv;
		obj.select();
	}
}

function clearUser(obj_MakeID,obj_uName){
	if(document.getElementById(obj_MakeID)){
		document.getElementById(obj_MakeID).value="";
	}
	if(document.getElementById(obj_uName)){
		document.getElementById(obj_uName).value="";
	}
}
		
function clearDeptAndUser(v_MakeDeptID,obj_deptname,obj_MakeID,obj_uName){
	if(document.getElementById(v_MakeDeptID)){
		document.getElementById(v_MakeDeptID).value="";
	}
	if(document.getElementById(obj_deptname)){
		document.getElementById(obj_deptname).value="";
	}
	if(document.getElementById(obj_MakeID)){
		document.getElementById(obj_MakeID).value="";
	}
	if(document.getElementById(obj_uName)){
		document.getElementById(obj_uName).value="";
	}
}

function formatFloat(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
}

