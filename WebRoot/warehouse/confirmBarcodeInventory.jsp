<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>条码盘点审核</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/jquery-1.11.1.min.js"></script>
<script language="JavaScript">

function disApprove(){

	var reason = $("#reason").val();
	if(reason.trim()=="") {
		alert("请输入原因");
		return;
	}
	$("#isapprove").val(2);
	showloading();
	document.referForm.submit();

}
	
function approve(){
	$("#isapprove").val(1);
	showloading();
	document.referForm.submit();

}
	
</script>
</head>

<body style="overflow: auto;">

<form name="referForm" id="referForm" method="post" action="approveBarcodeInventoryAction.do">
<input name="id" type="hidden" id="id" value="${ai.id}">
<input name="isapprove" type="hidden" id="isapprove" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 条码盘点审核</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	    <tr>
          <td width="13%"  align="right">
            	盘点机构:</td>
	      <td width="25%">${oname}</td>
	      <td width="9%" align="right"> 盘点仓库：</td>
	      <td width="24%">${wname}</td>
	    </tr>
	      <tr>
			<td align="right">
				不通过原因：
			</td>
			<td  colspan="6">
				<textarea id="reason" name="reason" rows="2" cols="100"></textarea>
			</td>
			</tr>
	  </table>
	</fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="button" name="approveB"  value="通过" onClick="javascript:approve();">&nbsp;&nbsp;
          <input type="button" name="disAppoveB" value="不通过" onClick="javascript:disApprove();"></td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</form>
</body>
</html>
