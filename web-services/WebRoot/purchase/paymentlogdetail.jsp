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

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 付款记录详情</td>
        </tr>
      </table>
      <form name="addform" method="post" action="updPaymentLogAction.do" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td width="13%"  align="right"> 收款方 ： </td>
            <td width="33%">${plf.pname}</td>
            <td width="18%" align="right">付款方式 ： </td>
            <td width="36%">${plf.paymodename} </td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">待付金额： </td>
            <td>${plf.dealsum}</td>
            <td align="right">本次付款金额： </td>
            <td><c:if test="${plf.approvestatus==2}"><del>${plf.paysum}</del></c:if></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">付款后余额：</td>
            <td>${plf.owebalance}</td>
            <td align="right">票据号： </td>
            <td>${plf.billnum}</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">申请人：</td>
            <td>${plf.createuser}</td>
            <td align="right">是否审阅：</td>
            <td>${plf.approvename}</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">是否完成：</td>
            <td>${plf.paystatusname}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right"> 备注 ：</td>
            <td colspan="3">${plf.remark}</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">&nbsp;</td>
            <td> <input name="cancel" type="button" id="cancel" value="返回" onClick="javascript:history.back();"></td>
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
