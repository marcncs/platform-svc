<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language=javascript>
	function CheckInput(){
		var scannerid =document.getElementById("scannerid").value.trim();
		if(scannerid==null||scannerid==""){
			alert("采集器编号不能为空!");
			return false;
		}
		return true;
	}
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增采集器配置</td>
        </tr>
      </table>
	  <form name="referform" method="post" action="../sys/addScannerWarhouseAction.do"  onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	     <td width="10%" height="20" align="right">仓库编号：</td>
          <td width="60%">${warhouseid}</td>
          <c:if test="${type=='ADD'}">
	          <td width="10%" align="right">采集器编号：</td>
	          <td width="40%"><input name="scannerid" type="text" id="scannerid"><span class="text-red3">*</span></td>
	           <td><input name= "type" type="hidden" value="ADD"></td>
          </c:if>
           <c:if test="${type=='EDIT'}">
	          <td width="10%" align="right">采集器编号：</td>
	          <td width="40%"><input name="scannerid" type="text" value="${sw.scannerid}"><span class="text-red3">*</span></td>
	          <td><input name= "type" type="hidden" value="EDIT"></td>
	           <td><input name= "swid" type="hidden" value="${sw.id }"></td>
          </c:if>
          	 <td><input type="hidden" name="warhouseid" value="${warhouseid }"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="28%" > <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; 
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
		 </table>
		 </form>
    </td>
  </tr>
</table>
</body>
</html>
