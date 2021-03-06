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
	document.searchform.productid.value=p.id;
	document.searchform.productname.value=p.productname;
	}
function excput(){
	searchform.action="../report/excPutSaleProductOrderAction.do";
	searchform.submit();
	searchform.action="../report/saleProductOrderAction.do";
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">产品零售排名</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="saleProductOrderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="8%"  align="right">制单机构：</td>
            <td width="21%"><select name="makeorganid" id="makeorganid">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==makeorganid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">配送机构：</td>
            <td width="31%"><select name="equiporganid" id="equiporganid">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==equiporganid?"selected":""}>
                <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">单据类型：</td>
            <td width="22%"><select name="billtype" id="billtype">
              <option value="">所有</option>
              <option value="SO" ${billtype=='SO'?"selected":""}>零售单</option>
              <option value="PD" ${billtype=='PD'?"selected":""}>零售单</option>
              <option value="WI" ${billtype=='WI'?"selected":""}>网站订单</option>
			  <option value="VO" ${billtype=='VO'?"selected":""}>行业零售单</option>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);"  size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);"  size="10"></td>
            <td align="right">排名方式：</td>
            <td><select name="ordertype" id="ordertype">
              <option value="q" ${ordertype=='q'?"selected":""}>零售数量</option>
              <option value="s" ${ordertype=='s'?"selected":""}>零售金额</option>
            </select>
              <a href="javascript:SelectSingleProduct();">
              <input type="submit" name="Submit" value="查询">
            </a></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
		  <td  width="11%" align="center" >零售排名</td>
          <td  width="11%" align="center" >产品编号</td>
          <td width="20%" align="center" >产品名称</td>
          <td width="18%" align="center" >规格</td>
          <td width="6%" align="center" >单位</td>
          <td width="10%" align="center" >零售数量</td>
          <td width="11%" align="center" >零售金额</td>
        </tr>
		<c:set var="totalcount" value="0"/>
<c:forEach items="${str}" var="d" >
        <tr class="table-back">
		  <td  >${d.id}</td>
          <td  >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          <td align="right">${d.subsum}</td>
        </tr>
</c:forEach> 
       
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%"><table width="60" border="0">
                <tr>
                  <td align="center"><a href="javascript:excput();" >导出</a></td>
                </tr>
              </table></td>
            <td width="50%" align="right">
			<presentation:pagination target="/report/saleProductOrderAction.do"/>	
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
