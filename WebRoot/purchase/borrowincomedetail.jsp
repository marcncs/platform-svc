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
<script language="javascript">
	function Audit(piid){
			window.open("../purchase/auditBorrowIncomeAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(piid){
			window.open("../purchase/cancelAuditBorrowIncomeAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function BackTrack(piid){
			window.open("../purchase/backTrackBorrowIncomeAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelBackTrack(piid){
			window.open("../purchase/cancelBackTrackBorrowIncomeAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function PurchaseIncome(piid){
			window.open("../purchase/purchaseIncomeAction.do?ID="+piid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(pidid, batch, piid){
			window.open("../purchase/toAddBorrowIncomeIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	
</script>

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
          <td width="925"> 借入单详情 </td>
          <td width="308" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PurchaseIncome('${pif.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${pif.isaudit==0}"><a href="javascript:Audit('${pif.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${pif.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			   <td width="60" align="center"><c:choose>
                  <c:when test="${pif.isbacktrack==0}"><a href="javascript:BackTrack('${pif.id}');">返还</a></c:when>
                  <c:otherwise> <a href="javascript:CancelBackTrack('${pif.id}')">取消返还</a></c:otherwise>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">入货仓库：</td>
          <td width="27%">${pif.warehouseidname}</td>
          <td width="9%" align="right">出货仓库：</td>
          <td width="21%">${pif.warehouseoutname}</td>
	      <td width="10%" align="right">采购订单号：</td>
	      <td width="22%">${pif.poid}</td>
	  </tr>
	  <tr>
	    <td  align="right">客户：</td>
	    <td>${pif.cname}</td>
	    <td align="right">客户联系人：</td>
	    <td>${pif.plinkman}</td>
	    <td align="right">联系电话：</td>
	    <td>${pif.tel}</td>
	    </tr>
	  <tr>
	  	<td  align="right">批次：</td>
	    <td>${pif.batch}</td>
	    <td  align="right">预计入库日期：</td>
	    <td>${pif.incomedate}</td>
	    <td align="right">总金额：</td>
	    <td>${pif.totalsum}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">制单人：</td>
          <td width="26%">${pif.makeidname}</td>
          <td width="8%" align="right">制单日期：</td>
          <td width="22%">${pif.makedate}</td>
	      <td width="11%" align="right">&nbsp;</td>
	      <td width="22%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td>${pif.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${pif.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${pif.auditdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否返还：</td>
	    <td>${pif.isbacktrackname}</td>
	    <td align="right">复核人：</td>
	    <td>${pif.backtrackidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${pif.backtrackdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否转退货：</td>
	    <td>${pif.istranswithdrawname}</td>
	    <td align="right">复核人：</td>
	    <td>${pif.transwithdrawidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${pif.transwithdrawdate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>借入单产品列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="11%">产品编号</td> 
          <td width="25%" >产品名称</td>
          <td width="17%">规格</td>
          <td width="9%">单位</td>
          <td width="5%">序号</td>
          <td width="13%">单价</td>
          <td width="10%">数量</td>
          <td width="10%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td><a href="javascript:toaddidcode(${p.id},'${pif.batch}','${pif.id}')"><img src="../images/CN/record.gif" width="19"  border="0" title="录入序号"></a></td>
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
          <td>${p.subsum}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>


</td>
  </tr>
</table>
</body>
</html>
