<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ page import="com.winsafe.drp.util.*" %>
<html>
<head>
<title>欢迎登陆RTCI系统</title>
<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
<script src='../js/function.js'></script>
<script type="text/javascript">
$(function(){ 
	if('${msg}'!="") {
		if(confirm("${msg}")){
			$("#main").attr('src','../users/toUpdSelfPwdAction.do');
			//window.location.href="../users/toUpdSelfPwdAction.do";
		} 
	}
});
var bb = setInterval("awake();",100000);
function awake(){
	var url = '../self/xMLAwakeAction.do';	
	var pars = '';
	// var myAjax = new Ajax.Request(
	//		url,
	//		{method: 'get', parameters: pars, onComplete: showResponse1}
	//		);		
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
				// $('message').innerHTML="<img src='../images/laba-10h.gif' border=0 display>";
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
		//setTimeout("showStockAnnun()",4000);
    }

	function showMessage(){
		var url="../users/showAnnumciatorAction.do";
		$('message').innerHTML="<a href=\"javascript:showMessage();\"><img src='../images/laba-10.gif' border=0></a>";
		window.open (url, '', 'height=600, width=800, top=100, left=200, toolbar=no, menubar=no, scrollbars=no,resizable=yes,location=n o, status=no') ;
	}

function switchSysBar(){
	if (switchPoint.innerText==3){
		switchPoint.innerText=4
		switchPoint.style.fontSize='12pt'
		document.all("frmTitle").style.display="none"
	}else{
		switchPoint.innerText=3
		switchPoint.style.fontSize='6pt'
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
	
	function openWinsafe(){
		var url = "http://www.winsafe.cn";
		popWinOpen(url,800,500);
	}
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
	padding:8px;
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
.list,li{list-style:none; margin:0; padding:0;}  

           .scroll{ width:100%; height:25px; overflow:hidden; text-align:center;}  

           .scroll li{ width:100%; height:25px; line-height:25px; overflow:hidden;}  

           .scroll li a{ font-size:14px;color:red; text-decoration:none;}  

           .scroll li a:hover{ text-decoration:underline;}  
-->
</style>
</head>


<body leftmargin="0" topmargin="0" onLoad="hasStockpileAwake();document.getElementById('awake').style.top=document.body.clientHeight+'px';showtips();">
<input type="hidden" id="timetips" name="timetips" value="${timetips}" />
<div id="awake">
  <div id="content" onMouseover="clearInterval(bb);" onMouseout="bb = setInterval('awake();',20000);"></div>
</div>


<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" valign="top">
<!-- add div to check height -->
<div id="mytitle" style="HEIGHT:85px">  <!-- 增加高度， 如果不写高度， IE会认为0 -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="100%" background="../images/logoMenuMid.jpg"> 
		  <div align="right">
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="toptitle" style="display:yes">
              <tr>
              
               <!--  <td height="50" ><img src="../images/logoMenu.jpg"></td> -->
                <td height="50" ><img src="../images/logo_bar.jpg"></td>
                <td width="auto" background="url('../images/logoMenuMid.jpg')"></td>
                <td align="right"><img src="../images/winsafeNewlogo.jpg" onclick="openWinsafe();"></td>
        	  </tr>
        	</table>
           </div>
		   </td>
        </tr>
        <tr>
          <td background="../images/CN/top-back1.gif">
		  	<div align="right">
		  	<form NAME="clock" onSubmit="0">
            <table width="100%" height="29"  border="0" cellpadding="0" cellspacing="0">
			
              <tr>
                    <td width="25%"><img src="../images/CN/spacer.gif" width="10" height="1">
                      <span class="text-blue">当前机构：${makeorganidname}&nbsp;&nbsp;&nbsp;用户：${realname}&nbsp;&nbsp;&nbsp;</span></td>
                    <td >
<div class="scroll">  

               <ul class="list">  
				   <logic:iterate id="d" name="affiches">
				   	<li><a href="#">${d}</a></li>  
				   </logic:iterate>
               </ul>  

           </div>  
</td>
                    <td width="35%"> 
                      <div align="right"> 
                        <span class="text-blue">
                        <%String loginMode = JProperties.loadProperties("system.properties",JProperties.BY_CLASSLOADER).getProperty("deployEnv");%>
						<%if(Constants.ENV_EXTERNAL.equals(loginMode)){%>
                        <a href="../legalnote/legalnote.jsp" target="_blank">法律申明</a> | 
                        <%}%>
                        <a href="../help/help.jsp" target="_blank"><img src="../images/CN/help.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;帮助/Help</a> | 
                      <a href="javascript:closeSys()"><img src="../images/CN/exit.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;退出/Quit</a></span>&nbsp;&nbsp;<a href="javascript:HiddenTopTiele();"><span id="updn"><img src="../images/CN/hiddenup.gif" width="16" height="16" border="0" align="absmiddle"></span></a><img src="../images/CN/spacer.gif" width="10" height="1"></div></td>
              </tr>
             </table>	
             </form>		
          </div>
		</td>
        </tr>
        
        </table>
</div> <!-- add mytitil end  -->
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
                 <!-- 20151228 chenchangfu
                  <TD bgcolor="#D8F3C6" style="WIDTH: 100%"> 
                   -->
                  <TD  style="WIDTH: 100%"> 
				 <IFRAME id="main" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="main" src="../sys/remind.htm" frameBorder=0 
      scrolling=yes></IFRAME>
	  </TD>
                </TR>
              </TBODY>
            </TABLE></td>
        </tr>
      </table>	  
	  </td>
  </tr>
</table>

</body>
<script type="text/javascript">
document.getElementById("menuTree").style.height = (document.body.clientHeight - document.getElementById("mytitle").clientHeight - 30)+"px";
function autoScroll(obj){  

                       $(obj).find(".list").animate({  

                           marginTop : "-25px"  

                       },500,function(){  

                           $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);  

                       })  

                   }  

                   $(function(){  

                       setInterval('autoScroll(".scroll")',5000)  

                   })  
//alert("document.body.clientHeight="+document.body.clientHeight+" mytitle.clientHeight="+document.getElementById("mytitle").clientHeight+" document.body.offsetHeight="+document.body.offsetHeight);
</script>
</html>
