<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
function Check(){
	var adjustsum = document.validateCustomer.v.value;
	
	if(adjustsum==""){
		alert("调整金额不能为空！");
		return false;
	}
	return true;
}

	function Audit(id){
			window.open("../finance/auditCashBankAdjustAction.do?ID="+id,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(id){
			window.open("../finance/cancelAuditCashBankAdjustAction.do?ID="+id,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="15"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="918"> 现金银行调整详情</td>
        <td width="310" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" align="center"><c:choose>
                <c:when test="${lf.isaudit==0}"><a href="javascript:Audit('${lf.id}');">复核</a></c:when>
                <c:otherwise> <a href="javascript:CancelAudit('${lf.id}')">取消复核</a></c:otherwise>
            </c:choose></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="12%"  align="right">现金银行名称：</td>
          <td width="21%">${lf.cbidname}</td>
          <td width="10%" align="right">调整金额：</td>
          <td width="20%"><windrp:format value='${lf.adjustsum}'/></td>
	      <td width="14%" align="right">&nbsp;</td>
	      <td width="23%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${lf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${lf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td>${lf.makeidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">制单日期：</td>
	    <td>${lf.makedate}</td>
	    <td align="right">是否复核：</td>
	    <td>${lf.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${lf.auditidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>${lf.auditdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${lf.memo}</td>
	    </tr>
	  </table>
	</fieldset>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
