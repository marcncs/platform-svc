<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
function ShowReport(p){
var b=$F("BeginDate");
var e=$F("EndDate");
var u="";
document.all.chartmsg.src="customerReportAction.do?rp="+p+"&b="+b+"&e="+e+"&u="+u;
}

	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
</script>
</head>

<body onLoad="ShowReport('rate');">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>会员>>会员分析</td>
        </tr>
      </table>
      <form name="search" method="post" action="toCustomerReportAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #B6D6FF;">
        
          <tr class="table-back">
            <td  align="right">登记日期：</td>
            <td ><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="12" onFocus="javascript:selectDate(this)" readonly="readonly">
              -
            <input name="EndDate" type="text" id="EndDate" size="12" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly="readonly"></td>
          <td width="114" align="right">会员数量：</td>
          <td colspan="2" align="left">${count}</td>
          <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
        </tr>
        
      </table>
      </form>
      <br>
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:ShowReport('rate');"><span>会员级别</span></a></li>
              <li><a href="javascript:ShowReport('source');"><span>会员来源</span></a></li>
            </ul>
          </div>
      </div>
      <div style="width:100%" id="listdiv">
          <div>
          <IFRAME id="chartmsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this)"></IFRAME></div>
      </div>
      </td>
  </tr>
</table>
</body>
</html>
