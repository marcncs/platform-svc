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
	var checkid=0;
	function CheckedObj(obj){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}
	

	function Audit(id){
			popWin("../ditch/auditSupplySaleApplyAction.do?ID="+id,500,250);
	}
	
	function CancelAudit(id){
		popWin("../ditch/cancelAuditSupplySaleApplyAction.do?ID="+id,500,250);
	}
	function Ratify(id){
		popWin("../ditch/toRatifySupplySaleApply.do?ID="+id,1000,650);
	}
	function CancelRatify(id){
		popWin("../ditch/cancelRatifySupplySaleApplyAction.do?ID="+id,500,250);
	}
	function BlankOut(id){
		if(window.confirm("您确认要作废编号为："+id+" 的 代销申请吗？如果作废后将不能恢复!"))
			popWin("../ditch/blankOutSupplySaleApplyAction.do?ID="+id,500,250);
	}
	function CancelBlankOut(id){
		popWin("../ditch/cancelBlankOutSupplySaleApplyAction.do?ID="+id,500,250);
	}
	function prints(id){
			popWin("../ditch/printSupplySaleApplyAction.do?ID="+id,900,600);
	}
	
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 代销申请详情 </td>
    <td align="right"><table   border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="60" align="center"><a href="javascript:prints('${ama.id}');">打印</a></td>
        <td style="float: right; padding-right: 20px;">
       <c:if test="${ISAUDIT=='no'}">
     	<c:choose>
          <c:when test="${ama.isaudit==0}"><a href="javascript:Audit('${ama.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${ama.id }')">取消复核</a></c:otherwise>
        </c:choose>
        </c:if>
        <c:if test="${ISAUDIT=='yes'}">
        <c:if test="${ama.isblankout==0}">
	        <c:choose>
	          <c:when test="${ama.isratify==0}"><a href="javascript:Ratify('${ama.id}');">批准</a></c:when>
	          <c:otherwise> <a href="javascript:CancelRatify('${ama.id }')">取消批准</a></c:otherwise>
	        </c:choose>
        </c:if>
        &nbsp;&nbsp;
        <c:choose>
          <c:when test="${ama.isblankout==0}"><a href="javascript:BlankOut('${ama.id}');">作废</a></c:when>
          <c:otherwise><span class="style1">已作废</span></c:otherwise>
        </c:choose>
        </c:if>
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
	  	<td width="11%"  align="right">编号：</td>
          <td width="22%">${ama.id}</td>
          <td width="10%" align="right">需求日期：</td>
          <td width="23%">
          <windrp:dateformat value="${ama.movedate}" p="yyyy-MM-dd"/></td>
	      <td width="9%" align="right">调出机构：</td>
	      <td width="25%"><windrp:getname key="organ" p="d" value="${ama.outorganid}" /></td>
	  </tr>
	  <tr>
	    <td  align="right">付款方式：</td>
	    <td><windrp:getname key='paymentmode' value='${ama.paymentmode}' p='d'/>
		</td>
	    <td align="right">开票信息：</td>
	    <td >${invmsgname }</td>
	    <td align="right">发运方式：</td>
	    <td ><windrp:getname key='TransportMode' value='${ama.transportmode}' p='d'/></td>
	  </tr>
	   <tr>
	    <td  align="right">收货地址：</td>
	    <td>${ama.transportaddr}</td>
	    <td align="right">发票抬头：</td>
	    <td >${ama.tickettitle}</td>
	    <td align="right">总金额：</td>
	    <td >${ama.totalsum }</td>
	  </tr>
	   <tr>
	    <td align="right">调入机构：</td>
	    <td ><windrp:getname key='organ' value='${ama.inorganid}' p='d'/></td>
	    <td align="right" >调入仓库：</td>
	    <td colspan="3"><windrp:getname key='warehouse' value='${ama.inwarehouseid}' p='d'/></td>
	  </tr>
	  <tr>
	     <td align="right">联系人：</td>
	    <td >${ama.olinkman }</td>
	  	<td  align="right">联系电话：</td>
	    <td colspan="3">${ama.otel}</td>
	  </tr>
	   <tr>
	    <td  align="right">代销原因：</td>
	    <td colspan="5">${ama.movecause}</td>
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
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ama.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat value='${ama.auditdate}' p='yyyy-MM-dd HH:mm:ss'/></td>
	  </tr>
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isratify}' p='f'/></td>
	    <td align="right">批准人：</td>
	    <td><windrp:getname key='users' value='${ama.ratifyid}' p='d'/></td>
	    <td align="right">批准日期：</td>
	    <td><windrp:dateformat value='${ama.ratifydate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${ama.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td><windrp:dateformat value='${ama.blankoutdate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	  <tr>
	  	<td align="right" >作废原因：</td>
	    <td>${ama.blankoutreason}</td>
	    <td  align="right">是否转代销单：</td>
	    <td><windrp:getname key='YesOrNo' value='${ama.istrans}' p='f'/></td>
		<td align="right">制单日期：</td>
	    <td><windrp:dateformat value='${ama.makedate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	     <tr>
	  	<td width="11%"  align="right">制单机构：</td>
          <td width="22%"><windrp:getname key='organ' value='${ama.makeorganid}' p='d'/></td>
          <td width="10%" align="right">制单部门：</td>
          <td><windrp:getname key='dept' value='${ama.makedeptid}' p='d'/></td>
         <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${ama.makeid}' p='d'/></td>
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
          <td>产品编号</td> 
          <td>产品名称</td>
          <td>规格</td>
          <td>单位</td>
          <td>订购价格</td>
          <td>销售价格</td>
          <td>数量</td>
          <td>已批准数量</td>
          <td>已转数量</td>
          <td>订购金额</td>
          <td>销售金额</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar" onClick="CheckedObj(this);">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td align="right"><fmt:formatNumber value='${p.punitprice}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.sunitprice}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.quantity}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.canquantity}' pattern='0.00'/></td>
          <td align="right"><fmt:formatNumber value='${p.alreadyquantity}' pattern='0.00'/></td>
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
