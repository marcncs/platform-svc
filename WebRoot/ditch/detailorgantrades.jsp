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
			popWin("../ditch/auditOrganTradesAction.do?ID="+id,500,250);
	}
	function CancelAudit(id){
		popWin("../ditch/cancelAuditOrganTradesAction.do?ID="+id,500,250);
	}
	function Ratify(id){
		popWin("../ditch/toRatifyOrganTradesAction.do?ID="+id,1000,650);
	}
	function CancelRatify(id){
		popWin("../ditch/cancelRatifyOrganTradesAction.do?ID="+id,500,250);
	}
	
	function Affirm(id){
		popWin("../ditch/affirmOrganTradesAction.do?ID="+id,500,250);
	}
	function CancelAffirm(id){
		popWin("../ditch/cancelAffirmOrganTradesAction.do?ID="+id,500,250);
	}
	
	function Receive(id){
		popWin("../ditch/receiveOrganTradesAction.do?ID="+id,500,250);
	}
	function CancelReceive(id){
		popWin("../ditch/cancelReceiveOrganTradesAction.do?ID="+id,500,250);
	}
	function BlankOut(id){
		if(window.confirm("您确认要作废编号为："+id+" 的 订购申请吗？如果作废后将不能恢复!"))
			popWin("../ditch/blankOutOrganTradesAction.do?ID="+id,500,250);
	}
	function CancelBlankOut(id){
		popWin("../ditch/cancelBlankOutOrganTradesAction.do?ID="+id,500,250);
	}
	function prints(id){
			popWin("../ditch/printOrganTradesAction.do?ID="+id+"&isshow=1",900,600);
	}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 渠道换货详情 </td>
    <td align="right"><table   border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="60" align="center"><a href="javascript:prints('${ot.id}');">打印</a></td>
        <td style="float: right; padding-right: 20px;">
     	<c:if test="${isshow=='yes'}">
        	<c:choose>
	     	<c:when test="${ot.isratify==0}"><a href="javascript:Ratify('${ot.id}');">批准</a></c:when>
	     	<c:otherwise><a href="javascript:CancelRatify('${ot.id }')">取消批准</a></c:otherwise>
	     	</c:choose>
	     	&nbsp;&nbsp;
	        <c:choose>
		     <c:when test="${ot.isreceive==0}">签收</c:when>
		     <c:otherwise>已签收</c:otherwise>
		    </c:choose>
	     </c:if>
	    <c:if test="${isshow=='no'}">
     	<c:choose>
          <c:when test="${ot.isaudit==0}"><a href="javascript:Audit('${ot.id}');">复核</a></c:when>
          <c:otherwise><a href="javascript:CancelAudit('${ot.id }')">取消复核</a></c:otherwise>
        </c:choose>
        &nbsp;&nbsp;
	     <c:choose>
	     <c:when test="${ot.isaffirm==0}"><a href="javascript:Affirm('${ot.id}');">确认</a></c:when>
	     <c:otherwise><a href="javascript:CancelAffirm('${ot.id }')">取消确认</a></c:otherwise>
	     </c:choose>
	    </c:if>
	     &nbsp;&nbsp;
        <c:choose>
          <c:when test="${ot.isblankout==0}">作废</c:when>
          <c:otherwise><span class="style1">已作废</span></c:otherwise>
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
	  	<td width="11%"  align="right">编号：</td>
          <td width="22%">${ot.id}</td>
          <td width="10%" align="right">供货机构：</td>
          <td width="23%">
          <windrp:getname key="organ" p="d" value="${ot.porganid}" /></td>
	      <td width="9%" align="right"> 供方联系人：</td>
	      <td width="25%">${ot.plinkman }</td>
	  </tr>
	  <tr>
	    <td  align="right">换出仓库：</td>
	    <td><windrp:getname key='warehouse' value='${ot.outwarehouseid}' p='d'/>
		</td>
		<td  align="right">换出地址：</td>
	    <td>${ot.transportaddr }
		</td>
	    
	    <td align="right">供方联系电话：</td>
	    <td >${ot.tel }</td>
	  </tr>
	  <tr>
	  <td  align="right">换入仓库：</td>
	    <td><windrp:getname key='warehouse' value='${ot.inwarehouseid}' p='d'/>
		</td>
		<td  align="right">换入地址：</td>
	    <td>${ot.rtransportaddr }
		</td>
	  	<td align="right">换方联系人</td>
	    <td >${ot.rlinkman }</td>
	  </tr>
	   <tr>
	   <td align="right">换方联系电话：</td>
	    <td >${ot.rtel }</td>
	    <td  align="right">换货原因：</td>
	    <td colspan="3">${ot.withdrawcause}</td>
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
	  <td align="right">制单机构：</td>
          <td ><windrp:getname key='organ' value='${ot.makeorganid}' p='d'/></td>
          <td  align="right">制单部门：</td>
          <td><windrp:getname key='dept' value='${ot.makedeptid}' p='d'/></td>
  
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${ot.makeid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td align="right">制单日期：</td>
	    <td><windrp:dateformat value='${ot.makedate}'/>
	  	<td align="right" >检货状态：</td>
	    <td ><windrp:getname key="TakeStatus" p="f" value="${ot.takestatus}"/></td>
	    <td  align="right">打印次数：</td>
	    <td >${ot.printtimes}</td>
	    </tr>
	  <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ot.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ot.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat value='${ot.auditdate}' p='yyyy-MM-dd HH:mm:ss'/></td>
	  </tr>
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isratify}' p='f'/></td>
	    <td align="right">批准人：</td>
	    <td><windrp:getname key='users' value='${ot.ratifyid}' p='d'/></td>
	    <td align="right">批准日期：</td>
	    <td><windrp:dateformat value='${ot.ratifydate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	     <tr>
	    <td  align="right">是否确认：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isaffirm}' p='f'/></td>
	    <td align="right">确认人：</td>
	    <td><windrp:getname key='users' value='${ot.affirmid}' p='d'/></td>
	    <td align="right">确认日期：</td>
	    <td><windrp:dateformat value='${ot.affirmdate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	     <tr>
	    <td  align="right">是否签收：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isreceive}' p='f'/></td>
	    <td align="right">签收人：</td>
	    <td><windrp:getname key='users' value='${ot.receiveid}' p='d'/></td>
	    <td align="right">签收日期：</td>
	    <td><windrp:dateformat value='${ot.receivedate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${ot.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td><windrp:dateformat value='${ot.blankoutdate}' p='yyyy-MM-dd HH:mm:ss'/>
	    </td>
	    </tr>
	  
	    <tr>
	  	<td align="right" >作废原因：</td>
	    <td colspan="5">${ot.blankoutreason }</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>渠道退货产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td>产品编号</td> 
          <td>产品名称</td>
          <td>规格</td>
          <td>单位</td>
          <td>批次</td>
          <td>数量</td>
          <td>批准数量</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar" onClick="CheckedObj(this);">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
           <td>${p.batch}</td>
          <td align="right">${p.quantity}</td>
          <td align="right">${p.canquantity}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  </td>
  </tr>
</table>
</body>
</html>
