<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
 <SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
 <SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<title>样品单详情</title>
<script language="javascript">
	function Audit(sbid){
			popWin2("auditSampleBillAction.do?SBID="+sbid);
	}
	
	function CancelAudit(sbid){
			popWin2("cancelAuditSampleBillAction.do?SBID="+sbid);
	}
	
	function Recycle(sbid){
			popWin2("receiveSampleBillAction.do?SBID="+sbid);
	}
	
	function CancelRecycle(sbid){
			popWin2("cancelReceiveSampleBillAction.do?SBID="+sbid);
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
          <td width="1040"> 样品单详情 </td>
          <td width="193" align="right"><table width="120" height="26" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><c:choose><c:when test="${sb.isaudit==0}"><a href="javascript:Audit('${sb.id}');">复核</a></c:when><c:otherwise>
<a href="javascript:CancelAudit('${sb.id}')">取消复核</a></c:otherwise>
</c:choose></td>
              <td width="60" align="center"><c:choose><c:when test="${sb.isrecycle==0}"><a href="javascript:Recycle('${sb.id}');">回收</a></c:when><c:otherwise>
<a href="javascript:CancelRecycle('${sb.id}')">取消回收</a></c:otherwise>
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
	  	<td width="9%" height="20" align="right"> 客户名称：</td>
          <td width="27%">${sb.cname}</td>
          <td width="12%" align="right">联系人：</td>
          <td width="20%">${sb.linkman}</td>
	      <td width="10%" align="right">联系电话：</td>
	      <td width="22%">${sb.tel}</td>
	  </tr>
	  <tr>
	    <td height="20" align="right">发货日期：</td>
	    <td><windrp:dateformat value="${sb.shipmentdate}" p="yyyy-MM-dd"/></td>
	    <td align="right">约定回收日期：</td>
	    <td><windrp:dateformat value="${sb.estimaterecycle}" p="yyyy-MM-dd"/></td>
	    <td align="right">样品制作者：</td>
	    <td>${sb.makeuser}</td>
	  </tr>
	  <tr>
	    <td height="20" align="right">邮编：</td>
	    <td>${sb.postcode}</td>
	    <td align="right">总金额：</td>
	    <td>${sb.totalsum}</td>
	    <td align="right"></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">送货地址：</td>
	    <td colspan="5">${sb.receiveaddr}</td>
	    </tr>
	  <tr>
	    <td height="20" align="right">备注：</td>
	    <td colspan="5">${sb.remark}</td>
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
	  	<td width="9%" height="20" align="right">制单部门：</td>
          <td width="29%"><windrp:getname key="dept" p="d" value="${sb.makedeptid}"/></td>
          <td width="11%" align="right">制单人：</td>
          <td width="19%"><windrp:getname key="users" p="d" value="${sb.makeid}"/></td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="23%"><windrp:dateformat value="${sb.makedate}" p="yyyy-MM-dd"/></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">是否复核：</td>
	    <td><windrp:getname key="YesOrNo" p="f" value="${sb.isaudit}"/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key="users" p="d" value="${sb.auditid}"/></td>
	    <td align="right">复核日期：</td>
	    <td><windrp:dateformat value="${sb.auditdate}" p="yyyy-MM-dd"/></td>
	    </tr>
	  <tr>
	    <td height="20" align="right">是否回收：</td>
	    <td>
	    	<windrp:getname key="YesOrNo" p="f" value="${sb.isrecycle}"/>
	    </td>
	    <td align="right">回收人：</td>
	    <td>
	    	<windrp:getname key="users" p="d" value="${sb.recycleid}"/>
	    </td>
	    <td align="right">回收日期：</td>
	    <td>
	    	<windrp:dateformat value="${sb.actualrecycle}" p="yyyy-MM-dd"/>
	    </td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>样品单产品列表</td>
        </tr>
      </table>
	  </legend>
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编码</td> 
          <td width="29%" height="25">产品名称</td>
          <td width="25%">规格</td>
          
          <td width="7%">单位</td>
          
          <td width="7%">单价</td>
          <td width="4%">相关</td>
          <td width="6%">数量</td>
          <td width="10%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td height="20">${p.productname}</td>
          <td>${p.specmode}</td>
           <td><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/>
          </td>
          <td><fmt:formatNumber value='${p.unitprice}' pattern='0.00'/></td>
          <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');"><img src="../images/CN/stock.gif" width="16" border="0"> </a></td>
         
          <td>${p.quantity}</td>
          <td><fmt:formatNumber value='${p.subsum}' pattern='0.00'/></td>
        </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
