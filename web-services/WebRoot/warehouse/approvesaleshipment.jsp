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
          <td width="772"> 销售发货单详情 </td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back">
          <td  align="right">出货仓库：</td>
          <td >${sbf.warehousename}</td>
          <td  align="right">销售单号：</td>
          <td >${sbf.soid}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">销售类型：</td>
          <td >${sbf.saletypename}</td>
          <td  align="right">销售部门：</td>
          <td >${sbf.saledeptname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">款项来源：</td>
          <td >${sbf.fundsrcname}</td>
          <td  align="right">收款去向：</td>
          <td >${sbf.fundattachname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">收货单位名称：</td>
          <td >${sbf.receivename}</td>
          <td  align="right">收货人：</td>
          <td >${sbf.linkman}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">联系电话：</td>
          <td >${sbf.tel}</td>
          <td  align="right">发运方式：</td>
          <td >${sbf.transportmodename}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">收货地址：</td>
          <td >${sbf.receiveaddr}</td>
          <td  align="right">需求日期：</td>
          <td ><label>${sbf.requiredate}</label></td>
        </tr>
        <tr class="table-back">
          <td width="17%"  align="right">总金额：</td>
          <td width="25%" >${sbf.totalsum}</td>
          <td width="16%"  align="right">是否提交：</td>
          <td width="42%" >${sbf.isrefername}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否审阅：</td>
          <td >${sbf.approvestatusname}</td>
          <td  align="right">审阅日期：</td>
          <td >${sbf.approvedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">是否记账：</td>
          <td >${sbf.istallyname}</td>
          <td  align="right">记账人：</td>
          <td >${sbf.tallyidname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">记账日期：</td>
          <td >${sbf.tallydate}</td>
          <td  align="right">制单人：</td>
          <td >${sbf.makename}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">制单日期：</td>
          <td >${sbf.makedate}</td>
          <td  align="right">是否已发货：</td>
          <td >${sbf.isshipmentname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">发货人：</td>
          <td >${sbf.shipmentidname}</td>
          <td  align="right">发货日期：</td>
          <td >${sbf.shipmentdate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td >${sbf.remark}</td>
          <td  align="right">&nbsp;</td>
          <td >&nbsp;</td>
        </tr>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 销售发货单产品列表</td>
  </tr>
</table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="32%" >产品名</td>
          <td width="19%">规格</td>
          <td width="11%">单位</td>
          <td width="9%">批次</td>
          <td width="9%">单价</td>
          <td width="9%">数量</td>
          <td width="11%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr align="center" class="table-back">
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.unitidname}</td>
            <td>${p.batch}</td>
            <td>${p.unitprice}</td>
            <td>${p.quantity}</td>
            <td>${p.subsum}</td>
          </tr>
        </logic:iterate>
      </table>
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
<form name="addform" method="post" action="../sales/approveSaleShipmentAction.do">
      <table width="100%" height="208"  border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td  align="right"><input name="sbid" type="hidden" id="sbid" value="${sbid}">
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
