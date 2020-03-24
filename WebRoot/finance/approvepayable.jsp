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
          <td width="772"> 应付款详情 </td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back">
          <td width="17%"  align="right">应付款金额：</td>
          <td width="33%">${paf.payablesum}</td>
          <td width="16%" align="right">&nbsp;</td>
          <td width="34%">&nbsp;</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否提交：</td>
          <td>${paf.isrefername}</td>
          <td align="right">是否审阅：</td>
          <td>${paf.approvestatusname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">审阅日期：</td>
          <td>${paf.approvedate}</td>
          <td align="right">是否复核：</td>
          <td>${paf.isauditname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">复核人：</td>
          <td>${paf.auditidname}</td>
          <td align="right">复核日期：</td>
          <td>${paf.auditdate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">制单人：</td>
          <td>${paf.makeidname}</td>
          <td align="right">制单日期：</td>
          <td>${paf.makedate}</td>
        </tr>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="approvePayableAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="paid" type="hidden" id="paid" value="${paid}">
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
