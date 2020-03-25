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
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function ShowReport(obj){
var b=$F("BeginDate");
var e=$F("EndDate");
var s=$F("MakeID");
if(obj=="c"){
document.all.chartmsg.src="perDaySaleBarAction.do?b="+b+"&e="+e+"&s="+s;
}else if(obj=="s"){
document.all.chartmsg.src="perDaySaleSumBarAction.do?b="+b+"&e="+e+"&s="+s;
}
}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 产品零售排名</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="perDaySaleReportAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td width="10%"  align="right"> 零售员 ：</td>
            <td width="33%">
			<select name="MakeID" id="MakeID">
            	<option value="">所有</option>
			  <logic:iterate id="u" name="auls" >
                <option value="${u.userid}" <c:if test='${u.userid==makeid}'><c:out value='selected'/></c:if>>${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">日期：</td>
            <td width="49%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" value="${begindate}">
              -
                <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" value="${enddate}">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top-lock">
          <td width="8%"  align="center" >产品编号</td>
          <td width="42%">产品名称</td>
          <td width="15%">单位</td>
          <td width="13%">数量</td>
          <td width="22%">金额小计</td>
        </tr>
		<logic:iterate id="r" name="rls" > 
        <tr class="table-back">
          <td  >${r.productid}</td>
          <td >${r.productname}</td>
          <td >${r.unitname}</td>
          <td >${r.quantity}</td>
          <td >${r.subsum}</td>
        </tr>
		</logic:iterate> 
        <tr class="table-back">
          <td colspan="4" align="center">&nbsp;</td>
          <td align="center">总计：${totalsum}</td>
        </tr>
      </table>
	  <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="38%">&nbsp;</td>
          <td width="62%" align="right"><presentation:pagination target="/report/perDaySaleReportAction.do"/></td>
        </tr>
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="57%" ><table  border="0" cellpadding="0" cellspacing="1">
            <tr class="back-bntgray2">
            <!--  <td width="90" align="center"><a href="javascript:ShowReport('c');">数量统计图表</a></td>
              <td width="90" align="center"><a href="javascript:ShowReport('s');">金额统计图表</a></td>-->
            </tr>
          </table></td>
          <td width="43%">&nbsp;</td>
        </tr>
      </table>
	  <IFRAME id="chartmsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 400" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
    </td>
  </tr>
</table>
</body>
</html>
