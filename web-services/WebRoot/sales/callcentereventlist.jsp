<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}

	function SelectCustomer(){
	showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.search.CID.value=getCookie("pid");
	document.search.cname.value=getCookie("pname");
}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 呼叫中心/短信>>呼叫事件查询</td>
  </tr>
</table>
<form name="search" method="post" action="../sales/listCallCenterEventAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
		<input type="hidden" name="CID" value="${CID}">
          <tr class="table-back"> 
            <td width="9%"  align="right">主叫号码：</td>
            <td width="22%"><input name="CallNum" type="text" id="CallNum" value="${CallNum}" maxlength="20">            </td>
            <td width="9%" align="right">被叫号码：</td>
            <td width="14%"><input name="CalledNum" type="text" id="CalledNum" value="${CalledNum}" maxlength="20"></td>
            <td width="9%" align="right">日期：</td>
            <td width="37%"><input type="text" name="BeginDate" size="10" onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
-
  <input type="text" name="EndDate" onFocus="javascript:selectDate(this)" value="${EndDate}" size="10" readonly></td>
  			<td></td>
          </tr>
          <tr class="table-back">
            <td  align="right">类型：</td>
            <td><select name="EventType">
					<option value="">请选择...</option>
					<option value="0" ${EventType==0?"selected":""}>来电</option>
					<option value="1" ${EventType==1?"selected":""}>去电</option>
				</select></td>
            <td align="right">座席号：</td>
            <td><input name="SeatNum" type="text" id="SeatNum" value="${SeatNum}" maxlength="20"></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}" maxlength="60"></td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
        <td class="SeparatePage"><pages:pager action="../sales/listCallCenterEventAction.do"/></td>
							
		</tr>
	 </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top-lock"> 
            <td width="5%"  >编号</td>
            <td width="13%" >主叫号码</td>           
			<td width="12%" >主叫客户</td>
			<td width="12%" >被叫号码</td>
			<td width="10%" >被叫用户</td>
            <td width="15%" >日期</td>
			<td width="6%" >类型</td>
			<td width="18%" >录音</td>
            <td width="14%" >操作人</td>
          </tr>
          <logic:iterate id="s" name="cclist" > 
          <tr class="table-back-colorbar" > 
            <td >${s.id}</td>
            <td>${s.callnum}</td>            
			<td>${s.customername}</td>
			<td>${s.callednum}</td>
			<td>${s.eventtype==0?s.useridname:""}</td>
            <td>${s.eventdate}</td>
			<td>${s.eventtype==0?"来电":"去电"}</td>
			<td>&nbsp;<a href="${s.soundfile}"><img src="../images/laba-10.gif" border="0">右键另存下载</a></td>
            <td>${s.useridname}</td>
            </tr>
          </logic:iterate>
      </table>
      <br>
      </div>
    </td>
  </tr>
</table> 


</body>
</html>
