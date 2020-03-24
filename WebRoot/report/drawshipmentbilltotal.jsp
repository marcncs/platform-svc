<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectSingleProduct(){
	var p=showModalDialog("../warehouse/toSelectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	document.searchform.ProductID.value=p.id;
	document.searchform.ProductName.value=p.productname;
	}
function excput(){
	searchform.action="../report/excPutDrawShipmentBillTotalAction.do";
	searchform.submit();
	searchform.action="../report/drawShipmentBillTotalAction.do";
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>出库>>领用汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="drawShipmentBillTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back">
            <td width="8%"  align="right">制单机构：</td>
            <td width="21%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==MakeOrganID?"selected":""}>
                  <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">制单人：</td>
            <td width="24%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}" ${u.userid==MakeID?"selected":""}>${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="6%" align="right">日期：</td>
            <td width="33%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${BeginDate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${EndDate}" size="10"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">出货仓库：</td>
            <td><select name="WarehouseID" >
            <option value="">请选择...</option>
			<logic:iterate id="w" name="alw" >
              <option value="${w.id}" ${w.id==WarehouseID?"selected":""}>${w.warehousename}</option>
            </logic:iterate>
          </select></td>
            <td align="right">产品：</td>
            <td><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}">
              <a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" border="0">
              <input type="submit" name="Submit" value="查询">
              </a></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top-lock">
          <td width="12%"  >产品编号</td>
          <td width="22%"  >产品名称</td>
          <td width="20%" >规格</td>
          <td width="14%" >单位</td>
          <td width="8%"  >数量</td>
          <td width="9%" >金额</td>
		  <!--<td width="9%" align="center" >成本</td>-->
        </tr>
		
<c:forEach items="${str}" var="d">
        <tr class="table-back">
          <td>${d.productid}</td>
          <td>${d.productname}</td>
          <td>${d.specmode}</td>
          <td>${d.unitidname}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          <td align="right">${d.subsum}</td>
        </tr>
</c:forEach> 
        <tr class="back-gray-light">
          <td  align="right" width="12%"> 本页合计：</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
		  <!-- <td align="center">&nbsp;</td>-->
          <td align="right"><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td align="right">${totalsum}</td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
		  <!-- <td align="center">&nbsp;</td>-->
          <td align="right">${allqt}</td>
          <td align="right">${allsum}</td>
        </tr>
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%"><table width="60" border="0">
                <tr>
                  <td align="center"><a href="javascript:void(0)" onClick="excput()">导出</a></td>
                </tr>
              </table></td>
            <td width="50%" align="right">
			<presentation:pagination target="/report/drawShipmentBillTotalAction.do"/>	
            </td>
          </tr>
       
      </table>
	  
	 
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="57%" >&nbsp;</td>
          <td width="43%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
