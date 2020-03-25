<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
function Check(){
	var stockpile = document.affirmform.stockpile.value;
	var prepareout = document.affirmform.prepareout.value;
	
	if(stockpile==""){
		alert("可用库存变更数量不能为空！");
		return false;
	}
	if(prepareout==""){
		alert("预定出库存变更数量不能为空！");
		return false;
	}
	return true;
}
</script>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">调整库存量</td>
        </tr>
      </table>
      	  <form name="affirmform" method="post" action="../warehouse/updProductStockpileAction.do" onSubmit="return Check();">
      <table width="100%" height="73" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="23%"  align="right"><input name="id" type="hidden" id="id" value="${psf.id}">
            可用库存：</td>
            <td width="22%">${psf.stockpile}</td>
            <td width="16%" align="right">变更数量： </td>
            <td width="39%"><input name="stockpile" type="text" id="stockpile" value="0" size="4"></td>
        </tr>
        <tr>
          <td  align="right">预定出库量：</td>
          <td>${psf.prepareout}</td>
          <td align="right">变更数量： </td>
          <td><input name="prepareout" type="text" id="prepareout" value="0" size="4"></td>
        </tr>
        <tr>
          <td align="right">&nbsp;</td>
          <td><input type="submit" name="Submit" value="确定">
              <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
		
      </table>
       </td>
  </tr>
</table>
</body>
</html>
