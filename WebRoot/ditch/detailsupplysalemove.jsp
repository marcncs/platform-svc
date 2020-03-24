<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/showSQ.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<script language="javascript">
	var checkid=0;
	function CheckedObj(obj){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}
	

	function Audit(id){
			popWin("../ditch/auditSupplySaleMoveAction.do?ID="+id,500,250);
	}
	
	function CancelAudit(id){
			popWin("../ditch/cancelAuditSupplySaleMoveAction.do?ID="+id,500,250);
	}
	function prints(id){
			popWin("../ditch/printSupplySaleMoveAction.do?ID="+id,900,600);
	}
	
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 渠道代销详情 </td>
    <td align="right">
    <table   border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="60" align="center"><a href="javascript:prints('${ama.id}');">打印</a></td>
        <td style="float: right; padding-right: 20px;">
     	<c:choose>
          <c:when test="${ama.isaudit==0}"><a href="javascript:Audit('${ama.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${ama.id }')">取消复核</a></c:otherwise>
        </c:choose>
        &nbsp;&nbsp;
        <c:choose>
          <c:when test="${ama.isshipment==0}">发货</c:when>
          <c:otherwise>取消发货</c:otherwise>
        </c:choose>
        &nbsp;&nbsp;
        <c:choose>
          <c:when test="${ama.isblankout==0}">作废</c:when>
          <c:otherwise><span class="style1">已作废</span></c:otherwise>
        </c:choose>
         &nbsp;&nbsp;
        <c:choose>
          <c:when test="${ama.iscomplete==0}">签收</c:when>
          <c:otherwise>取消签收</c:otherwise>
        </c:choose>
       </td>
      
      </tr>
    </table></td>
  </tr>
</table>

<fieldset style="text-align: center;">
 <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">编号：</td>
          <td width="20%">${ama.id}</td>
          <td width="12%" align="right">需求日期：</td>
          <td width="23%">
          <windrp:dateformat value="${ama.movedate}" p="yyyy-MM-dd"/></td>
	       <td align="right">总金额：</td>
	    <td >${ama.totalsum }</td>
	  </tr>
	  <tr>
	    <td  align="right">付款方式：</td>
	    <td><windrp:getname key='paymentmode' value='${ama.paymentmode}' p='d'/>
		</td>
	    <td align="right">开票信息：</td>
	    <td ><windrp:getname key="InvoiceType" p="f" value="${ama.invmsg}"/></td>
	    <td align="right">发票抬头：</td>
	    <td >${ama.transportmode}</td>
	  </tr>
	   <tr>
	   <td align="right">发运方式：</td>
	    <td ><windrp:getname key='TransportMode' value='${ama.transportmode}' p='d'/></td>
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${ama.makeorganid}' p='d'/></td>
	    <td width="9%" align="right">调出仓库：</td>
	      <td width="25%"><windrp:getname key='warehouse' value='${ama.outwarehouseid}' p='d'/></td>
	   
	  </tr>
	  <tr>
	  <td align="right">申请机构： </td>
	    <td ><windrp:getname key="organ" p="d" value="${ama.supplyorganid}" /></td>
	  <td align="right">调入机构：</td>
	    <td ><windrp:getname key='organ' value='${ama.inorganid}' p='d'/></td>
	     <td align="right">调入仓库：</td>
	    <td ><windrp:getname key='warehouse' value='${ama.inwarehouseid}' p='d'/></td>
	  </tr>
       <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="3">${ama.transportaddr}</td>
	    </tr>
	   <tr>
	    <td  align="right">代销原因：</td>
	    <td colspan="3">${ama.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${ama.remark}</td>
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
	  	<td align="right">制单部门：</td>
          <td><windrp:getname key='dept' value='${ama.makedeptid}' p='d'/></td>
          <td align="right">制单人：</td>
          <td><windrp:getname key='users' value='${ama.makeid}' p='d'/></td>
	    <td align="right">制单日期：</td>
	    <td><windrp:dateformat p="yyyy-MM-dd HH:mm:ss" value="${ama.makedate}" /></td>
	    </tr>
      <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ama.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat p="yyyy-MM-dd HH:mm:ss" value="${ama.auditdate}" /></td>
	  </tr>
	  <tr>
	    <td  align="right">是否发货：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isshipment}' p='f'/></td>
	    <td align="right">发货人：</td>
	    <td><windrp:getname key='users' value='${ama.shipmentid}' p='d'/></td>
	    <td align="right">发货日期：</td>
	    <td><windrp:dateformat p="yyyy-MM-dd HH:mm:ss" value="${ama.shipmentdate}" /></td>
	    </tr>
        <tr>
	    <td  align="right">是否签收：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.iscomplete}' p='f'/></td>
	    <td align="right">签收人：</td>
	    <td><windrp:getname key='users' value='${ama.receiveid}' p='d'/></td>
	    <td align="right">签收日期：</td>
	    <td><windrp:dateformat p="yyyy-MM-dd HH:mm:ss" value="${ama.receivedate}" /></td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${ama.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td><windrp:dateformat p="yyyy-MM-dd HH:mm:ss" value="${ama.blankoutdate}" /></td>
	    </tr>
	  <tr>
	  	<td align="right" >作废原因：</td>
	    <td colspan="5">${ama.blankoutreason }	      </td>
	    </tr>
	    
	    
	    <tr>
	    <td align="right">是否检货：</td>
	    <td><windrp:getname key="YesOrNo" p="f" value="${ama.takestatus }"/></td>
	    <td  align="right">打印次数：</td>
	    <td>${ama.printtimes }</td>
	    <td align="right"></td>
	    <td></td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>代销申请产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="10%">产品编号</td> 
          <td width="18%">产品名称</td>
          <td width="17%">规格</td>
          <td width="7%">单位</td>
          <td width="8%">订购价格</td>
          <td width="7%">销售价格</td>
          <td width="6%">相关</td>
          <td width="6%">数量</td>
          <td width="8%">检货数量</td>
          <td width="7%">订购金额</td>
		  <td width="7%">销售金额</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar" onClick="CheckedObj(this);">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td align="right"><fmt:formatNumber value='${p.punitprice}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.sunitprice}' pattern='0.00'/></td>
          <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16" border="0"> </a></td>
          <td align="right"><fmt:formatNumber value='${p.quantity}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.takequantity}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.psubsum}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.ssubsum}' pattern='0.00'/></td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  </td>
  </tr>
</table>
</body>
</html>
