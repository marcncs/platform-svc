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
          <td width="772"> 盘点单详情 </td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back">
          <td width="15%"  align="right">盘点仓库：</td>
          <td width="32%">${scf.warehouseidname}</td>
          <td width="14%" align="right">盘点日期：</td>
          <td width="39%">${scf.checkdate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">账面日期：</td>
          <td>${scf.reckondate}</td>
          <td align="right">盘点部门：</td>
          <td>${scf.checkdeptname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否提交：</td>
          <td>${scf.isrefername}</td>
          <td align="right">是否审阅：</td>
          <td>${scf.approvestatusname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">审阅日期：</td>
          <td>${scf.approvedate}</td>
          <td align="right">制单人：</td>
          <td>${scf.makeidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">制单日期：</td>
          <td>${scf.makedate}</td>
          <td align="right">修改人：</td>
          <td>${scf.updateidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">修改日期：</td>
          <td>${scf.lastdate}</td>
          <td align="right">是否完成：</td>
          <td>${scf.iscompletename}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">完成确认人：</td>
          <td>${scf.completeidname}</td>
          <td align="right">完成日期：</td>
          <td>${scf.completedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td>${scf.memo}</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 盘点单产品列表</td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="25%" >产品</td>
          <td width="18%">规格</td>
          <td width="10%">单位</td>
          <td width="18%">批次</td>
          <td width="11%">单价</td>
          <td width="9%">账面数量</td>
          <td width="9%">盘点数量</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr align="center" class="table-back">
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.unitidname}</td>
            <td>${p.batch}</td>
            <td>0.00</td>
            <td>${p.reckonquantity}</td>
            <td>${p.checkquantity}</td>
          </tr>
        </logic:iterate>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="../warehouse/approveStockCheckAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="scid" type="hidden" id="scid" value="${scid}">
            审阅状态：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%"  align="right" valign="top"> 审阅内容：</td>
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
