<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function addNew(){
	window.open("../finance/toAddIncomeLogAction.do","","height=550,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../finance/toUpdIncomeLogAction.do?ID="+checkid,"","height=550,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	if(checkid!=""){
			document.all.submsg.src="../finance/incomeLogDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferIncomeLogAction.do?ILID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除所选的编号为："+checkid+"的记录吗?如果删除将永远不能恢复!")){
			window.open("../finance/delIncomeLogAction.do?ILID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
	        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 收款记录
          </td>
        </tr>
      </table>
      <form name="search" method="post" action="listIncomeLogByReceivableObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right"><input name="ROID" type="hidden" id="ROID" value="${roid}">
            制单日期：</td>
            <td width="25%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
              -
            <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="9%" align="right">关键字：</td>
            <td width="27%"><input type="text" name="KeyWord" maxlength="64"></td>
            <td width="10%" align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td style="text-align: right;"><pages:pager action="../finance/listIncomeLogByReceivableObjectAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="listIncomeLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
		  <tr class="title-top"> 
            <td width="10%"  align="center">编号 </td>
            <td width="29%"  align="center">付款人</td>
            <td width="10%"  align="center">收款金额</td>
            <td width="15%"  align="center">收款去向</td>
            <td width="12%"  align="center">制单人</td>
            <td width="10%"  align="center">制单日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="p" name="arls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
            <td  >${p.id}</td>
            <td >${p.drawee}</td>
            <td  align="right"><windrp:format value='${p.incomesum}'/></td>
            <td >${p.fundattachname}</td>
            <td >${p.makeidname}</td>
            <td >${p.makedate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
	  <br>
	      <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'submsg','listdiv');setIframeHeight(this)"></IFRAME></div>
      </div>
      
</body>
</html>
