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
function ShowReport(){
var b=$F("BeginDate");
var e=$F("EndDate");
var u="";
if(b==null||b==""){
	alert("开始日期不能为空");
	return;
}
if(e==null||e==""){
	alert("结束日期不能为空");
	return;
}

document.all.submsg.src="customerExpandAction.do?b="+b+"&e="+e+"&u="+u;
}
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
</script>
</head>

<body>
 <div id="bodydiv">
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >报表分析>>会员>>会员量</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #B6D6FF;">
          <tr class="table-back">
            <td  align="right">登记日期：</td>
            <td >
            <input name="BeginDate" type="text" id="BeginDate"
             onFocus="javascript:selectDate(this)"
              value="<%=com.winsafe.hbm.util.DateUtil.getMonthFirstDay() %>" size="12" maxlength="7">
              -
            <input name="EndDate" type="text" id="EndDate" 
            onFocus="javascript:selectDate(this)" 
            value="<%=com.winsafe.hbm.util.DateUtil.getMonthLastDay() %>" size="12" maxlength="7"></td>
            <td  align="right"></td>
            <td ></td>
             <td class="SeparatePage"><input name="button" type="image" id="button" onclick="ShowReport();" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
      </div>
      <br>
      
      <div style="width:100%;" id="listdiv" >
      <div id="tabs1">
       <ul>
         <li><a href="javascript:ShowReport();"><span>会员量图表</span></a></li>
       </ul>
     </div>
          <IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME>
      </div>
</body>
</html>
