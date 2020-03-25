<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>修改仓库报警设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectSingleProduct(){
	var p=showModalDialog("toSelectSingleProductAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.validateWarehouse.ProductID.value=p.id;
	document.validateWarehouse.ProductName.value=p.productname;
}
function CheckInput(){
	var Safetyl = document.getElementById("Safetyl");
	
	if(Safetyl.value.trim() ==""){
		alert("最低安全库存不能为空!");
		Safetyl.focus();
		return  false;
	}
	
	var Safetyh = document.getElementById("Safetyh");
	
	if(Safetyh.value.trim() ==""){
		alert("最低安全库存不能为空!");
		Safetyh.focus();
		return  false;
	}
	showloading();
	return true;

}
</script>
</head>

<body style="overflow-y: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改仓库报警设置</td>
        </tr>
      </table>
	  <form name="validateWarehouse" method="post" action="../users/updOrganSafetyAction.do" onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"><input name="ID" type="hidden" id="ID" value="${wsf.id}">
产品：</td>
          <td width="21%"><input name="ProductID" type="hidden" id="ProductID" value="${wsf.productid}">
            <input name="ProductName" type="text" id="ProductName" value="${productname}" readonly="readonly"></td>
          <td width="13%" align="right">最低安全库存：</td>
          <td width="23%"><input name="Safetyl" type="text" id="Safetyl" value="${wsf.safetyl}" onClick="javascript:this.select();" onKeyPress="KeyPress(this)"></td>
	      <td width="11%" align="right">最高安全库存：</td>
	      <td width="23%"><input name="Safetyh" type="text" id="Safetyh" value="${wsf.safetyh}" onClick="javascript:this.select();" onKeyPress="KeyPress(this)"></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td width="28%" > <input type="submit" name="Submit" value="确定"> &nbsp;&nbsp;
            <input name="cancel" type="button" id="cancel" value="取消" onClick="window.close();"></td>
          </tr>
        
	  </table></form>
    </td>
  </tr>
</table>
</body>
</html>
