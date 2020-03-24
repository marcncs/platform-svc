<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(soid){
			popWin2("../machin/auditAssembleRelationAction.do?ID="+soid);
	}
	
	function CancelAudit(soid){
			popWin2("../machin/cancelAuditAssembleRelationAction.do?ID="+soid);
	}
	
	function Prints(soid){
			popWin("../machin/printAssembleRelationAction.do?ID="+soid,900,600);
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
          <td width="958"> 组装关系详情 </td>
          <td width="275" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:Prints('${arf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose><c:when test="${arf.isaudit==0}"><a href="javascript:Audit('${arf.id}');">复核</a></c:when><c:otherwise>
<a href="javascript:CancelAudit('${arf.id}')">取消复核</a></c:otherwise>
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
	  	<td width="9%"  align="right"><input name="id" type="hidden" id="id" value="${arf.id}">
	  	  组装产品编码：</td>
          <td width="28%">${arf.arproductid}</td>
          <td width="12%" align="right">组装产品名称：</td>
          <td width="20%">${arf.arproductname}</td>
	      <td width="9%"  align="right">规格：</td>
	      <td width="22%">${arf.arspecmode}</td>
	  </tr>
	  <tr>
	    <td  align="right">计量单位：</td>
	    <td><windrp:getname key='CountUnit' value='${arf.arunitid}' p='d'/></td>
	    <td align="right">数量：</td>
	    <td>${arf.arquantity}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${arf.remark}</td>
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
	    <td><windrp:getname key='organ' value='${arf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${arf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${arf.makeid}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">制单日期：</td>
          <td width="29%">${arf.makedate}</td>
          <td width="11%" align="right">是否复核：</td>
          <td width="19%"><windrp:getname key='YesOrNo' value='${arf.isaudit}' p='f'/></td>
	      <td width="11%" align="right">复核人：</td>
	      <td width="21%"><windrp:getname key='users' value='${arf.auditid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>${arf.auditdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="80" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>组装材料列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="9%">产品编码</td> 
          <td width="18%" >产品名称</td>
          <td width="16%">规格</td>
          <td width="9%">单位</td>
          <td width="9%">单价</td>
          <td width="8%">数量</td>		 
          <td width="7%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          <td>${p.quantity}</td>		
          <td><fmt:formatNumber value="${p.subsum}" pattern="0.00"/></td>
        </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
