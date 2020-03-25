<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
/*
	function Audit(piid){
			popWin("../warehouse/auditProductIncomeAction.do?PIID="+piid+"&type=1",900,600);
	}
	
	function CancelAudit(piid){
			showloading();
			popWin2("../warehouse/cancelAuditProductIncomeAction.do?PIID="+piid);
	}

	function ProductIncome(piid){
			popWin("../warehouse/productIncomeAction.do?ID="+piid,900,600);
	}
	*/
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddReceiveIncomeIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	/*
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddProductIncomeIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}
	*/
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="976"> 经销商签收入库单详情 </td>
          <td width="257" align="right">
          <table width="120"  border="0" cellpadding="0" cellspacing="0">
          	<c:if test="${type == '2'}">

	      	</c:if>
	      	<c:if test="${type != '2'}">
	      		<tr>
	              <td width="60" align="center"><c:choose>
	                  <c:when test="${pif.isaudit==0}"><a href="javascript:Audit('${pif.id}');">复核</a></c:when>
	                  <c:otherwise>已复核 </c:otherwise>
	              </c:choose></td>
	             
	            </tr>
	      	</c:if>
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
	  	<td width="9%"  align="right">入货仓库：</td>
          <td width="25%"><windrp:getname key='warehouse' value='${pif.warehouseid}' p='d'/></td>
          <td width="10%" align="right">手工单号：</td>
          <td width="22%">${pif.handwordcode}</td>
	      <td width="9%" align="right">入库类别：</td>
	      <td width="25%"><windrp:getname key='ProductIncomeSort' value='${pif.incomesort}' p='f'/></td>
	  </tr>
	  <tr>
	    <td  align="right">入库日期：</td>
	    <td>${pif.incomedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${pif.remark}</td>
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
	    <td><windrp:getname key='organ' value='${pif.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
        <td><windrp:getname key='dept' value='${pif.makedeptid}' p='d'/></td>
	  	<td width="9%"  align="right">制单人：</td>
        <td width="25%"><windrp:getname key='users' value='${pif.makeid}' p='d'/></td>
        </tr>
	  <tr>
          <td width="10%" align="right">制单日期：</td>
          <td width="22%">${pif.makedate}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%"><windrp:getname key='YesOrNo' value='${pif.isaudit}' p='f'/></td>
	    <td  align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${pif.auditid}' p='d'/></td>
        	  </tr>
	  <tr>
	    <td align="right">复核日期：</td>
	    <td>${pif.auditdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>

	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="120" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>产成品入库单产品列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="6%">产品编号</td> 
          <td width="8%">产品内部编号</td> 
          <td width="23%" >产品名称</td>
          <td width="10%">规格</td>
<%--          <td width="10%">批次</td>--%>
          <td width="3%">序号</td>
<%--          <td width="8%">数量</td>--%>
<%--          <td width="4%">单位</td>--%>
          <td width="7%">箱数</td>
          <td width="8%">散数</td>
<%--          <td width="4%">成本单价</td>--%>
<%--          <td width="4%">成本金额</td>--%>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td>${p.nccode}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
<%--          <td>${p.batch}</td>--%>
          <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${pif.id}','${p.id}','${pif.warehouseid}','${pif.isaudit}')" bitclick="toaddbit('${pif.id}','${p.id}','${pif.warehouseid}','${pif.isaudit}')"/></td>
<%--          <td><windrp:format value="${p.quantity}"/></td>--%>
<%--          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>--%>
          <td>${p.boxNum}</td>
          <td>${p.scatterNum}</td>
<%--          <td align="right"><windrp:format value="${p.costprice}"/></td>--%>
<%--          <td align="right"><windrp:format value="${p.costsum}"/></td>--%>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  
	 

</td>
  </tr>
</table>
</body>
</html>
