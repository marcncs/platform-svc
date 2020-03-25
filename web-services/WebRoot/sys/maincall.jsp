<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>-WINDRP-分销系统</title>
<script src='../js/prototype.js'></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script type="text/javascript">
var bb = setInterval("awake();",100000);
function awake(){
	var url = '../self/xMLAwakeAction.do';	
	var pars = '';
	var myAjax = new Ajax.Request(
			url,
			{method: 'get', parameters: pars, onComplete: showResponse1}
			);		
}
	
function showResponse1(originalRequest){
	var awake = originalRequest.responseXML.getElementsByTagName("awake"); 
	var strcontent="";
	if(awake.length>=1){
		switchdiv();
	}else{
		return false;
	}
	
	for(var i=0;i<awake.length;i++){
		var a = awake[i];
		var content = a.getElementsByTagName("awakecontent")[0].firstChild.data;
		var strid =a.getElementsByTagName("awakeid")[0].firstChild.data ;
		if(content.length>51){
			content=content.substring(0,50)+"&hellip;";
		}
		strcontent = "<div><div style='background-color:#003399;color:#fff;text-align:left;font-weight:bold;height:25px;line-height:25px;'><span style='float:right;font-weight:normal;cursor:hand;' onclick=\"document.getElementById('awake').style.display='none'\">&times;&nbsp;</span>&nbsp;<bean:message key='sys.main.systemawake'/></div><form name='form1' method='post' action=''><div id='tip'><a href='javascript:void(0)' onclick='showAwake("+strid+")'>" + content+ "</a></div><div id='but'><input type=\"button\" value=\"<bean:message key='sys.main.iknow'/>\" onclick='affrie("+strid+")'></div></form></div>";
	}
	document.getElementById("content").innerHTML=strcontent;
}



function showAwake(id){
	var url="../self/awakeAction.do?ID="+id;
	//alert(url);
	window.open (url, '', 'height=300, width=500, top=300, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no') ;
	document.getElementById('awake').style.display='none';
}
	
function affrie(id){				//确认提醒
	var url = '../self/affrieAwakeAction.do';
   var pars = 'ID='+id;
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: ''}
				);
	document.getElementById('awake').style.display='none';
}
	//-------------------------------------------------------------------
	var handle;
	function showtips()
	{
		handle = setInterval("delwidth()",10)	
		delwidth();
	}
	function delwidth()
	{
		var obj = document.getElementById("awake");
		obj.style.top = (parseInt(obj.style.top)-4).toString()+"px";
		if(parseInt(obj.style.top)-4 < parseInt(document.body.clientHeight)-160)
		{
			clearInterval(handle);
		}
	}
	
	function switchdiv()
	{
		var obj = document.getElementById("awake");
		if(obj.style.display=="none")
		{
			document.getElementById('awake').style.top = document.body.clientHeight;//等于浏览器的高度
			obj.style.display="block";
			//awake();
			showtips();
		}else
		{
			obj.style.display="none";
		}
	}
	
	var topc=3;
	function HiddenTopTiele(){
		if (topc==3){
			topc=4;
			document.all("toptitle").style.display="none";
			document.getElementById('updn').innerHTML="<img src='../images/CN/showdown.gif' width='16' height='16' border='0' align='absmiddle'>";
		}else{
			topc=3;
		document.all("toptitle").style.display="";
		document.getElementById('updn').innerHTML="<img src='../images/CN/hiddenup.gif' width='16' height='16' border='0' align='absmiddle'>";
		}
	}

	function hasStockpileAwake(){
		<c:choose>
			<c:when test="${isOrganAwake==null}">
				$('message').innerHTML="<img src='../images/laba-10.gif' border=0 display>";
			</c:when>
			<c:otherwise>
				showStockAnnun();
			</c:otherwise>
		</c:choose>		
	}
	
	function showStockAnnun(){
	   var url = '../users/readStockAnnumciatorAction.do';
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse3}
                    );
					
	}
	  function showResponse3(originalRequest)
    {
		var id = originalRequest.responseXML.getElementsByTagName("id"); 
		if(id.length>=1)
		{
			$('message').innerHTML="<a href=\"javascript:showMessage();\"><img src='../images/laba-10r.gif' border=0></a>";
		}
		else
		{
			$('message').innerHTML="<a href=\"javascript:showMessage();\"><img src='../images/laba-10.gif' border=0></a>";
		}
		//setTimeout("showStockAnnun()",150000);
    }

	function showMessage(){
		var url="../users/showAnnumciatorAction.do";
		$('message').innerHTML="<a href=\"javascript:showMessage();\"><img src='../images/laba-10.gif' border=0></a>";
		window.open (url, '', 'height=600, width=800, top=100, left=200, toolbar=no, menubar=no, scrollbars=no,resizable=yes,location=n o, status=no') ;
	}

function switchSysBar(){
	if (switchPoint.innerText==3){
		switchPoint.innerText=4
		document.all("frmTitle").style.display="none"
	}else{
		switchPoint.innerText=3
	document.all("frmTitle").style.display=""
	}
}
	function closeSys(){
		if ( confirm("你确认要退出系统？") ){	
			clock.action="../exitAction.do";
			clock.submit();
		}
	}
	
	function refeshWindow(){
		main.window.location.href = main.window.location.href;
		//main.window.location.reload();
	}
//----------------------------呼叫中心---------------------------------------	
	var worknumber=""; 
	var userid=${users.userid};
	worknumber=userid;
	if ( userid < 10 ){
		worknumber="00"+userid;
	}
	if ( userid > 10 && userid < 100 ){
		worknumber="0"+userid;
	}
	
	
	var firstlogin=true;
	function getCurdate(){ 
		var curDate = new Date();
		Y = curDate.getYear(); 
		M = curDate.getMonth() + 1;
		D = curDate.getDate();
		if (M < 10)
			M = "0" + M;
		if (D < 10)
			D = "0" + D;   
		return Y + "" + M + "" + D;    
	}  
	
	//获取唯一的computeid
	var computeid="";	
	function getComputeid(){
		sendServer();
		if ( computeid == ""){
			sendServer();
		}
		//alert(computeid);
		return computeid;
	}
	
	var xmlHttp; 
	function sendServer() { 	
		if (window.ActiveXObject){ 
			   xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
		}else if(window.XMLHttpRequest){ 
			   xmlHttp = new XMLHttpRequest(); 
		} 
		
		var url="../sales/ajaxComputeIdAction.do"; 
		xmlHttp.open("post",url,false); 
		xmlHttp.onreadystatechange = callback; 
		xmlHttp.send(null); 
	} 
	
	function callback(){ 
		if (xmlHttp.readyState == 4) { 
			if (xmlHttp.status == 200) { 
				var data = eval('(' + xmlHttp.responseText + ')');
				computeid = data.computeid;
			} 
		}          
	}

	
	//div名字
	var divname=["listen","indrue","diaodu","zhuany","sanfan","play","qiangchai","qiangjie"];
	function ShowListen(obj){
		for(i=0;i<divname.length;i++){
			CloseListen(divname[i]);
		}
		$(obj).style.visibility = "visible" ;
	}
	
	function CloseListen(obj){
		$(obj).style.visibility = "hidden";
	}
	
	
	function startClient(){
		// alert("start");	
		 cti.StartClient("${callcenterip}",21000);
		 setTimeout("logIn()",5000);		
	}
	
	//签到
	function logIn(){
		cti.WorkNumber=worknumber;
		cti.SendData("LogIn"+worknumber);	   
	   	cti.attachEvent("OnReceiveMsg",receiveMsg);
	}
	
	//签退	
	function logOut(){
			//alert("end");		
			cti.SendData("LogOut"+worknumber);
			cti.WorkNumber="";
		}
		
		//来电挂断
		function hangUp(){
		 cti.SendData("HangUp");	   
		}
		
		//离席-示忙
		function leaveSt(){
		 cti.SendData("LeaveSt");	
		 ${"but_leavest"}.disabled=true;
		 ${"but_enterst"}.disabled=false;
		 setCookieTime("leaveStbegin","1",1);		 
		}
		//入席-示闲
		function enterSt(){
		 cti.SendData("EnterSt");	
		 ${"but_leavest"}.disabled=false;
		 ${"but_enterst"}.disabled=true;	
		 delCookies("leaveStbegin");   
		}
		
		//来电挂起
		function holdSta(){
			var compid = getComputeid();
			cti.SendData("HoldSta"+compid);	
			${"but_sta"}.disabled=true;
			${"but_stp"}.disabled=false;
			setCookieTime("holdStabegin","1",1);	
		}
		
		//来电恢复
		function holdStp(){
			var compid = getComputeid();
			cti.SendData("HoldStp"+compid);	
			${"but_sta"}.disabled=false;
			${"but_stp"}.disabled=true;
			delCookies("holdStabegin"); 
		}
		
		//内呼-呼叫座席
		var diaocompid="";
		var diaoid="";
		function diaoduIn(){
			//"Diaodu" + ComputerID + AlarmType + Index + telephoneNumber
			var diaoduid = document.getElementById("diaoduid").value;
			if ( diaoduid == ""){
				alert("请输入呼叫号码！");
				return;
			}
			var compid = getComputeid();
			//alert("compid="+compid);
			diaocompid = compid;
			diaoid = diaoduid;
			var str=compid+"001"+"1"+diaoduid;
			cti.SendData("Diaodu"+str);
		}	
		function showStock(){
		
		}		
		
		//来电转内线
		//打进来的computeid
		var zhuanyi_computeid="";
		var zhuanyi_alarmtype="";
		function zhuanYIn(){
			//"ZhuanY" + ComputerID + AlarmType + Index + telephoneNumber
			var zhuanyid = document.getElementById("zhuanyid").value;
			var compid = zhuanyi_computeid+zhuanyi_alarmtype;
			var str=compid+"1"+zhuanyid;
			cti.SendData("ZhuanY"+str);
		}
		
		
		//会议加内线
		function sanFanIn(){
			//"SanFan" + ComputerID + AlarmType + Index + telephoneNumber
			var sanfanid = document.getElementById("sanfanid").value;
			var compid = getComputeid();
			var str=compid+"001"+"1"+sanfanid;
			//alert("compid="+ compid);
			cti.SendData("SanFan"+str);
		}
		
		//强插
		function intrude(){
		 	var intrudeid = document.getElementById("intrudeid").value;
		 	cti.SendData("Intrude"+intrudeid);	  
		}
		
		//监听
		function listen(){
			var listenid = document.getElementById("listenid").value;
			//alert(listenid);
		 	cti.SendData("Listen"+listenid);	   
		}
		
		//强拆
		function qiangchai(){
			var qiangchaiid = document.getElementById("qiangchaiid").value;
			cti.SendData("HangUp"+qiangchaiid);
		}
		
		//强接-拦截
		function qiangjie(){
			var qiangjieid = document.getElementById("qiangjieid").value;
			cti.SendData("QIANGJIE"+qiangjieid);
			setCookie("qiangjiebegin");
		}
		
		//验证
		function yanzheng(){
			cti.SendData("ToIVRYanZheng");
		}
		
		//评分
		function pingfen(){
			cti.SendData("ToIVRPingFen");
		}
		
		//放音
		function play(){
			var playid = document.getElementById("playid").value;
			var playtypeobj = divform.playtype;
			var playtype=2;
			var sendstr="jPlay";
			for(i=0;i<playtypeobj.length;i++){
				if (playtypeobj[i].checked){
					playtype=playtypeobj[i].value;
				}
			}
			if ( playtype == 3 ){
				sendstr="iPlay";
			}else if ( playtype == 4 ){
				sendstr="cPlay";
			}
			
			//alert(sendstr+playid);
		 	cti.SendData(sendstr+playid);	   
		}
 
  	var iscalled=true;
	var isbjstart=true;
	//处理返回信息
	function receiveMsg(msg){	
		//签到信息
		if ( msg == ("LOGIN"+worknumber+":OK") ){			
			deallogin(1);
			setCookieTime("callCenterLogin","1",1);
		}
		//签退信息
		if ( msg == ("LOGOUT"+worknumber+":OK") ){
			deallogin(2);
			delCookies("callCenterLogin");
			delCookies("leaveStbegin");  
			delCookies("holdStabegin");  
			cti.StopClient();
		}		
		//alert(msg);
		//pringmsg(msg);
		//来电信息
		if ( msg.indexOf("CenterIsCalled") > 0 ){
			if (iscalled){
				calledshow(msg);
				//iscalled=false;
			}
		}
		//来电应答信息
		if ( msg.indexOf("BaojingStart") > 0 ){
			if (isbjstart){
				//alert(msg);
				callingsh(msg);
				//isbjstart=false;
			}
		}
		//呼叫电话信息
		if ( msg.indexOf("DiaoduStart") >= 0 ){
			diaodumsgdeal(msg);
		}
		
		if ( msg.indexOf("DTMF") == 0 ){
			alert(msg);
		}
		//来电挂起是否提机
		if ( msg.indexOf("NotHook") >= 0 ){
			alert("请您先提机，再操作该功能！");
		}
		//强接是否提机
		/*if ( msg.indexOf("QIANGJIENotHook") >= 0 ){
			alert("您还没提机！");
		}*/
		
	}
	//来电显示处理
	function calledshow(msg){
		var start=msg.indexOf("CenterIsCalled")+14;
		var type= msg.substring(start, start+3);
		var datetime=msg.substring(start+3, start+17);
		//alert(msg);
		var phone=msg.substring(start+17, msg.length);
	/*	if ( phone.indexOf(">") > 0){
			phone=phone.substring(0, phone.indexOf(">"));
		}
		phone = phone.replace(/\s*$/, "");
		window.open("../sales/listCalledMsgAction.do?telphone="+phone,"来电信息","height=400,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	*/
		if ( phone.indexOf(">") > 0){
			phone=phone.substring(0, phone.indexOf(">"));
			phone = phone.replace(/\s*$/, "");
		window.open("../sales/listCalledMsgAction.do?telphone="+phone,"来电信息","height=400,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}
		
	}
	
	//强接时显示来电信息
	function qiangjieShowMsg(phone){
		window.open("../sales/listCalledMsgAction.do?telphone="+phone,"来电信息","height=400,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	//应答处理
	function callingsh(msg){		
		var start=msg.indexOf("BaojingStart")+12;
		var callalarmtype= msg.substring(start, start+3);
		var computerid=msg.substring(start+3, start+17);
		var phone=msg.substring(start+17, msg.length);
		if ( phone.indexOf(",") >=0 ){
			phone=phone.substring(0, phone.indexOf(","));
		}
		zhuanyi_computeid=computerid;
		zhuanyi_alarmtype=callalarmtype;
		//alert("callalarmtype="+callalarmtype+"  computerid="+computerid+"   phone="+phone);
		//----------如果是强接先显示来电信息-----------
		var isqj = eval(getCookie("qiangjiebegin"));
		if ( isqj == 1 ){
			qiangjieShowMsg(phone);
			delCookies("qiangjiebegin");
		}
		//-----------------------------------------
		var url = "../sales/addCallCenterEventAction.do?callnum="+phone+"&callednum="+worknumber+"&et=0&at="+callalarmtype+"&cpid="+computerid;
			var pars = '';
			var myAjax = new Ajax.Request(url,
						{method: 'get', parameters: pars, onComplete: showStock}
						);
	}
	
	//签到签退显示信息处理
	function deallogin(flag){
		if ( flag == 1 ){ 
			${"lgmsg"}.innerHTML="工号"+worknumber+" 签到成功！";
			${"but_login"}.disabled=true;
			${"but_logout"}.disabled=false;
		}else if ( flag == 2 ){ 
			${"lgmsg"}.innerHTML="工号"+worknumber+" 签退成功！";
			${"but_login"}.disabled=false;
			${"but_logout"}.disabled=true;
		}
		$("loginshowmsg").style.visibility = "visible" ;
		setTimeout("CloseListen('loginshowmsg')", 1000);		
	}
	
	//呼叫电话信息处理
	function diaodumsgdeal(msg){		
		var start=msg.indexOf("DiaoduStart")+11;
		var cpid2=msg.substring(start, start+14);
		//alert("callalarmtype="+callalarmtype+"  computerid="+computerid+"   phone="+phone);
		var url = "../sales/addCallCenterEventAction.do?callnum="+worknumber+"&callednum="+diaoid+"&et=1&at=001&cpid="+diaocompid+"&cpid2="+cpid2;
			var pars = '';
			var myAjax = new Ajax.Request(url,
						{method: 'get', parameters: pars, onComplete: showStock}
						);
						
		
	}
	
	//页面加载信息处理
	function loadCallCenter(){
		//----------已经签了刷新时再发一次签到信息-----------------
		var callCenterLogin = eval(getCookie("callCenterLogin"));
		if ( callCenterLogin == 1 ){
			logIn();
			//${"but_login"}.disabled=true;
			//${"but_logout"}.disabled=false;
		}
		//------------------------------------------------------
		//----------已经示忙了将其设成不可用-----------------
		var leaveStbegin = eval(getCookie("leaveStbegin"));
		if ( leaveStbegin == 1 ){
			leaveSt();
			//${"but_leavest"}.disabled=true;
		 	//${"but_enterst"}.disabled=false;
		}
		//------------------------------------------------------
		//----------已经来电挂起了将其设成不可用-----------------
		var holdStabegin = eval(getCookie("holdStabegin"));
		if ( holdStabegin == 1 ){
			holdSta();
			//${"but_sta"}.disabled=true;
			//${"but_stp"}.disabled=false;
		}
		//------------------------------------------------------
		document.getElementById("main").src="../sales/calledCenterAction.do?cid=&tel=";
		//window.open("../sales/calledCenterAction.do?cid=&tel=","呼叫中心","height=900,width=1000,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function testshow(){	
		//测试呼叫电话
		//diaodumsgdeal("DiaoduStart200904231055361, True");
		//测试来电
		callingsh("BaojingStart0012009042312331238477141                        , True");
		setCookie("qiangjiebegin","1");
		//setCookie("callCenterLogin","1");
		//来电
		//window.open("../sales/listCalledMsgAction.do?telphone=15921554200","来电信息","height=400,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		//pringmsg("ssssssssssssssssssssssssssssss");
	}
	
	function pringmsg(msg){
		var str = ${"shmsg"}.innerHTML;
		${"shmsg"}.innerHTML=str+"<br>"+msg;
	}
	
	function showCallDiv(){
		if ( ${"callcenterdiv"}.style.visibility == "visible" ){
			${"callcenterdiv"}.style.visibility = "hidden";
		}else{
			${"callcenterdiv"}.style.visibility = "visible";
		}
	}

//----------------------------呼叫中心---------------------------------------	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
#awake {
	position: absolute;
	width: 100%;
	text-align: right;
	font-size: 12px;
	height: 0px;
	z-index:999;
	display:none;
	overflow:hidden;
	filter: Alpha(Opacity=85);
}
#awake #content {
	height: 160px;
	width: 150px;
	background-color: #CCCCCC;
	text-align: left;
	border: 1px solid #0066FF;
	overflow:hidden;
	word-break:break-all;
}
#awake #content #tip {
	padding:16px;
}	

#awake #content #but {
	text-align: center;;
}
#awake #content a {
	text-decoration:none;
	color: #0066FF;
}
body {
	MARGIN-BOTTOM:0px;
	overflow-y:hidden;
}


#loginshowmsg,#listen,#indrue,#diaodu,#zhuany,#sanfan,#play,#qiangchai,#qiangjie {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}

#callshow {
	position:absolute;
	left:400px;
	top:0px;
	width:170px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>


<body leftmargin="0" topmargin="0" onLoad="hasStockpileAwake();document.getElementById('awake').style.top=document.body.clientHeight+'px';showtips();getAjaxComputeid();">
<div id="awake">
  <div id="content" onMouseover="clearInterval(bb);" onMouseout="bb = setInterval('awake();',20000);"></div>
</div>
<form NAME="divform">
<div id="loginshowmsg">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td height="32" align="center"><span id="lgmsg"></span></td>
    </tr>
    <tr>
	  <td align="center"><input type="button" Onclick="CloseListen('loginshowmsg');" value="取消"></td>
    </tr>
  </table>
</div>
<div id="listen">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">监听号码：</td>
      <td width="35%"><input type="text" name="listenid" size="8" ></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始监听" onClick="listen()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('listen');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="indrue">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">强插号码：</td>
      <td width="35%"><input type="text" name="intrudeid" size="8"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始强插" onClick="intrude()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('indrue');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="qiangchai">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">强拆座席：</td>
      <td width="35%"><input type="text" name="qiangchaiid" size="8"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始强拆" onClick="qiangchai()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('qiangchai');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="qiangjie">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">强接座席：</td>
      <td width="35%"><input type="text" name="qiangjieid" size="8"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始强接" onClick="qiangjie()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('qiangjie');" value="取消"></td>
    </tr>
  </table>
</div>


<div id="diaodu">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">拨叫号码：</td>
      <td width="35%"><input type="text" name="diaoduid" size="13"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始拨叫" onClick="diaoduIn()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('diaodu');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="zhuany">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">转接号码：</td>
      <td width="35%"><input type="text" name="zhuanyid" size="13"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始转接" onClick="zhuanYIn()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('zhuany');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="sanfan">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">会议号码：</td>
      <td width="35%"><input type="text" name="sanfanid" size="13"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始会议" onClick="sanFanIn()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('sanfan');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="play">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
     <td width="45%" height="32">放音类型：</td>
      <td width="35%">
	  	<input type="radio" name="playtype" value="2" checked>播放金额
		<input type="radio" name="playtype" value="3">播放数量
		<input type="radio" name="playtype" value="4">播放号码
		</td>
    </tr>
	<tr>
     <td width="45%" height="32"></td>
      <td width="35%"><input type="text" name="playid" size="13"></td>
    </tr>
    <tr>
      <td colspan="3"><input type="button" name="bb" value="开始放音" onClick="play()"></td>
	  <td colspan="3"><input type="button" name="cc" Onclick="CloseListen('play');" value="取消"></td>
    </tr>
  </table>
</div>

<div id="callshow">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
     <tr>
     <td colspan="2" align="center"><font color="#FF0000">您有新的来电!</font></td>
    </tr>
	<tr>
     <td width="45%" >来电类型：</td>
	 <td id="AlarmType" align="left"></td>
    </tr>
	<tr>
     <td width="45%">来电号码：</td>
	 <td id="TelephoneNumber" align="left"></td>
    </tr>
    <tr>
	  <td colspan="2" align="center"><input type="button" name="cc" Onclick="CloseListen('callshow');" value="取消"></td>
    </tr>
  </table>
</div>


<div id="callcenterdiv" style="position:absolute; width:550px; left:150; visibility:hidden">
<div align="left" style="height:10;margin-left:5;">
    <input type="button" id="but_login" onClick="startClient()" class="btn2" value="签到">
    <input type="button" id="but_logout" onClick="logOut()" class="btn2" value="签退" disabled>
    <input type="button" onClick="hangUp()" class="btn2" value="来电挂断">
    <input type="button" id="but_leavest" onClick="leaveSt()" class="btn2" value="示忙">
    <input type="button" id="but_enterst" onClick="enterSt()" class="btn2" value="示闲" disabled>
    <input type="button" id="but_sta" onClick="holdSta()" class="btn2" value="来电挂起">           		
    <input type="button" id="but_stp" onClick="holdStp()" class="btn2" value="来电恢复" disabled>
    <input type="button" onClick="ShowListen('diaodu')" class="btn2" value="呼叫">
    <input type="button" onClick="ShowListen('play')" class="btn2" value="放音">
</div>
<div align="left" style="height:10; margin-left:5; margin-top:8px;">				
<input type="button" onClick="ShowListen('zhuany')" class="btn2" value="来电转移">
<input type="button" onClick="ShowListen('sanfan')" class="btn2" value="会议">
<input type="button" onClick="pingfen()" class="btn2" value="评分">				
<input type="button" onClick="ShowListen('listen')" class="btn2" value="监听">
<input type="button" onClick="ShowListen('indrue')" class="btn2" value="强插">
<input type="button" onClick="ShowListen('qiangchai')" class="btn2" value="强拆">
<input type="button" onClick="ShowListen('qiangjie')" class="btn2" value="强接">
<input type="button" onClick="loadCallCenter()" class="btn2" value="呼叫中心">
<!--<span style="margin-right:15"><a href="javascript:testshow();">测试</a></span>
<span style="margin-right:15"><a href="javascript:yanzheng();">验证</a></span>-->
</div>
<div id="shmsg"></div>
</div>
</form>


<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D8F3C6">
        <tr> 
          <td width="13%">
		  <div align="right">
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="toptitle" style="display:yes">
              <tr>
                <td width="0%" bgcolor="#FFFFFF"></td>
                <td width="65%" " bgcolor="#FFFFFF" height="50"><span class="text-white">&nbsp;&nbsp;&nbsp;&nbsp;</span><img src="../images/logo.gif" border="0">&nbsp;</td>
                
                <td width="35%" align="right" valign="bottom" " bgcolor="#FFFFFF"><span style="font-size:10px; color:#CCC">技术支持&nbsp;</span><a href="http://www.winsafe.cn" target="_blank"><img src="../images/winsafelogo.jpg" width="67" height="21" border="0" align="bottom"></a>&nbsp;</td>
        		</tr>
        	</table>
           </div>
		   </td>
        </tr>
        <tr>
          <td  background="../images/CN/top-back1.gif">
		  	<div align="right">
		  	<form NAME="clock" onSubmit="0">
            <table width="100%" height="29"  border="0" cellpadding="0" cellspacing="0">
			
              <tr>
                    <td width="60%"><img src="../images/CN/spacer.gif" width="10" height="1">
                      <span class="text-blue">当前机构：${makeorganidname}&nbsp;&nbsp;&nbsp;用户：${realname}&nbsp;&nbsp;&nbsp;</span><span class="text-blue">库存报警：</span><span id="message" style="vertical-align:middle"> </span></td>
                    <td width="5%"></td>
                    <td width="35%"> 
                      <div align="right"> 
                        <span class="text-blue">                        
                        <a href="javascript:showCallDiv()"><img src="../images/CN/callcent.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;呼叫中心</a> | 
                        <a href="javascript:refeshWindow()" ><img src="../images/CN/shuaxin.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;刷新</a> | 
                        <a href="http://xxx/help/help.asp" target="_blank"><img src="../images/CN/help.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;帮助</a> | 
                      <a href="javascript:closeSys()"><img src="../images/CN/exit.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;退出</a></span>&nbsp;&nbsp;<a href="javascript:HiddenTopTiele();"><span id="updn"><img src="../images/CN/hiddenup.gif" width="16" height="16" border="0" align="absmiddle"></span></a><img src="../images/CN/spacer.gif" width="10" height="1"></div></td>
              </tr>
			 
            </table>
             </form>			
          </div>
		</td>
        </tr>
        
        </table>
	</td>
  </tr>
  <tr>
    <td valign="top">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top"> 		
            <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
                <TR> 
                  <TD align="middle" vAlign="top" noWrap id="frmTitle" >
				  <c:import url="left.jsp"/>
				  
				  <!--
				  <IFRAME 
      style="Z-INDEX: 2; VISIBILITY: inherit; WIDTH: 120px; HEIGHT: 100%" 
      name=yuzi src="left.jsp" frameBorder=0></IFRAME> -->
	  			</TD>
                  <TD background="images/CN/spot1.gif" bgColor="#ECF0F5"> 
                    <TABLE height="100%" cellSpacing=0 cellPadding=0 border=0>
                      <TBODY>
                        <TR> 
                          <TD style="HEIGHT: 100%" onClick="switchSysBar()"><FONT 
            style="FONT-SIZE: 6pt; CURSOR: hand; COLOR: black; FONT-FAMILY: Webdings"><SPAN 
            id=switchPoint><FONT color=#000000>3</FONT></SPAN> </FONT></TR>
                      </TBODY>
                    </TABLE></TD>
                  <TD bgcolor="#D8F3C6" style="WIDTH: 100%"> 
				 <IFRAME id="main" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="main" src="../sys/listSelfAwakeAction.do" frameBorder=0 
      scrolling=yes></IFRAME>
	  </TD>
                </TR>
              </TBODY>
            </TABLE></td>
        </tr>
      </table>	 
	  </td>
  </tr>
<!--  <tr>
    <td height="1">
		<table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" background="../images/CN/low-back.gif">
        <tr> 
          <td height="1"></td>
        </tr>
      </table>
	</td>
 </tr>-->
</table>
<!--javascript响应OCX控件的事件-->
<OBJECT name=cti 
classid="clsid:2A11C996-9AD5-462B-BECC-10C1825AEBFA"
codebase="CTIclient.ocx#version=1,0,96,0"
width=0
height=0
align=center
hspace=0
vspace=0
>
</body>
</html>