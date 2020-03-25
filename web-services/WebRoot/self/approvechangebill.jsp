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

<body >


<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 换货单详情 </td>
  </tr>
</table>
      <table width="100%" height="281"  border="0" cellpadding="0" cellspacing="1">

        <tr class="table-back">
          <td  align="right"><input name="cid" type="hidden" id="cid" value="${cb.cid}">
            客户：</td>
          <td>${cb.cname}<a href="javascript:SelectCustomer();">
            <input name="id" type="hidden" id="id" value="${cb.id}">
          </a></td>
          <td  align="right">客户方联系人：</td>
          <td >${cb.clinkman}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">性别：</td>
          <td >${sex}</td>
          <td  align="right">联系电话：</td>
          <td >${cb.tel}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">手机号：</td>
          <td >${cb.mobile}</td>
          <td  align="right">出库单编号：</td>
          <td >${cb.sbid}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">旧货放入仓库：</td>
          <td >${inwarehouse}</td>
          <td  align="right">新货调出仓库：</td>
          <td >${outwarehouse}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">换货数量 ：</td>
          <td >${cb.changecount}</td>
          <td  align="right">差价：</td>
          <td >${cb.pricedifference}</td>
        </tr>
        <tr class="table-back">
          <td  align="right"> 制单人：</td>
          <td >${makename}</td>
          <td  align="right">制单日期：:</td>
          <td >${cb.makedate}</td>
        </tr>
        <tr class="table-back">
          <td  align="right"><input name="srcpid" type="hidden" id="srcpid" value="${cb.srcpid}">
            原产品名： </td>
          <td >${cb.srcpname}<a href="javascript:SelectSrcProduct();"></a></td>
          <td  align="right"><input name="newpid" type="hidden" id="newpid" value="${cb.newpid}">
            新产品名： </td>
          <td >${cb.newpname}<a href="javascript:SelectNewProduct();"></a></td>
        </tr>
        <tr class="table-back">
          <td  align="right"> 原条码/识别码： </td>
          <td >${cb.srcbarcode}</td>
          <td  align="right">新条码/识别码： </td>
          <td >${cb.newbarcode}</td>
        </tr>
        <tr class="table-back">
          <td height="70" align="right"> 换货原因： </td>
          <td height="70">${cb.changecause}</td>
          <td height="70" align="right">备注：</td>
          <td height="70">${cb.memo}</td>
        </tr>
	</table>

      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 审阅信息</td>
  </tr>
</table>
 <form name="addform" method="post" action="approveChangeBillAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td  align="right"><input name="wid" type="hidden" id="wid" value="${wid}">
              审阅状态：</td>
            <td>${approvestatus}  <input name="id" type="hidden" id="id" value="${cb.id}"></td>
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
