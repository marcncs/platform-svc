<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script language="javascript">
function formChk(){
	var productid = document.validateProduct.ProductID.value;
	var productprice = document.validateProduct.productprice.value.trim();
	var starttime = document.validateProduct.starttime.value;
	var endtime = document.validateProduct.endtime.value;	
	var memo = document.validateProduct.memo.value;
	if(productid==""){
		alert("请选择产品！");
		return false;
	}
	if(productprice==""){
		alert("请输入产品价格！");
		return false;
	}
	if(isNaN(productprice)){
		alert("产品价格只能为数字！");
		return false;
	}
	if(parseInt(productprice)<0){
		alert("产品价格必须大于0！");
		return false;
	}
	if(starttime==""){
		alert("请输入开始日期！");
		return false;
	}
	
	if(endtime==""){
		alert("请输入结束日期！");
		return false;
	}
	
	if(starttime>endtime){
		alert("开始日期必须小于结束日期！");
		return false;
	}
	showloading();
	return true;
 }
 
 //选择产品
	function SelectSingleProduct(){
		var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.validateProduct.ProductID.value=p.id;
		document.validateProduct.ProductName.value=p.productname;
		document.validateProduct.unitId.value = p.unitid;
	}

</script>

<body style="overflow:auto">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 系统设置>>修改产品价格</td>
      </tr>
    </table>
        <form action="updProductPriceHistoryAction.do" method="post"  name="validateProduct" onSubmit="return formChk();">
		<input type="hidden" name="pphid" value="${pph.id }" />
         <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="12%"  align="right">产品：</td>
              <td width="20%">
			  <input name="productId" type="hidden" id="ProductID" value="${pph.productId}">
           <input id="ProductName" size="27" name="productName" dataType="Require" msg="产品不能为空!" value="${pph.productName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a>
			<span class="style1">*</span></td>
             <td align="right">最小计量单位：</td>
			   <td><windrp:select key="CountUnit" name="unitId" value="${pph.unitId}" p="n|d"/></td>
              <td width="12%" align="right">产品价格：</td>
              <td width="23%">
			   <input name="productprice" type="text" id="productprice" value="${pph.unitPrice }" size="30" maxlength="128">
              <span class="style1">* </span></td>
            </tr>
			 <tr>
			   <td height="20" align="right">日期：</td>
			   <td width="23%"><input name="starttime" type="text" id="starttime" value="<windrp:dateformat value='${pph.startTime}' p='yyyy-MM-dd'/>"  size="10" onFocus="javascript:selectDate(this)" readonly> - <input name="endtime" type="text" id="endtime" value="<windrp:dateformat value='${pph.endTime}' p='yyyy-MM-dd'/>" size="10" onFocus="javascript:selectDate(this)" readonly>
		     	 <span class="style1">* </span></td>
		      </tr>
			  </table>
		  </fieldset>
			  
			  <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>辅助信息</td>
            </tr>
          </table>
		  </legend>
			  
			  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
             <td  align="right" valign="top">备注：</td>
             <td><textarea name="memo" cols="120" rows="10"  id="memo" dataType="Limit" max="512"  msg="备注必须在512个字之内" require="false">${pph.memo }</textarea><br><span class="td-blankout">(备注长度不能超过512字符)</span></td>
             </tr>
          </table>
		  </fieldset>

          <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close()">
              </div></td>
            </tr>
          </table>
        </form>
 </td>
</tr>
</table>
`
</body>
</html>
