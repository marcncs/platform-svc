<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<title>WINDRP-分销系统</title>
<script language="javascript">
function Audit(piid){
		showloading();
		popWin2("../warehouse/auditPurchaseIncomeAction.do?PIID="+piid);
	}
	
	function CancelAudit(piid){
			showloading();
			popWin2("../warehouse/cancelAuditPurchaseIncomeAction.do?PIID="+piid);
	}
	
	function Tally(piid){
			showloading();
			popWin2("../warehouse/tallyPurchaseIncomeAction.do?PIID="+piid);
	}
	
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddPurchaseIncomeIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddPurchaseIncomeIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}
	function PrintPurchaseIncome(id){
			popWin("../warehouse/printPurchaseIncomeAction.do?ID="+id,900,600);
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
          <td width="925"> 采购入库单详情 </td>
          <td width="308" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PrintPurchaseIncome('${pif.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${pif.isaudit==0}"><a href="javascript:Audit('${pif.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${pif.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			   <td width="60" align="center"><c:choose>
                  <c:when test="${pif.istally==0}"><a href="javascript:Tally('${pif.id}');">记账</a></c:when>
                  <c:otherwise>取消记账</c:otherwise>
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
	  	<td width="11%"  align="right">供应商：</td>
          <td width="27%">${pif.providename}</td>
          <td width="8%" align="right">仓库：</td>
          <td width="22%"><windrp:getname key='warehouse' value='${pif.warehouseid}' p='d'/></td>
	      <td width="10%" align="right">采购订单号：</td>
	      <td width="22%">${pif.poid}</td>
	  </tr>
	  <tr>
	    <td  align="right">供应商联系人：</td>
	    <td>${pif.plinkman}</td>
	    <td align="right">联系电话：</td>
	    <td>${pif.tel}</td>
	    <td align="right">结算方式：</td>
	    <td><windrp:getname key='paymentmode' value='${pif.paymode}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td  align="right">预计入库日期：</td>
	    <td><windrp:dateformat value='${pif.incomedate}' p='yyyy-MM-dd'/></td>
	    <td  align="right">帐期：</td>
	    <td>${pif.prompt}/天</td>
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
	  	<td width="11%"  align="right">制单机构：</td>
          <td width="26%"><windrp:getname key='organ' value='${pif.makeorganid}' p='d'/></td>
          <td width="8%" align="right">制单人：</td>
          <td width="22%"><windrp:getname key='users' value='${pif.makeid}' p='d'/></td>
	      <td width="11%" align="right">制单日期：</td>
	      <td width="22%">${pif.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${pif.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${pif.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${pif.auditdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否记帐：</td>
	    <td><windrp:getname key='YesOrNo' value='${pif.istally}' p='f'/></td>
	    <td align="right">记帐人：</td>
	    <td><windrp:getname key='users' value='${pif.tallyid}' p='d'/></td>
	    <td align="right">记帐日期：</td>
	    <td>${pif.tallydate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购入库单产品列表</td>
        </tr>
      </table>
	  </legend>
      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="11%">产品编号</td> 
          <td width="25%" >产品名称</td>
          <td width="17%">规格</td>
          <td width="9%">单位</td>
          <td width="5%">序号</td>
          <td width="10%">数量</td>
		  <td width="10%">单价</td>
		  <td width="10%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${pif.id}','${p.id}','${pif.warehouseid}','${pif.isaudit}')" bitclick="toaddbit('${pif.id}','${p.id}','${pif.warehouseid}','${pif.isaudit}')"/></td>
          <td><windrp:format value="${p.quantity}"/></td>
		   <td align="right"><windrp:format value="${p.unitprice}"/></td>
		    <td align="right"><windrp:format value="${p.subsum}"/></td>
          </tr>
        </logic:iterate> 
      </table>
	  </fieldset>


</td>
  </tr>
</table>
</body>
</html>
