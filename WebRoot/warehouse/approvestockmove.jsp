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
          <td width="772"> 调拨单详情 </td>
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
	  	<td width="9%"  align="right">调拨日期：</td>
          <td width="25%">${smf.movedate}</td>
          <td width="9%" align="right">调出仓库：</td>
          <td width="23%">${smf.outwarehouseidname}</td>
	      <td width="9%" align="right">调入仓库：</td>
	      <td width="25%">${smf.inwarehouseidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">总金额：</td>
	    <td>${smf.totalsum}</td>
	    <td align="right">调拨原因：</td>
	    <td colspan="3">${smf.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${smf.remark}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="25%">${smf.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${smf.makedate}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%">${smf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${smf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${smf.auditdate}</td>
	    <td align="right">是否发货：</td>
	    <td>${smf.isshipmentname}</td>
	    </tr>
	  <tr>
	    <td  align="right">发货人：</td>
	    <td>${smf.shipmentidname}</td>
	    <td align="right">发货日期：</td>
	    <td>${smf.shipmentdate}</td>
	    <td align="right">是否签收：</td>
	    <td>${smf.iscompletename}</td>
	    </tr>
	  <tr>
	    <td  align="right">签收人：</td>
	    <td>${smf.receiveidname}</td>
	    <td align="right">签收日期：</td>
	    <td>${smf.receivedate}</td>
	    <td align="right">是否提交：</td>
	    <td>${smf.isrefername}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否审阅：</td>
	    <td>${smf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${smf.approvedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>调拨单产品列表</td>
        </tr>
      </table>
	  </legend>

      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="15%">产品编号</td>
          <td width="18%" >产品名称</td>
          <td width="21%">规格</td>
          <td width="8%">单位</td>
          <td width="14%">批次</td>
          <td width="8%">单价</td>
          <td width="7%">数量</td>
          <td width="9%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back">
            <td>${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.unitidname}</td>
            <td>${p.batch}</td>
            <td>0.00</td>
            <td>${p.quantity}</td>
            <td>0.00</td>
          </tr>
        </logic:iterate>
      </table>
	  </fieldset>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审阅信息</td>
        </tr>
      </table>
	  </legend>
 <form name="addform" method="post" action="../warehouse/approveStockMoveAction.do">
      <table width="100%" height="208"  border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td  align="right"><input name="smid" type="hidden" id="smid" value="${smid}">
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
            <td height="18" align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
        
      </table>
      </form>
      </fieldset>
    </td>
  </tr>
</table>
</body>
</html>
