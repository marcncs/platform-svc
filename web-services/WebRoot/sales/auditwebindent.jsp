<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<script src="../js/selectwarehouse.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function ChkValue(){
		var wid = document.getElementsByName("warehouseid");
		for (i=0; i<wid.length; i++){
			if ( wid[i].value == ""){
				alert("请选择出货仓库！");
				return false;
			}
		}
		return true;
	}
	
</script>
<style type="text/css">
<!--
#hd {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
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
<div id="hd">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width="50%" height="32" class="title-back">历史成交价</td>
      <td width="50%" class="title-back">成交价日期</td>
    </tr>
    <tr>
      <td colspan="2">
       <div id="historyprice">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

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
<form name="webindentform" method="post" action="updWebIndentAction.do" onSubmit="return ChkValue();">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
<input type="hidden" name="SOID" value="${sof.id}">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="958"> 审核网站订单 </td>
          <td width="275" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <!--<td width="60" align="center"><a href="javascript:SaleOrder('${sof.id}');">打印</a></td>-->
              <td width="60" align="center">&nbsp;</td>
              <td width="60" align="center">&nbsp;</td>
              <td width="60" align="center">&nbsp;</td>
            </tr>
          </table></td>
  </tr>
</table>
<fieldset align="center"> <legend>
      <table width="70" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>订货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"> 会员编号： 
	  	  <input name="cid" type="hidden" id="cid" value="${sof.cid}"></td>
          <td width="28%">${sof.cid}</td>
          <td width="12%" align="right">会员名称：</td>
          <td width="20%">${sof.cname}</td>
	      <td width="10%" align="right">会员手机：</td>
	      <td width="21%">${sof.cmoblie}</td>
	  </tr>
	  </table>
</fieldset>


<fieldset align="center"> <legend>
      <table width="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>收货人信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">	  	  收货人：</td>
          <td width="28%">${sof.receiveman}</td>
          <td width="12%" align="right">收货人手机：</td>
          <td width="20%">${sof.receivemobile}</td>
	      <td width="10%" align="right">收货人电话：</td>
	      <td width="21%">${sof.receivetel}</td>
	  </tr>
	  <tr>
	    <td  align="right">收货地址：</td>
	    <td colspan="5">${sof.transportaddr}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">	  	  交货日期：</td>
          <td width="28%">${sof.consignmentdate}</td>
          <td width="12%" align="right">发运方式：</td>
          <td width="21%">${sof.transportmodename}</td>
	      <td width="9%" align="right">付款方式：</td>
	      <td width="21%">${sof.paymentmodename}</td>
	  </tr>
	  <tr>
	    <td  align="right">开票信息：</td>
	    <td><c:if test="${sof.invmsg>0}"><font color="red"></c:if>${sof.invmsgname}</td>
	    <td align="right">发票抬头：</td>
	    <td>${sof.tickettitle}</td>
	    <td align="right">送货机构：</td>
	    <td><select name="equiporganid" id="equiporganid">
          <logic:iterate id="ol" name="aolist" >
            <option value="${ol.id}" ${ol.id==sof.equiporganid?"selected":""}>
            <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
              <c:out value="--"/>
            </c:forEach>
              ${ol.organname}</option>
          </logic:iterate>
        </select></td>
	    </tr>
	  <tr>
	    <td  align="right">总金额：</td>
	    <td>${sof.totalsum}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
		<td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">   ${sof.remark}</td>
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
	  	<td width="9%"  align="right">制单日期：</td>
          <td width="29%">${sof.makedate}</td>
          <td width="11%" align="right">&nbsp;</td>
          <td width="21%">&nbsp;</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="21%">&nbsp;</td>
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
	    <td  align="right">是否结案：</td>
	    <td>${sof.isendcasename}</td>
	    <td align="right">结案人：</td>
	    <td>${sof.endcaseidname}</td>
	    <td align="right">结案日期：</td>
	    <td>${sof.endcasedate}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否作废：</td>
	    <td>${sof.isblankoutname}</td>
	    <td align="right">作废人：</td>
	    <td>${sof.blankoutidname}</td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">作废原因：</td>
	    <td colspan="5">${sof.blankoutreason}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>零售单产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">	
        <tr align="center" class="title-top">
          <td width="8%">产品编号</td> 
          <td width="15%" >产品名称</td>
          <td width="18%">规格</td>
          <td width="7%">单位</td>
          <td width="12%">出货仓库</td>
		<!--  <td width="8%">批次</td>-->
		  <td width="3%">单价</td>
          <td width="5%">税后单价</td>
          <td width="5%">数量</td>
		  <td width="6%">检货数量</td>
		  <td width="4%">折扣率</td>
		  <td width="4%">税率</td>
          <td width="6%">金额</td>
        </tr>
		<c:set var="count" value="2"/> 
        <logic:iterate id="p" name="als" > 
        <tr class="table-back">
          <td><input type="hidden" name="wdid" value="${p.id}">${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.unitidname}</td>
          <td><input type="hidden" name="warehouseid" id="warehouseid${count}" value="${p.warehouseid}"><input type="text" name="wname" value="${p.warehouseidname}" onFocus="selectUnit(this,$F('equiporganid'),'')" readonly>
		  </td>
		  <!-- <td></td>-->
		  <td align="right"><fmt:formatNumber value="${p.unitprice}" pattern="0.00"/></td>
          <td align="center"><c:if test="${p.unitprice!=p.taxunitprice}"><font color="red"></c:if><fmt:formatNumber value="${p.taxunitprice}" pattern="0.00"/></td>
          <td align="center"><fmt:formatNumber value="${p.quantity}" pattern="0"/></td>
		  <td align="center"><fmt:formatNumber value="${p.takequantity}" pattern="0"/></td>
		  <td align="right"><fmt:formatNumber value="${p.discount}" pattern="0.00"/>%</td>
		  <td align="right"><fmt:formatNumber value="${p.taxrate}" pattern="0.0"/>%</td>
          <td align="right"><fmt:formatNumber value="${p.subsum}" pattern="0.00"/></td>
         </tr>
		 <c:set var="count" value="${count+1}"/>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
    <tr>
            <td  align="center"><input type="Submit" name="Submit"    value="提交">&nbsp;&nbsp;
            <input type="reset" name="Submit2" value="取消" ></td>
          </tr>
		  
</table>
</form>
</body>
</html>
