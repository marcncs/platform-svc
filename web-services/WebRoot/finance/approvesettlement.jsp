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
          <td width="772"> 结算单详情 </td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back">
          <td width="14%"  align="right">结算日期：</td>
          <td width="36%">${sf.settlementdate}</td>
          <td width="12%" align="right">结算人：</td>
          <td width="38%">${sf.settlementidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">供应商：</td>
          <td>${sf.providename}</td>
          <td align="right">结算总金额：</td>
          <td>${sf.settlementsum}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td>${sf.remark}</td>
          <td align="right">是否提交：</td>
          <td>${sf.isrefername}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否审阅：</td>
          <td>${sf.approvestatusname}</td>
          <td align="right">审阅日期：</td>
          <td>${sf.approvedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否复核：</td>
          <td>${sf.isauditname}</td>
          <td align="right">复核人：</td>
          <td>${sf.auditidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">复核日期：</td>
          <td>${sf.auditdate}</td>
          <td align="right">是否批准：</td>
          <td>${sf.isratifyname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">批准人：</td>
          <td>${sf.ratifyidname}</td>
          <td align="right">批准日期：</td>
          <td>${sf.ratifydate}</td>
        </tr>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="approveSettlementAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="sid" type="hidden" id="sid" value="${sid}">
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
            <td><input type="submit" name="Submit" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit" value="取消" onClick="javascript:history.back();"></td>
          </tr>
        
      </table>
     </form> 
    </td>
  </tr>
</table>
</body>
</html>
