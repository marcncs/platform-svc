<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
var submenu=0;
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
	window.open("../finance/toAddCashBankAdjustAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../finance/toUpdCashBankAdjustAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录"+checkid+"吗？如果删除将永远不能恢复!")){
		window.open("../finance/delCashBankAdjustAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="cashBankAdjustDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
	}
	
	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 现金银行调整</td>
        </tr>
      </table>
      <form name="search" method="post" action="listCashBankAdjustAction.do">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="9%"  align="right">制单机构：</td>
            <td width="18%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="10%" align="right">制单人：</td>
            <td width="15%"><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
              <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID','','du')" value="请选择" readonly></td>
            <td width="10%" align="right">制单日期：</td>
            <td width="35%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)"  size="12" readonly>
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12" readonly>
 </td>
            <td width="3%" align="right"><span class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
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
          <td style="text-align: right;"><pages:pager action="../finance/listCashBankAdjustAction.do"/></td>
        </tr>
      </table>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
            <td width="16%" >编号</td>
			<td width="12%">现金银行编号</td>
			<td width="31%">现金银行名称</td>
			<td width="15%">调整金额</td>
            <td width="13%">是否复核</td>
            <td width="13%">制单人</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
			<td>${s.cbid}</td>
			<td>${s.cbidname}</td>
			<td><windrp:format value='${s.adjustsum}'/></td>
            <td>${s.isauditname}</td>
            <td>${s.makeidname}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
	  <br>
	  <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>调整单详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
      
</body>
</html>
