<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 付款单详情 </td>
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
            <td>${plf.paysum}</td>
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
       
      </table>
       </form>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="approvePaymentLogAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="plid" type="hidden" id="plid" value="${plid}">
            审阅状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%" align="right" valign="top"> 审阅内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td height="18" align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
