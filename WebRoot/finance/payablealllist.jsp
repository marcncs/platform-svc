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
<SCRIPT language="javascript" src="../js/showbill.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
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
	window.open("../finance/toAddPayableAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdPayableAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	if(checkid!=""){
		document.all.submsg.src="../finance/payableDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferPayableAction.do?PAID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			window.open("../finance/delPayableAction.do?PID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
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
	

	function SelectCustomer(){
		var os = document.search.ObjectSort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.POID.value=o.id;
		document.search.cname.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.POID.value=m.cid;
		document.search.cname.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.POID.value=p.pid;
		document.search.cname.value=p.pname;
		}
		if(os==3){
		var p=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.POID.value=p.uid;
		document.search.cname.value=p.uname;
		}

	}
	
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}

	function excput(){
		search.target="";
		search.action="../finance/excPutListPayableAllAction.do";
		search.submit();
		search.action="listPayableAllAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../finance/printListPayableAllAction.do";
		search.submit();
		search.target="";
		search.action="listPayableAllAction.do";
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">账务管理>>应付款结算</td>
        </tr>
      </table>
      <form name="search" method="post" action="listPayableAllAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="12%"  align="right">制单日期：</td>
            <td width="20%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
              -
            <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="9%" align="right">对象类型：</td>
            <td width="24%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/></td>
            <td width="9%" align="right">选择对象：</td>
            <td ><input name="POID" type="hidden" id="POID">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="5%"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">应付金额不等零：</td>
            <td><windrp:select key="YesOrNo" name="GreatZero" p="y|f" value="${GreatZero}"/></td>
            <td align="right">所属机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">关键字：</td>
            <td width="21%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
           <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
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
          <td class="SeparatePage"><pages:pager action="../finance/listPayableAllAction.do"/></td>
        </tr>
      </table>
	  </div>
	<div id="listdiv" style="overflow-y: auto; height: 650px;" >
	<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
			
          <tr align="center" class="title-top"> 
            <td width="10%" >编号</td>
            <td width="12%">应付款对象名称</td>
            <td width="9%">结算金额</td>
            <td width="8%">应付款金额</td>
            <td width="11%">所属机构</td>
            <td width="7%">制单日期</td>
            <td width="10%">到期日</td>
            <td width="7%">超龄(天)</td>
            <td width="11%">单据号</td>
            <td width="19%">描述</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${s.id});"> 
            <td >${s.id}</td>
            <td>${s.payableobjectname}</td>           
			 <td align="right"><windrp:format value='${s.settlementsum}' p='###,##0.00'/></td>
			 <td align="right"><windrp:format value='${s.payablesum}' p='###,##0.00'/></td>
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:dateformat value='${s.awakedate}' p='yyyy-MM-dd'/></td>
            <td>${s.overage}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td>${s.payabledescribe}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
		  
      </table>
      </FORM>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>应付款详情</span></a></li>
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
