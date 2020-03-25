<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>修改会员级别晋级规则</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/validate.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script language="javascript">
function ChkValue(){
		var startprice = document.mgrform.startprice;
		var endprice = document.mgrform.endprice;
		var startintegral = document.mgrform.startintegral;
		var endintegral = document.mgrform.endintegral;
		
		if(startprice.value.trim()==""){
			alert("首次订单金额不能为空！");
			startprice.select();
			return false;
		}
		if ( !IsNumber(startprice.value) ){
			alert("首次订单金额必须是数字类型！");
			startprice.select();
			return false;
		}
		
		if(endprice.value==""){
			alert("首次订单金额不能为空！");
			endprice.select();
			return false;
		}
		if ( !IsNumber(endprice.value) ){
			alert("首次订单金额必须是数字类型！");
			endprice.select();
			return false;
		}
		
		if(startintegral.value.trim()==""){
			alert("开始积分不能为空！");
			startintegral.select();
			return false;
		}
		if ( !IsNumber(startintegral.value) ){
			alert("开始积分必须是数字类型！");
			startintegral.select();
			return false;
		}
		
		if(endintegral.value.trim()==""){
			alert("结束积分不能为空！");
			endintegral.select();
			return false;
		}
		if ( !IsNumber(endintegral.value) ){
			alert("结束积分必须是数字类型！");
			endintegral.select();
			return false;
		}
		
		showloading();
		return true;
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改会员级别晋级规则</td>
        </tr>
      </table>
	  <form name="mgrform" method="post" action="../sys/updMemberGradeRuleAction.do" onSubmit="return ChkValue();">
	  <input type="hidden" name="id" value="${mgr.id}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%" height="20" align="right">
首次订单金额：</td>
          <td width="25%"><input name="startprice" type="text" id="startprice" size="8" value="<fmt:formatNumber value="${mgr.startprice}" pattern="0.00"/>" onKeyPress="KeyPress(this)" maxlength="10">~<input name="endprice" type="text" id="endprice" size="8" value="<fmt:formatNumber value="${mgr.endprice}" pattern="0.00"/>" onKeyPress="KeyPress(this)" maxlength="10">元
            <font color="#FF0000">*</font></td> 
			<td width="7%" height="20" align="right">积分：</td>
          <td width="25%"><input name="startintegral" type="text" id="startintegral" size="8" value="<fmt:formatNumber value="${mgr.startintegral}" pattern="0.00"/>" onKeyPress="KeyPress(this)" maxlength="10">~<input name="endintegral" type="text" id="endintegral" size="8" value="<fmt:formatNumber value="${mgr.endintegral}" pattern="0.00"/>" onKeyPress="KeyPress(this)" maxlength="10">
            /年
            <font color="#FF0000">*</font></td>       	
			<td width="10%" height="20" align="right">
会员级别：</td>
          <td width="20%"><select name="mgid">
                      <c:forEach var="mg" items="${mglist}">
                        <option value="${mg.id}" ${mg.id==mgr.mgid?"selected":""}>${mg.gradename}</option>
                      </c:forEach>
                    </select>
            <font color="#FF0000">*</font></td>         
	  </tr>
	  
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td width="28%" height="20"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
	  </table></form>
    </td>
  </tr>
</table>
</body>
</html>
