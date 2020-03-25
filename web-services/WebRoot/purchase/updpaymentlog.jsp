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
<script language="javascript">
	function CountBalance(){
		var dealsum = document.addform.dealsum.value;
		var paysum = document.addform.paysum.value;
		var owebalance = 0.00;
		owebalance = parseFloat(dealsum)-parseFloat(paysum);
		document.addform.owebalance.value=owebalance;
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
          <td width="772"> 修改付款记录</td>
        </tr>
      </table>
       <form name="addform" method="post" action="updPaymentLogAction.do" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td width="13%"  align="right"><input name="id" type="hidden" id="id" value="${plf.id}">
            收款方 ： </td>
            <td width="33%">              <input name="pname" type="text" id="pname" value="${plf.pname}" readonly> 
            </td><td width="18%" align="right">付款方式 ：  </td>
            <td width="36%">${plf.paymodename} </td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">应付金额： </td>
            <td><input name="dealsum" type="text" id="dealsum" value="${plf.dealsum}" readonly></td>
            <td align="right">本次付款金额： </td>
            <td><input name="paysum" type="text" id="paysum" value="${plf.paysum}" onChange="CountBalance();"></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">付款后余额：</td>
            <td><input name="owebalance" type="text" id="owebalance" value="${plf.owebalance}" readonly></td>
            <td align="right">票据号： </td>
            <td><input name="billnum" type="text" id="billnum3" value="${plf.billnum}"></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right"> 备注

 ：</td>
            <td colspan="3"><textarea name="remark" cols="120" rows="5" id="remark">${plf.remark}</textarea> </td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">&nbsp;</td>
            <td> <input type="submit" name="Submit" value="确定"> <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:history.back();"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
