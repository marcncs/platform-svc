<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(oid){
			window.open("../finance/auditOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAudit(oid){
			window.open("../finance/cancelAuditOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function Audittwo(oid){
			window.open("../finance/toEndCaseOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAudittwo(oid){
			window.open("../finance/cancelEndcaseOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	function Auditend(oid){
			window.open("../sales/auditendOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAuditend(oid){
			window.open("../sales/cancelAuditendOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
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
    <td width="948"> 费用单详情 </td>
    <td width="285" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="60" align="center"><c:choose>
          <c:when test="${olf.isaudit==0}"><a href="javascript:Audit('${olf.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${olf.id}')">取消复核</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${olf.isendcase==0}"><a href="javascript:Audittwo('${olf.id}')">结款</a></c:when>
          <c:otherwise><a href="javascript:CancelAudittwo('${olf.id}')">取消结款</a></c:otherwise>
        </c:choose></td>        
      </tr>
    </table></td>
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
	  	<td width="9%"  align="right">报销人：</td>
          <td width="21%"><windrp:getname key='users' value='${olf.outlayid}' p='d'/></td>
          <td width="13%" align="right">报销部门：</td>
          <td width="21%"><windrp:getname key='dept' value='${olf.outlaydept}' p='d'/></td>
	      <td width="11%" align="right">核算部门：</td>
	      <td width="25%"><windrp:getname key='dept' value='${olf.castdept}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">核算员：</td>
	    <td><windrp:getname key='users' value='${olf.caster}' p='d'/></td>
	    <td align="right">总费用：</td>
	    <td>${olf.totaloutlay}</td>
	    <td align="right">备注：</td>
	    <td>${olf.remark}</td>
	    </tr>
		<tr>
	    <td  align="right">本次冲借：</td>
	    <td>${olf.thisresist}</td>
	    <td align="right">实付费用：</td>
	    <td>${olf.factpay}</td>
	    <td align="right">付款来源：</td>
	    <td>${olf.fundsrcname}</td>
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
	  	<td width="9%"  align="right">制单人：</td>
          <td width="21%">${olf.makeidname}</td>
          <td width="13%" align="right">制单日期：</td>
          <td width="19%">${olf.makedate}</td>
	      <td width="13%" align="right">是否复核：</td>
	      <td width="25%">${olf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核人：</td>
	    <td>${olf.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${olf.auditdate}</td>
	    <td align="right">是否结款：</td>
	    <td>${olf.isendcasename}</td>
	    </tr>
	  <tr>
	    <td  align="right">结款人：</td>
	    <td>${olf.endcaseidname}</td>
	    <td align="right">结款日期：</td>
	    <td>${olf.endcasedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>	  
	  </table>
	</fieldset>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>费用明细</td>
        </tr>
      </table>
	  </legend>
	  <FORM METHOD="POST" name="listform" ACTION="">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
      
        <tr align="center" class="title-top">
          <td width="305" >费用类别</td>
          <td >凭证号</td>
          <!--<td width="135" >相关客户ID</td>-->
          <td >备注</td>
          <td >费用金额</td>
        </tr>
        <logic:iterate id="d" name="als">
          <tr class="table-back-colorbar">
            <td >${d.outlayprojectidname}</td>
            <td width="707">${d.voucher}</td>
            <td width="707">${d.remark}</td>
            <td width="227">${d.outlaysum}</td>
          </tr>
        </logic:iterate>
      
    </table>
    </form>
	</fieldset>
	
	

    </td>
  </tr>
</table>
</body>
</html>
