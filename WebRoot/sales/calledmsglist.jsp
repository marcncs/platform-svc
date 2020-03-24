<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<head>
<title>来电信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	//10秒后关闭窗口
	var bb = setInterval("winClose();",15000);
	function winClose() { 
		//关闭窗口跳转到呼叫中心页面
		toaddsaleorder2();
		window.close(); 
	} 
function shake(){
	for(i=10;i>0;i=i-2){
	   for(j=10;j>0;j=j-2){
		window.top.moveBy(0,i);
		window.top.moveBy(i,0);
	   	window.top.moveBy(0,-i);
	   	window.top.moveBy(-i,0);  
	   }
	}
	setTimeout("shake()", 2000);
}

function toaddCustomer(){
	var telphone = ""+eval(${telphone});
	var tel="";
	var officetel="";
	//alert(telphone.length);
	if ( telphone.length == 11 ){
		tel=telphone;
	}else{
		officetel=telphone;
	}
	opener.document.getElementById("main").src="../sales/toAddMemberAction.do?op=call&tel="+tel+"&officetel="+officetel;	
	window.close();
}

function toaddsaleorder2(){
	opener.document.getElementById("main").src="../sales/calledCenterAction.do?cid=${customer.cid}&tel=${telphone}";
	//window.open("../sales/calledCenterAction.do?cid=${customer.cid}&tel=${telphone}","呼叫中心","height=1000,width=1200,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	window.close();
}
</script>
</head>
<html:errors/>
<body onLoad="shake();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 您有新的来电</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>来电信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">来电客户：</td>
          <td width="17%">${customer.cname}</td>
          <td width="13%" align="right">来电号码：</td>
          <td width="20%"><a href="javascript:void(0)" onClick="toaddsaleorder2()">${telphone}</a></td>
	      <td width="12%" align="right">会员级别：</td>
	      <td width="25%">${ratename}</td>
	  </tr>
	  <c:if test="${customer.cname==null}">
	  <tr>
	    <td  colspan="6" align="center"><a href="javascript:void(0)" onClick="toaddCustomer()">此来电客户为新会员：注册新会员</a></td>
	    </tr>
	   </c:if>
	  <tr>
	    <td  align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td><input type="button" value="关闭" onClick="window.close();"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
