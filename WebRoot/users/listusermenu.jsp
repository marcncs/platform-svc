<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}

function addNew(){
	popWin("../sales/toAddLinkManAction.do",900,600);
	}

	function Update(){
		if(checkid>0){
popWin("updLinkManAction.do?id="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	//function Detail(id){
	//window.open("linkManDetailAction.do?id="+id,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	//}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="linkManDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delLinkmanAction.do?LID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function updUserMenu(obj,userid,lmid){
	 
		if ( obj.checked ){
			//alert('userid='+userid+" lmid="+lmid);
			var updurl = "../users/addUserLeftMenuAction.do?userid="+userid+"&lmid="+lmid;
			updPurview(updurl);
		}else{
			var updurl = "../users/delUserLeftMenuAction.do?userid="+userid+"&lmid="+lmid;
			updPurview(updurl);
		}
	}
	
	function updPurview(updurl){
		var pars = '';
        var myAjax = new Ajax.Request(
                    updurl,{method: 'get', parameters: pars, onComplete: updResult}
                    );
	}
	
	function updResult(updpurview)
    {
		var result = updpurview.responseXML.getElementsByTagName("resultStr")[0].firstChild.data;
		buildText(result);//赋值给信息提示框

    }
	
	function buildText(str) {//赋值给消息提示框

		//alert(str);
		document.getElementById("result").style.display="";
		document.getElementById("result").innerHTML=str;
		setTimeout("document.getElementById('result').style.display='none'",500);
	}
	

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style>
#result {font-weight:bold; position:absolute;left:753px;top:20px; text-align:center; background-color:#ff0000;color:#fff;LEFT:expression(Math.abs(Math.round((document.body.clientWidth - result.offsetWidth)/2))); TOP:expression(Math.abs(Math.round((document.body.clientHeight)/2+document.body.scrollTop-180)))}
#result h1 {font-size:12px; margin:0px; padding:0px 5px 0px 5px};
</style>
</head>

<body>

<div id="result"></div>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 采单列表 </td>
  </tr>
</table>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

<tr>
	<td>
 <logic:present name="mls">
 <table border="0" width="100%" cellpadding="0" cellspacing="1" >
 	<c:forEach var="m" items="${mls}" varStatus="vs"><td width="17%" align="left" valign="top">
	
	 
	 
		<tr class="table-back" onClick="CheckedObj(this,${m.lmid});">
	    	<td  class="title-top"><input type="checkbox" onClick="updUserMenu(this,${cuserid},${m.lmid})" border="0" name="haspower" ${m.userid==0?"":"checked"}>${m.lmenuname}</td>
	  	</tr>
	  	<c:set var="list" value="${m.datelist}"/>
	 </c:forEach> 
	  	
		<c:forEach var="l" items ="${list}">
		 
		  <tr class="table-back" onClick="CheckedObj(this,${l.lmid});">
		    <td><input type="checkbox" onClick="updUserMenu(this,${cuserid},${l.lmid})" border="0" name="haspower" ${l.userid==0?"":"checked"}>${l.lmenuname}</td>
		  </tr>
	  </c:forEach> 
	  
	    
 </table>
    </logic:present> 
 
 
 </td>
  </tr>
 
</table>
 </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
            <!-- <tr align="center">
                <td width="60"><a href="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();" >修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr> -->
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	</td>
  </tr>
</table>


</body>
</html>
