<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript"> 
function ShowReport(){
var b=$F("BeginYear");
var e=$F("EndYear");
var u=$F("MakeID");
var o=$F("MakeOrganID");
var oname=$F("oname");
if(b==null||b==""){
	alert("开始日期不能为空");
	return;
}
if(e==null||e==""){
	alert("结束日期不能为空");
	return;
}

if(oname==null||oname==""){
	alert("请选择机构");
	return;
}
 
document.all.chartmsg.src="saleOrderTrendAction.do?b="+b+"&e="+e+"&u="+u+"&o="+o;
}
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.getElementById("MakeOrganID").value=p.id;
		document.getElementById("oname").value=p.organname;
	}
</script>
</head>
<body>
    <div id="bodydiv">
 	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>报表分析>>成本>>营业额趋势</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #B6D6FF;">
 
          <tr class="table-back">
            <td align="right">日期：</td>
            <td>
              <select name="BeginYear" id="BeginYear"> 
                <c:forEach items="${list}" var="p">
                	<c:choose>
			  		<c:when test="${p.year==beginyear}"><option value="${p.year}" selected="selected">${p.year}</option></c:when>
			  		<c:otherwise><option value="${p.year}">${p.year}</option></c:otherwise>
			  	</c:choose>
                </c:forEach>
              </select>
              -            
              <select name="EndYear" id="EndYear">
			  	<c:forEach items="${list}" var="p">
			  	<c:choose>
			  		<c:when test="${p.year==endyear}"><option value="${p.year}" selected="selected">${p.year}</option></c:when>
			  		<c:otherwise><option value="${p.year}">${p.year}</option></c:otherwise>
			  	</c:choose>
                </c:forEach>
               </select></td>
               <td align="right">
					机构：
				</td>
				<td>
					<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}"
				readonly><a href="javascript:SelectOrgan();"><img 
					src="../images/CN/find.gif" width="18" height="18" 
					border="0" align="absmiddle"></a>

				</td>
            
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" onClick="ShowReport();" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
	</div>
      <br>
      <div style="width:100%" id="listdiv">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:ShowReport();"><span>营业额趋势表</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="chartmsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME></div>
      </div>
 
</body>
</html>
