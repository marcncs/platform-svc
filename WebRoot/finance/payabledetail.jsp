<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(rid){
			window.open("../finance/auditPayableAction.do?POID="+rid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAudit(rid){
			window.open("../finance/cancelAuditPayableAction.do?POID="+rid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
</script>
</head>

<body>


<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="1004"> 应付款结算单详情 </td>
    <td width="229" align="right">&nbsp;</td>
  </tr>
</table>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="13%"  align="right">应付款结算金额：</td>
          <td width="20%">${paf.payablesum}</td>
          <td width="9%" align="right">单据号：</td>
          <td width="21%">${paf.billno}</td>
          <td width="8%" align="right">到期日：</td>
          <td width="29%"><windrp:dateformat value='${paf.awakedate}' p='yyyy-MM-dd'/>
</td>
	  </tr>
	  <tr>
	  	<td width="13%"  align="right">结算方式：</td>
          <td width="20%"><windrp:getname key='paymentmode' value='${paf.paymode}' p='d'/>
</td>
          <td width="9%" align="right">已付款金额：</td>
          <td width="21%">${paf.alreadysum}</td>
          <td width="8%" align="right">是否关闭：</td>
          <td width="29%"><windrp:getname key='YesOrNo' value='${paf.isclose}' p='f'/></td>
	  </tr>
	  <tr>
	    <td  align="right">描述：</td>
	    <td colspan="5">${paf.payabledescribe}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${paf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${paf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${paf.makeid}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td width="11%"  align="right">制单日期：</td>
          <td width="21%">${paf.makedate}</td>
          <td width="11%" align="right">&nbsp;</td>
          <td width="23%">&nbsp;</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
	
	
</td>
  </tr>
</table>
</body>
</html>
