<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	function Refer(){
		if(confirm("你确定要生成采购申请吗？这将花费少许时间。")==true){
window.open("../purchase/makePurchaseApplyAction.do","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 库存分析</td>
        </tr>
      </table>
      <form name="search" method="post" action="stockConstrueAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
	  
        <tr class="table-back"> 
          <td width="10%" align="right">名称关键字：</td>
          <td width="90%"> 
            <input name="KeyWord" type="text" id="KeyWord" value="${keyword}">
            <input type="submit" name="Submit" value="查询"></td>
        </tr>
		
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="78">产品编号</td>
            <td width="381" >产品名称</td>
            <td width="86"> 当前库存</td>
            <td width="51"> 单位</td>
            <td width="84"> 最低库存</td>
            <td width="106">标准库存</td>
            <td width="92">最高库存</td>
            <td width="91">建议采购量</td>
          </tr>
          <logic:iterate id="p" name="alp" > 
          <tr align="center" class="table-back"> 
            <td>${p.id}</td>
            <td >${p.productname}</td>
            <td> <font <c:if test="${p.stockpile<=0}"><c:out value="color=red" /></c:if>>${p.stockpile}</font></td>
            <td>${p.unit}</td>
            <td>${p.leaststock}</td>
            <td>${p.standardstock}</td>
            <td>${p.tiptopstock}</td>
            <td><input name="purchaseacount" type="text" id="purchaseacount" value="${p.purchaseacount}" size="8" maxlength="8"></td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table width="178"  border="0" cellpadding="0" cellspacing="0">
            <tr align="center">
              <td width="90"><a href="javascript:Refer();">生成采购申请</a></td>
            </tr>
          </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/purchase/stockConstrueAction.do"/>          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
