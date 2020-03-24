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
          <td width="772"> 零售单详情 </td>
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
	  	<td width="9%"  align="right">客户名：</td>
          <td width="21%">${sof.cname}</td>
          <td width="13%" align="right">客户方单据编号：</td>
          <td width="23%">${sof.customerbillid}</td>
	      <td width="9%" align="right">零售类型：</td>
	      <td width="25%">${sof.saletypename}</td>
	  </tr>
	  <tr>
	    <td  align="right">零售部门：</td>
	    <td>${sof.saledeptname}</td>
	    <td align="right">款项来源：</td>
	    <td>${sof.fundsrcname}</td>
	    <td align="right">收款去向：</td>
	    <td>${sof.fundattachname}</td>
	    </tr>
	  <tr>
	    <td  align="right">发运方式：</td>
	    <td>${sof.transportmodename}</td>
	    <td align="right">发货地址：</td>
	    <td colspan="3">${sof.transportaddr}</td>
	    </tr>
	  <tr>
	    <td  align="right">收货人：</td>
	    <td>${sof.receiveman}</td>
	    <td align="right">联系电话：</td>
	    <td>${sof.tel}</td>
	    <td align="right">交货日期：</td>
	    <td>${sof.consignmentdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">总金额：</td>
	    <td>${sof.totalsum}</td>
	    <td align="right">备注：</td>
	    <td colspan="3">${sof.remark}</td>
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
          <td width="21%">${sof.makename}</td>
          <td width="13%" align="right">制单日期：</td>
          <td width="23%">${sof.makedate}</td>
	      <td width="9%" align="right">修改人：</td>
	      <td width="25%">${sof.updatename}</td>
	  </tr>
	  <tr>
	    <td  align="right">最后修改日期：</td>
	    <td>${sof.lastupdate}</td>
	    <td align="right">是否复核：</td>
	    <td>${sof.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${sof.auditidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    <td align="right">是否批准：</td>
	    <td>${sof.isaudittwoname}</td>
	    <td align="right">批准人：</td>
	    <td>${sof.audittwoidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">批准日期：</td>
	    <td>${sof.audittwodate}</td>
	    <td align="right">是否提交：</td>
	    <td>${sof.isrefername}</td>
	    <td align="right">是否审阅：</td>
	    <td>${sof.approvestatusname}</td>
	    </tr>
	  <tr>
	    <td  align="right">审阅日期：</td>
	    <td>${sof.approvedate}</td>
	    <td align="right">订单状态：</td>
	    <td>${sof.orderstatusname}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>零售单产品列表</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td> 
          <td width="25%" >产品名称</td>
          <td width="28%">规格</td>
          <td width="8%">单位</td>
          <td width="8%">单价</td>
          <td width="7%">数量</td>
          <td width="11%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
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
	   <form name="addform" method="post" action="../sales/approveSaleOrderAction.do">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td  align="right"><input name="soid" type="hidden" id="soid" value="${soid}">
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
