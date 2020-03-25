<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(piid){
		showloading();
		popWin2("../aftersale/auditWithdrawAction.do?ID="+piid);
	}
	
	function CancelAudit(piid){
		showloading();
		popWin2("../aftersale/cancelAuditWithdrawAction.do?ID="+piid);
	}
	
	function blankout(wid){
		if(window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")){
			window.open("../aftersale/blankoutWithdrawAction.do?id="+wid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
	}
	
	function PrintWithdraw(id){
			popWin("printSaleWithdrawAction.do?ID="+id,900,600);
	}
	
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddWithdrawIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddWithdrawIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
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
          <td width="925"> 零售退货详情 </td>
          <td width="308" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PrintWithdraw('${sof.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isaudit==0}"><a href="javascript:Audit('${sof.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sof.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			  <td width="60" align="center"><c:choose><c:when test="${sof.isblankout==0}"><a href="javascript:blankout('${sof.id}');">作废</a></c:when><c:otherwise>
<font color="#FF0000">已作废</font></c:otherwise>
</c:choose>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">会员编号：</td>
          <td width="27%">${sof.cid}</td>
          <td width="8%" align="right">会员名称：</td>
          <td width="22%">${sof.cname}</td>
	      <td width="10%" align="right">联系人：</td>
	      <td width="22%">${sof.clinkman}</td>
	  </tr>
	  <tr>
	    <td  align="right">会员手机：</td>
	    <td>${sof.cmobile}</td>
	    <td  align="right">联系电话：</td>
	    <td>${sof.tel}</td>
	    <td align="right">相关单据号：</td>
	    <td>${sof.billno}</td>
	    </tr>
	  <tr>
	  	<td  align="right">入货仓库：</td>
	    <td><windrp:getname key='warehouse' value='${sof.warehouseid}' p='d'/></td>
	    <td  align="right">总金额：</td>
	    <td>${sof.totalsum}</td>
	    <td align="right">结算方式：</td>
	    <td><windrp:getname key='paymentmode' value='${sof.paymentmode}' p='d'/></td>
	    </tr>
	  <tr>
	  	<td  align="right">退货类型：</td>
	    <td><windrp:getname key='WithdrawSort' value='${sof.withdrawsort}' p='f'/></td>
	    <td  align="right">打印次数：</td>
	    <td>${sof.printtimes}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">退货原因：</td>
	    <td colspan="5">${sof.withdrawcause}</td>
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
          <td width="26%"><windrp:getname key='organ' value='${sof.makeorganid}' p='d'/></td>
          <td width="8%" align="right">制单人：</td>
          <td width="22%"><windrp:getname key='users' value='${sof.makeid}' p='d'/></td>
	      <td width="11%" align="right">制单日期：</td>
	      <td width="22%">${sof.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${sof.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    </tr>
	<tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${sof.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>退货物品清单列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="8%">产品编号</td> 
          <td width="18%" >产品名称</td>
          <td width="12%">规格</td>
          <td width="8%">单位</td>
          <td width="3%">序号</td>
          <td width="7%">单价</td>
          <td width="8%">税后单价</td>
          <td width="7%">数量</td>
		  <td width="12%">折扣率</td>
		  <td width="5%">税率</td>
          <td width="12%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${sof.id}','${p.id}','${sof.warehouseid}','${sof.isaudit}')" bitclick="toaddbit('${sof.id}','${p.id}','${sof.warehouseid}','${sof.isaudit}')"/></td>
          <td align="right"><windrp:format value="${p.unitprice}" p="###,##0.00"/></td>
          <td align="right"><windrp:format value="${p.taxunitprice}" p="###,##0.00"/></td>
          <td align="right"><windrp:format value="${p.quantity}" p="###,##0.00"/></td>
		  <td align="right">${p.discount}%</td>
		  <td align="right">${p.taxrate}%</td>
          <td align="right"><windrp:format value="${p.subsum}" p="###,##0.00"/></td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>


</td>
  </tr>
</table>
</body>
</html>
