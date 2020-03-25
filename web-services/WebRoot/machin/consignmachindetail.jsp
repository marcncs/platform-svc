<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(soid){
			window.open("../machin/auditConsignMachinAction.do?ID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function CancelAudit(soid){
			window.open("../machin/cancelAuditConsignMachinAction.do?ID="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
	}
	
	function prints(soid){
			popWin("../machin/printConsignMachinAction.do?ID="+soid,900,600);
	}
	
	function TransPurchasePlan(id){
	window.open("../machin/toConsignMachinTransPurchasePlanAction.do?ID="+id,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}
	
	function TransStuffShipment(id){
	window.open("../machin/toConsignMachinTransConsignStuffShipmentAction.do?id="+id,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
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
          <td > 委外加工单详情 </td>
          <td align="right">
          <table  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:prints('${arf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose><c:when test="${arf.isaudit==0}"><a href="javascript:Audit('${arf.id}');">复核</a></c:when>
              <c:otherwise>
			  <a href="javascript:CancelAudit('${arf.id}')">取消复核</a></c:otherwise>
			  </c:choose></td>
				<td width="80" align="center"><a href="javascript:TransPurchasePlan('${arf.id}');">生成采购计划</a></td>
				<!--<td width="100" align="center"><a href="javascript:TransStuffShipment('${arf.id}')">转委外材料出库</a></td>-->
			   <!-- <td width="60" align="center"><c:choose><c:when test="${arf.isendcase==0}">未结案</c:when><c:otherwise>
已结案</c:otherwise>
</c:choose></td>-->
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
	    <td  align="right">编号：</td>
	    <td>${arf.id}</td>
	    <td align="right">供应商：</td>
	    <td>${arf.pidname}</td>
	    <td  align="right">联系人：</td>
	    <td>${arf.plinkman}</td>
	    </tr>
	  <tr>
	  	<td width="9%"  align="right">联系电话：</td>
          <td width="28%">${arf.tel}</td>
          <td width="12%" align="right">加工产品编码：</td>
          <td width="20%">${arf.cproductid}</td>
	      <td width="12%"  align="right">加工产品名称：</td>
	      <td width="19%">${arf.cproductname}</td>
	  </tr>
	  <tr>
	    <td  align="right">规格：</td>
	    <td>${arf.cspecmode}</td>
	    <td align="right">计量单位：</td>
	    <td>${arf.cunitidname}</td>
	    <td align="right">数量：</td>
	    <td>${arf.cquantity}</td>
	    </tr>
	  <tr>
	    <td  align="right">完成数量：</td>
	    <td>${arf.completequantity}</td>
	    <td align="right">加工费单价：</td>
	    <td>${arf.cunitprice}</td>
	    <td align="right">加工费总金额：</td>
	    <td>${arf.ctotalsum}</td>
	  </tr>
	  <tr>
	    <td  align="right">预计完工日期：</td>
	    <td>${arf.completeintenddate}</td>
	    <td align="right">结算方式：</td>
	    <td>${arf.paymodename}</td>
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
          <td width="19%">${arf.isauditname}</td>
	      <td width="11%" align="right">复核人：</td>
	      <td width="21%">${arf.auditidname}</td>
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
      <table width="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>材料列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="10%">产品编码</td> 
          <td width="19%" >产品名称</td>
          <td width="17%">规格</td>
          <td width="10%">单位</td>
          <td width="9%">单价</td>
          <!--<td width="9%">单价</td>-->
          <td width="8%">数量</td>		 
          <td width="8%">现有库存</td>
          <td width="11%">转材料出库数量</td>
          <td width="8%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          <td>${p.quantity}</td>		
          <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a>${p.stockpile}</td>
		   <td>${p.alreadyquantity}</td>
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
