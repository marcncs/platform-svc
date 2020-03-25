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
function SelectProvide(){
	var p=showModalDialog("../purchase/toSelectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	document.searchform.PID.value=p.pid;
	document.searchform.PName.value=p.pname;
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
            <td width="9%"  align="right">出货仓库：</td>
            <td width="28%"><select name="WarehouseID" >
			<option value="">请选择...</option>
            <logic:iterate id="w" name="alw" >
              <option value="${w.id}">${w.warehousename}</option>
            </logic:iterate>
          </select></td>
            <td width="9%" align="right">制单人：</td>
            <td width="23%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">制单机构：</td>
            <td width="22%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==equiporganid?"selected":""}><c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>${o.organname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${BeginDate}" size="10">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${EndDate}" size="10"></td>
            <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord">
              <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
          <td width="7%"  align="center" >仓库名称</td>
          <td width="9%" align="center" >产品编号</td>
          <td width="24%" align="center" >产品名称</td>
          <td width="23%" align="center" >规格</td>
          <td width="14%" align="center" >单位</td>
          <td width="6%" align="center">单价</td>
          <td width="8%" align="center" >数量</td>
          <td width="9%" align="center">金额</td>
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
