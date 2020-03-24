<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Audit(piid){
			popWin("../purchase/auditPurchaseInvoiceAction.do?PIID="+piid,500,250);
	}
	
	function CancelAudit(piid){
			popWin("../purchase/cancelAuditPurchaseInvoiceAction.do?PIID="+piid,500,250);
	}

</script>
<title>WINDRP-分销系统</title>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="926"> 采购发票详情 </td>
          <td width="307" align="right">
		  <table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sbf.isaudit==0}"><a href="javascript:Audit('${sbf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sbf.id}')">取消复核</a></c:otherwise>
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
	  	<td width="10%"  align="right">供应商：</td>
          <td width="22%">${sbf.provideidname}</td>
          <td width="8%" align="right">发票编号：</td>
          <td width="24%">${sbf.invoicecode}</td>
	      <td width="9%" align="right">发票类型：</td>
	      <td width="27%">${sbf.invoicetypename}</td>
	  </tr>
	  <tr>
	    <td  align="right">制票日期：</td>
	    <td><windrp:dateformat value="${sbf.makeinvoicedate}" p="yyyy-MM-dd"/>
	    </td>
	    <td align="right">开票日期：</td>
	    <td><windrp:dateformat value="${sbf.invoicedate}" p="yyyy-MM-dd"/></td>
	    <td align="right">总金额：</td>
	    <td>${sbf.invoicesum}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${sbf.memo}</td>
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
	  	<td width="11%"  align="right">制单人：</td>
          <td width="23%"><windrp:getname key='users' value='${sbf.makeid}' p='d' /></td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%"><windrp:dateformat value="${sbf.makedate}" p="yyyy-MM-dd"/></td>
	      <td width="9%" align="right">修改人：</td>
	      <td width="25%"><windrp:getname key='users' value='${sbf.updateid}' p='d' /></td>
	  </tr>
	  <tr>
	    <td  align="right">最后修改日期：</td>
	    <td><windrp:dateformat value="${sbf.lastdate}" p="yyyy-MM-dd"/></td>
	    <td align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${sbf.isaudit}' p='f' /></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${sbf.auditid}' p='d' /></td>
	    </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>  <windrp:dateformat value="${sbf.auditdate}" p="yyyy-MM-dd"/> </td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购发票产品列表</td>
        </tr>
      </table>
	  </legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="15%">采购订单号</td> 
          <td width="24%" >金额</td>
          <td width="23%">制单日期</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.poid}</td> 
          <td align="right"><windrp:format value="${p.subsum}" p="###,##0.00"/></td>
          <td>${p.makedate}</td>
          </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
