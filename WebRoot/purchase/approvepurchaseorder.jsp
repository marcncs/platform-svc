<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
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
          <td width="772"> 采购订单详情 </td>
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
	  	<td width="9%"  align="right">供应商：</td>
          <td width="21%">${pbf.providename}</td>
          <td width="13%" align="right">联系人：</td>
          <td width="23%">${pbf.plinkman}</td>
	      <td width="10%" align="right">联系电话：</td>
	      <td width="24%">${pbf.tel}</td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td >${pbf.receiveaddr}</td>
		<td width="10%" align="right">预计收货日期：</td>
	      <td width="24%">${pbf.receivedate}</td>
	    <td align="right">批次：</td>
	    <td>${pbf.batch}</td>
	    </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td>${pbf.purchasedeptname}</td>
	    <td align="right">采购人员：</td>
	    <td>${pbf.purchaseidname}</td>
	    <td align="right">付款方式：</td>
	    <td>${pbf.paymentmodename}</td>
	    </tr>
	  <tr>
	    <td  align="right">总金额：</td>
	    <td>${pbf.totalsum}</td>
	    <td align="right">备注：</td>
	    <td colspan="3">${pbf.remark}</td>
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
          <td width="21%">${pbf.makeidname}</td>
          <td width="13%" align="right">制单日期：</td>
          <td width="23%">${pbf.makedate}</td>
	      <td width="9%" align="right">是否提交：</td>
	      <td width="25%">${pbf.isrefername}</td>
	  </tr>
	  <tr>
	    <td  align="right">审阅状况：</td>
	    <td>${pbf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${pbf.approvedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购单产品列表</td>
        </tr>
      </table>
	  </legend>

<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="16%">产品编号</td> 
          <td width="29%" >产品名称</td>
          <td width="27%">规格</td>
          <td width="7%">单位</td>
          <td width="6%">单价</td>
          <td width="6%">数量</td>
		  <td width="6%">入库数量</td>
          <td width="9%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitname}</td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
		  <td>${p.incomequantity}</td>
          <td>${p.subsum}</td>
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
 <form name="addform" method="post" action="approvePurchaseOrderAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
       
			<input name="pbaid" type="hidden" id="pbaid" value="${pbaid}">
          <tr class="table-back"> 
            <td  align="right"><input name="pbid" type="hidden" id="pbid" value="${pbid}">
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
      </fieldset>
    </td>
  </tr>
</table>
</body>
</html>
