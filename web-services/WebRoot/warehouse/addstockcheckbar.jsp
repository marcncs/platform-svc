<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<script language=javascript>

function ChkValue(){
	if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
	}
	
	showloading();
	return true;
}

function SelectSingleProduct(){
	var psid=$F("psid");
	var wid=$F("warehouseid");
	var wbit=$F("warehousebit");
	var p=showModalDialog("../warehouse/selectSingleCheckProductAction.do?IsIDCode=1&KeyWordLeft="+psid+"&wid="+wid+"&wbit="+wbit,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.validateProvide.productid.value=p.id;
	document.validateProvide.ProductName.value=p.productname;
	}
	

</script>
</head>

<body style="overflow:auto">

<form name="validateProvide" method="post" action="addStockCheckBarAction.do" onSubmit="return ChkValue();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
<input type="hidden" name="isbar" value="${isbar}">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增库存盘点单</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">盘点仓库：</td>
          <td width="27%"><windrp:warehouse name="warehouseid"/></td>
          <td width="10%" align="right">仓位：</td>
          <td width="22%"><input type="hidden" name="warehousebit" id="warehousebit">
<input type="text" name="bitname" id="bitname" onClick="selectDUW(this,'warehousebit',$F('warehouseid'),'b')" value="请选择" readonly></td>
	      <td width="12%" align="right">&nbsp;</td>
	      <td width="20%">&nbsp;</td>
	  </tr>
      <tr>
	  	<td width="9%"  align="right">产品类别：</td>
          <td width="27%"><input type="hidden" name="psid" id="psid"/>			
			<windrp:pstree id="psid" name="productstruts" /></td>
          <td width="10%" align="right">产品：</td>
          <td width="22%"><input name="productid" type="hidden" id="productid">
              <input id="ProductName" name="ProductName" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
	      <td width="12%" align="right">是否盘点零库存：</td>
	      <td width="20%"><windrp:select key="YesOrNo" name="iszero" p="y|f"/></td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td><input name="memo" type="text" id="memo" value="" size="40" maxlength="120"></td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr>
          <td align="center"><input type="submit" name="Submit"  value="提交">&nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="window.close();"></td>
        </tr>
      </table></td>
  </tr>

</table>
</form>
</body>
</html>
