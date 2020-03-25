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
				window.open("../aftersale/auditSaleRepairAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	
	function CancelAudit(piid){
			window.open("../aftersale/cancelAuditSaleRepairAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Endcase(piid){
				window.open("../aftersale/backTrackSaleRepairAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	
	function CancelEndcase(piid){
			window.open("../aftersale/cancelBackTrackSaleRepairAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function PurchaseIncome(piid){
			window.open("../warehouse/purchaseIncomeAction.do?ID="+piid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(pidid, batch, piid){
			window.open("../aftersale/toAddSaleRepairIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function blankout(wid){
		if(window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")){
			window.open("../aftersale/blankoutSaleRepairAction.do?id="+wid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
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
          <td width="925"> 零售返修详情 </td>
          <td width="308" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PurchaseIncome('${sof.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isaudit==0}"><a href="javascript:Audit('${sof.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sof.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			   <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isbacktrack==0}"><a href="javascript:Endcase('${sof.id}');">返还</a></c:when>
                  <c:otherwise> <a href="javascript:CancelEndcase('${sof.id}')">取消返还</a></c:otherwise>
              </c:choose></td>
			  <td width="60" align="center">
                  <c:if test="${sof.isblankout==0}"><a href="javascript:blankout('${sof.id}');">作废</a></c:if>
                  </td>
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
	  	<td width="11%"  align="right">客户名称：</td>
          <td width="27%">${sof.cname}</td>
          <td width="10%" align="right">联系人：</td>
          <td width="20%">${sof.clinkman}</td>
	      <td width="10%" align="right">联系电话：</td>
	      <td width="22%">${sof.tel}</td>
	  </tr>
	  <tr>
	  	<td  align="right">入货仓库：</td>
	    <td>${sof.warehouseinidname}</td>
	    <td  align="right">出货仓库：</td>
	    <td>${sof.warehouseoutidname}</td>
	    <td align="right">预计取货日期：</td>
	    <td>${sof.tradesdate}</td>
	    </tr>
		<tr>
	  	<td  align="right">金额：</td>
	    <td>${sof.totalsum}</td>
	    <td  align="right">备注：</td>
	    <td colspan="3">${sof.remark}</td>
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
          <td width="26%">${sof.makeidname}</td>
          <td width="8%" align="right">制单日期：</td>
          <td width="22%">${sof.makedate}</td>
	      <td width="11%" align="right">&nbsp;</td>
	      <td width="22%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td>${sof.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${sof.auditidname}</td>
	    <td align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否返还：</td>
	    <td>${sof.isbacktrackname}</td>
	    <td align="right">返还人：</td>
	    <td>${sof.backtrackidname}</td>
	    <td align="right">返还日期：</td>
	    <td>${sof.backtrackdate}</td>
	    </tr>
	<tr>
	    <td  align="right">是否作废：</td>
	    <td>${sof.isblankoutname}</td>
	    <td align="right">作废人：</td>
	    <td>${sof.blankoutidname}</td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>返修物品清单列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="9%">产品编号</td> 
          <td width="20%" >产品名称</td>
          <td width="14%">规格</td>
          <td width="9%">单位</td>
          <td width="4%">序号</td>
          <td width="8%">批次</td>
		  <td width="6%">单价</td>
          <td width="8%">数量</td>
		  <td width="6%">金额</td>
		  	 
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td><a href="javascript:toaddidcode(${p.id},'${p.batch}','${sof.id}')"><img src="../images/CN/record.gif" width="19"  border="0" title="录入序号"></a></td>
          <td>${p.batch}</td>
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
