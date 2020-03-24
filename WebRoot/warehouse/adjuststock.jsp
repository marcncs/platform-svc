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
<script language="JavaScript">
arr = new Array();
	<c:set var="temp" value="0"/>
		<logic:iterate id="ps" name="alpls">
			arr[${temp}] = new Array("${ps.stockpile}","${ps.warehouseid}");
			<c:set var="temp" value="${temp+1}"/>
		</logic:iterate>
	temp=${temp};
	
	function getCurrentStockpile(oSelect){
		for(i=0;i<temp;i++){ 
       		if(arr[i][1]==oSelect.value){
         	document.validateStockAdjust.beforequantity.value=arr[i][0];
       } 
     }
	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 库存调整</td>
        </tr>
      </table>
       <form name="validateStockAdjust" method="post" action="adjustStockAction.do" onSubmit="return validateValidateStockAdjust(this);">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td width="12%"  align="right">
		<input name="pid" type="hidden" id="pid" value="${pid}">
            请选择仓库：</td>
            <td width="35%"> 
			<select name="warehouseid" id="warehouseid" onChange="getCurrentStockpile(this);">
				<option value="">请选择仓库</option>
                <logic:iterate id="w" name="alw" > 
				<option value="${w.id}">${w.warehousename}</option>
                </logic:iterate> </select></td>
            <td width="18%" align="right">当前数量：</td>
            <td width="35%"><input name="beforequantity" type="text" id="beforequantity" readonly></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">操作类型：</td>
            <td><select name="operatesign" id="select">
<option value="+" selected>增加</option>
                <option value="-">减少</option>
              </select></td>
            <td align="right">调整数量：</td>
            <td><input name="adjustquantity" type="text" id="adjustquantity"></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">调整原因：</td>
            <td><textarea name="adjustcause" cols="40" rows="4" id="adjustcause"></textarea></td>
            <td align="right">备注：</td>
            <td><textarea name="remark" cols="40" rows="4" id="remark"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">&nbsp;</td>
            <td> <input type="submit" name="Submit" value="确定"> <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        <html:javascript formName="validateStockAdjust"/>
      </table> 
      </form>
      </td>
  </tr>
</table>
</body>
</html>
