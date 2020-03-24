<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<script language="javascript">

	function CheckedObj(obj){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}
	

	function Audit(id){
			popWin("../ditch/auditOrganInvoiceAction.do?ID="+id,500,250);
	}
	
	function CancelAudit(id){
		popWin("../ditch/cancelAuditOrganInvoiceAction.do?ID="+id,500,250);
	}
	
	
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 渠道发票详情 </td>
    <td align="right"><table   border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td style="float: right; padding-right: 20px;">
     	<c:choose>
          <c:when test="${ama.isaudit==0}"><a href="javascript:Audit('${ama.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${ama.id }')">取消复核</a></c:otherwise>
        </c:choose>
       </td>
      
      </tr>
    </table></td>
  </tr>
</table>

<fieldset align="center"> 
<legend>基本信息
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="10%" align="right">编号：</td>
        <td width="20%">${ama.id}</td>
        <td width="10%"  align="right">收支类型：</td>
	    <td width="20%"><windrp:getname key="InOrOut" p="f" value="${ama.inorout}"/></td>
	    <td width="10%" align="right">机构：</td>
	    <td><windrp:getname key="organ" p="d" value="${ama.organid}" /></td>
	  </tr>
	  <tr>
	  <td align="right">发票编号：</td>
	    <td >${ama.invoicecode }</td>
	    <td  align="right">发票类型：</td>
          <td >
          	<windrp:getname key="InvoiceType" p="f" value="${ama.invoicetype}"/>
          </td>
	    <td align="right">发票内容：</td>
	    <td >${ama.invoicecontent }</td>
	  </tr>
	   <tr>
	    <td  align="right">制票日期：</td>
	    <td><windrp:dateformat value="${ama.makeinvoicedate}" p="yyyy-MM-dd"/> </td>
	    <td align="right">开票日期：</td>
	    <td ><windrp:dateformat value="${ama.invoicedate}" p="yyyy-MM-dd"/></td>
	    <td align="right">发票抬头：</td>
	    <td >${ama.invoicetitle }</td>
	  </tr>
	   <tr>
	    <td  align="right">寄票地址：</td>
	    <td colspan="3">${ama.sendaddr }</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${ama.memo}</td>
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
	  	<td width="10%" align="right">是否复核：</td>
          <td width="20%"><windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="20%"><windrp:getname key='users' value='${ama.auditid}' p='d'/></td>
	      <td width="10%" align="right">复核日期：</td>
	      <td ><windrp:dateformat value='${ama.auditdate}' p='yyyy-MM-dd'/></td>
	  </tr>
	  <tr>
	    <td align="right">修改人：</td>
	    <td><windrp:getname key='users' value='${ama.updateid}' p='d'/></td>
	    <td align="right">最后修改日期：</td>
	    <td><windrp:dateformat value='${ama.lastdate}' p='yyyy-MM-dd'/>
	    </td>
	    <td  align="right"></td>
	    <td></td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
        <tr>
          <td>制单信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="10%" align="right">制单机构：</td>
          <td width="20%"><windrp:getname key='organ' value='${ama.makeorganid}' p='d'/></td>
          <td width="10%" align="right">制单部门：</td>
          <td width="20%"><windrp:getname key='dept' value='${ama.makedeptid}' p='d'/></td>
          <td>&nbsp;</td><td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${ama.makeid}' p='d'/></td>
	    <td align="right">制单日期：</td>
	    <td><windrp:dateformat value='${ama.makedate}' p='yyyy-MM-dd'/>
	    </td>
	    <td>&nbsp;</td><td>&nbsp;</td>
	    </tr>
	 
	  </table>
	</fieldset>
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>订购单列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td >编号</td> 
          <td >单据编号</td>
          <td >金额</td>
          <td >制单日期</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar" onClick="CheckedObj(this);">
          <td>${p.id}</td> 
          <td >${p.poid}</td>
          <td align="right"><fmt:formatNumber value='${p.subsum}' pattern='0.00'/></td>
          <td><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/></td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  </td>
  </tr>
</table>
</body>
</html>
