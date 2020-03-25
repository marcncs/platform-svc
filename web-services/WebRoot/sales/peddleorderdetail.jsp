<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">

	function Prints(soid){
			window.open("../sales/printPeddleOrderAction.do?ID="+soid,"newwindow","height=600,width=970,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Blankout(soid){
		if(window.confirm("您确认要作废该记录吗？如果作废将不能恢复!")){
			window.open("../sales/blankoutPeddleOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
	
	function toaddidcode(pidid, batch, piid){
			window.open("../sales/toAddPeddleOrderIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	
	
</script>
<style type="text/css">
<!--

#sq {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>

<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width='40%' height="32" class="title-back"> 仓库</td>
	  <td width='25%' class="title-back">批次</td>
      <td width='35%' class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="3">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="958"> 零售单详情 </td>
          <td width="275" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:Prints('${sof.id}');">打印</a></td>         
			  <td width="60" align="center"><c:choose><c:when test="${sof.isblankout==0}"><a href="javascript:Blankout('${sof.id}')">作废</a></c:when><c:otherwise>
<font color="#FF0000">已作废</font></c:otherwise>
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
	  	<td width="9%"  align="right">会员编号：</td>
          <td width="22%">${sof.cid}</td>
          <td width="9%"  align="right">会员名称：</td>
          <td width="23%">${sof.cname}</td>
          <td width="9%" align="right">收货人：</td>
          <td width="28%">${sof.receiveman}</td>
	  </tr>
	  <tr>
	    <td align="right">会员手机：</td>
	    <td >${sof.cmobile}</td>
	    <td  align="right">联系电话：</td>
	    <td>
	      ${sof.receivetel}</td>
	    <td  align="right">总金额：</td>
	    <td><fmt:formatNumber value="${sof.totalsum}" pattern="0.00"/></td>
	  </tr>
	  <tr>
	    <td  align="right">出货仓库：</td>
	    <td>${sof.warehouseidname}</td>
	    <td align="right">备注：</td>
	    <td>${sof.remark}</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
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
	  	<td width="9%"  align="right">制单人：</td>
          <td width="22%">${sof.makeidname}</td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${sof.makedatename}</td>
	      <td width="9%" align="right">是否挂帐：</td>
	      <td width="28%">${sof.isaccountname}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td>${sof.isblankoutname}</td>
	    <td align="right">作废人：</td>
	    <td>${sof.blankoutidname}</td>
	    <td><div align="right">作废日期：</div></td>
	    <td>${sof.blankoutdate}</td>
	  </tr>
	   <tr>
	    <td  align="right">作废原因：</td>
	    <td colspan="5">${sof.blankoutreason}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="91" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="91">零售单产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="8%">产品编号</td> 
          <td width="15%" >产品名称</td>
          <td width="18%">规格</td>
          <td width="7%">单位</td>
          <td width="4%">单价</td>
          <td width="5%">税后单价</td>
          <td width="5%">数量</td>
		  <td width="4%">折扣率</td>
		  <td width="4%">税率</td>
          <td width="14%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td align="right"><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          <td align="center"><fmt:formatNumber value="${p.taxunitprice}" pattern="0.00"/></td>
          <td align="center"><fmt:formatNumber value="${p.quantity}" pattern="0"/></td>
		  <td align="right"><fmt:formatNumber value="${p.discount}" pattern="0.00"/>%</td>
		  <td align="right"><fmt:formatNumber value="${p.taxrate}" pattern="0.0"/>%</td>
          <td align="right"><fmt:formatNumber value="${p.subsum}" pattern="0.00"/></td>
        </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
