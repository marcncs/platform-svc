<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="javascript">
	function Audit(sbid){
			showloading();
			window.open("../warehouse/auditShipmentBillAction.do?SBID="+sbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(sbid){
			showloading();
			window.open("../warehouse/cancelAuditShipmentBillAction.do?SBID="+sbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	/*
	function ShipmentBill(sbid){
			window.open("../warehouse/shipmentBillAction.do?ID="+sbid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}*/
	
	function BlankOut(sbid){
		if(window.confirm("您确认要作废所选的记录吗？如果作废将永远不能恢复!")){			
			window.open("../warehouse/toBlankoutShipmentBillAction.do?id="+sbid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
	
	function PrintShipmentBill(id){
			window.open("printShipmentBillAction.do?ID="+id,"","height=650,width=1000,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function PrintSendGoods(id){
		window.open("printSendGoodsAction.do?ID="+id,"","height=650,width=1000,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
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
          <td width="926"> 送货清单详情 </td>
          <td width="307" align="right"><table width="270"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="80" align="center"><a href="javascript:PrintSendGoods('${sbf.id}');">打印送货单</a></td>
              <td width="80" align="center"><a href="javascript:PrintShipmentBill('${sbf.id}');">打印结算单</a></td>
              <td width="90" align="center"><c:choose>
                <c:when test="${sbf.isaudit==0}"><a href="javascript:Audit('${sbf.id}');">配送完成</a></c:when>
                <c:otherwise> <a href="javascript:CancelAudit('${sbf.id}')">取消配送完成</a></c:otherwise>
              </c:choose></td>
			  <td width="60" align="center"><c:choose><c:when test="${sbf.isblankout==0}"><a href="javascript:BlankOut('${sbf.id}')">作废</a></c:when><c:otherwise><font color="#FF0000">已作废</font></c:otherwise>
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
	    <td width="11%"  align="right">对象名称：</td>
	    <td width="28%">${sbf.cname}</td>
	    <td width="8%" align="right">收货人：</td>
	    <td width="21%">${sbf.linkman}</td>
	    <td width="8%" align="right">联系电话：</td>
	    <td width="24%">${sbf.tel}</td>
	    </tr>	 
	  <tr>
	    <td  align="right">需求日期：</td>
	    <td>${sbf.requiredate}</td>
	    <td align="right">付款方式：</td>
	    <td><windrp:getname key='paymentmode' value='${sbf.paymentmode}' p='d'/></td>
	    <td align="right">开票信息：</td>
	    <td><c:if test="${sbf.invmsg>0}"><font color="red"></c:if>${invmsgname}</td>
	    </tr>
	  <tr>
	    <td  align="right">发运方式：</td>
	    <td><windrp:getname key='TransportMode' value='${sbf.transportmode}' p='d'/></td>
	    <td align="right">运输单号：</td>
	    <td>${sbf.transportnum}</td>
	    <td align="right">总金额：</td>
	    <td>${sbf.totalsum}</td>
	    </tr>
	   <tr>
	    <td  align="right">收货仓库：</td>
	    <td colspan="5"><windrp:getname key='warehouse' value='${sbf.inwarehouseid}' p='d'/></td>
	    </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="5">${sbf.receiveaddr}
          <label></label>          <label></label></td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${sbf.remark}</td>
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
          <td width="25%"><windrp:getname key='organ' value='${sbf.makeorganid}' p='d'/></td>
          <td width="11%" align="right">制单人：</td>
          <td width="17%"><windrp:getname key='users' value='${sbf.makeid}' p='d'/> </td>
	      <td width="12%" align="right">制单日期：</td>
	      <td width="24%">${sbf.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否配送完成：</td>
	    <td><windrp:getname key='YesOrNo' value='${sbf.isaudit}' p='f'/></td>
	    <td align="right">配送完成人：</td>
	    <td><windrp:getname key='users' value='${sbf.auditid}' p='d'/></td>
	    <td align="right">配送完成日期：</td>
	    <td>${sbf.auditdate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${sbf.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${sbf.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td>${sbf.blankoutdate}</td>
	    </tr>
		<tr>
	    <td  align="right">作废原因：</td>
	    <td colspan="5">${sbf.blankoutreason}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>销售出库单产品列表</td>
        </tr>
      </table>
	  </legend>

<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="9%">产品编号</td> 
          <td width="11%" >产品名称</td>
          <td width="15%">规格</td>
		  <td width="10%">出货仓库</td>         
          <td width="4%">单位</td>
          <td width="8%">单价</td>
          <td width="8%">数量</td>
		   <td width="8%">折扣率</td>
          <td width="8%">税率</td>
          <td width="10%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
		  <td><windrp:getname key='warehouse' value='${p.warehouseid}' p='d'/></td>
		  <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>          
          <td>${p.unitprice}</td>
          <td>${p.quantity}</td>
		   <td>${p.discount}%</td>
          <td>${p.taxrate}%</td>
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
