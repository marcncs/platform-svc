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
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	IncomeLogDetail();
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
	
	function IncomeLogDetail(){
		if(checkid!=""){
			document.all.submsg.src="incomeLogDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid!=""){
			window.open("../finance/delIncomeLogAction.do?ILID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
	
	function SelectCustomer(){
		var os = document.search.ObjectSort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.ROID.value=o.id;
		document.search.cname.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.ROID.value=m.cid;
		document.search.cname.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.ROID.value=p.pid;
		document.search.cname.value=p.pname;
		}
		if(os==3){
		var p=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.ROID.value=p.uid;
		document.search.cname.value=p.uname;
		}

	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}


	function excput(){
		search.target="";
		search.action="../finance/excPutListIncomeLogAction.do";
		search.submit();
		search.action="listIncomeLogAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../finance/printListIncomeLogAction.do";
		search.submit();
		search.target="";
		search.action="listIncomeLogAction.do";
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
          <td width="772">账务管理>>收款
          </td>
        </tr>
      </table>
      <form name="search" method="post" action="listIncomeLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right">对象类型：</td>
            <td width="26%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/></td>
            <td width="9%" align="right">选择对象：</td>
            <td width="26%"><input name="ROID" type="hidden" id="ROID" value="${ROID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="10%" align="right">是否复核：</td>
            <td width="20%" colspan="2"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否收款：</td>
            <td><windrp:select key="YesOrNo" name="IsReceive" p="y|f" value="${IsReceive}"/></td>
            <td align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">制单部门：</td>
            <td colspan="2"><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
              <input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" value="${deptname}" readonly></td>
          </tr>
		   <tr class="table-back">
            <td  align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}"> <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
-
  <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
		    <td class="SeparatePage"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0"></td>
		   </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
        <td width="50">
		<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
		width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
	    </td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51">
			<a href="javascript:print();"><img
					src="../images/CN/print.gif" width="16" height="16"
					border="0" align="absmiddle">&nbsp;打印</a>
		</td>
		<td width="1">
			<img src="../images/CN/hline.gif" width="2" height="14">
		</td>
          <td class="SeparatePage"><pages:pager action="../finance/listIncomeLogAction.do"/></td>
        </tr>
      </table>
	  </div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="listdiv" style="overflow-y: auto; height: 650px;" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
		  <tr class="title-top"> 
            <td width="10%"  align="center">编号 </td>
            <td width="29%"  align="center">付款人</td>
            <td width="10%"  align="center">结算方式</td>
            <td width="10%"  align="center">收款金额</td>           
            <td width="13%"  align="center">收款去向</td>
            <td width="10%"  align="center">制单机构</td>
			<td width="14%"  align="center">制单日期</td>
          </tr>
          <logic:iterate id="p" name="arls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td >${p.drawee}</td>
            <td ><windrp:getname key='paymentmode' value='${p.paymentmode}' p='d'/>
</td>
            <td align="right"><windrp:format value='${p.incomesum}' p="###,##0.00"/></td>            
            <td >${p.fundattachname}</td>
            <td ><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
			<td >${p.makedate}</td>
          </tr>
          </logic:iterate> 
      </table>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:IncomeLogDetail();"><span>收款记录详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
      
	  </div>
	  </td>
  </tr>
</table>
</body>
</html>
