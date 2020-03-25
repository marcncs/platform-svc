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

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 收款单详情 </td>
  </tr>
</table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back">
          <td width="13%"  align="right"> 付款人： </td>
          <td width="33%">${ilf.drawee}</td>
          <td width="18%" align="right">本次收款金额：</td>
          <td width="36%">${ilf.incomesum}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">款项来源： </td>
          <td>${ilf.fundsrcname}</td>
          <td align="right">收款去向：</td>
          <td>${ilf.fundattachname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">票据号：</td>
          <td>${ilf.billnum}</td>
          <td align="right">收款时间：</td>
          <td>${ilf.incomedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否提交：</td>
          <td>${ilf.isrefername}</td>
          <td align="right">审阅状态：</td>
          <td>${ilf.approvestatusname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">审阅日期：</td>
          <td>${ilf.approvedate}</td>
          <td align="right">制单人：</td>
          <td>${ilf.makeidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">制单时间：</td>
          <td>${ilf.makedate}</td>
          <td align="right">是否复核：</td>
          <td>${ilf.isauditname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">复核人：</td>
          <td>${ilf.auditidname}</td>
          <td align="right">复核日期：</td>
          <td>${ilf.auditdate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td colspan="3">${ilf.remark}</td>
        </tr>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
 <form name="addform" method="post" action="../finance/approveIncomeLogAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td  align="right"><input name="ilid" type="hidden" id="ilid" value="${ilid}">
            审阅状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back">
            <td  align="right"><input name="actid" type="hidden" id="actid" value="${actid}">
            动作：</td>
            <td>${stractid}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%"  align="right" valign="top"> 审阅内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right" valign="top">&nbsp;</td>
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
