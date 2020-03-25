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
var checkcname="";
var orgid="";
var submenu=0;
	function CheckedObj(obj,objid,objcname,objorgid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 orgid=objorgid;
	 submenu = getCookie("PayableMenu");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":Payable(); break;
		case "2":PaymentLog(); break;
		default:Payable();
	 }
	}
	
	function addNew(){
	window.open("../finance/toAddPayableObjectAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Payable(){
	setCookie("PayableMenu","1");
		if(checkid!=""){
			document.all.submsg.src="../finance/listPayableAction.do?POID="+checkid+"&ORGID="+orgid;
		}else{
			alert("请选择你要操作的记录");
		}
			
	}
	
	function PaymentLog(){
	setCookie("PayableMenu","2");
		if(checkid!=""){
			document.all.submsg.src="../finance/listPaymentLogByPayableObjectAction.do?POID="+checkid+"&ORGID="+orgid;
		}else{
			alert("请选择你要操作的记录");
		}
			
	}
	
	function Update(){
		if(checkid!=""){
		window.open("toUpdPayableObjectAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		window.open("../finance/delPayableObjectAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}
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
		search.action="../finance/excPutListPayableObjectAction.do";
		search.submit();
		search.action="listPayableObjectAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../finance/printListPayableObjectAction.do";
		search.submit();
		search.target="";
		search.action="listPayableObjectAction.do";
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
          <td width="772">账务管理>>付款管理</td>
        </tr>
      </table>
      <form name="search" method="post" action="listPayableObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="8%"  align="right">结算期单：</td>
            <td width="23%">
              <input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
              - 
            <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)" value="${EndDate}" readonly></td>
            <td width="10%" align="right">对象类型：</td>
            <td width="24%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/>            </td>
            <td width="9%" align="right">选择对象：</td>
            <td colspan="2"><input name="POID" type="hidden" id="POID">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          </tr>
          <tr class="table-back">
            <td  align="right">所属机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
            <td align="right">&nbsp;</td>
            <td width="18%">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
		   <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
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
          <td class="SeparatePage"><pages:pager action="../finance/listPayableObjectAction.do"/></td>
        </tr>
      </table>
	  </div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="listdiv" style="overflow-y: auto; height: 650px;" >
					<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="9%">对象类型</td>
			<td width="22%">领款方</td>
			<td width="12%">期初应付金额</td>
            <td width="8%">本期应付金额</td>
            <td width="8%">本期已付金额 </td>
			<td width="8%">期未应付金额 </td>
            <td width="10%">所属机构</td>
            </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.oid}','${s.objectsortname}','${s.makeorganid}');"> 
            <td >${s.oid}</td>
            <td>${s.objectsortname}</td>
			<td>${s.payee}</td>
			<td align="right"><fmt:formatNumber value="${s.previoussum}" pattern="0.00"/></td>
            <td align="right"><fmt:formatNumber value="${s.currentsum}" pattern="0.00"/></td>
			<td align="right"><fmt:formatNumber value="${s.currentalreadysum}" pattern="0.00"/></td>
            <td align="right"><fmt:formatNumber value="${s.waitpayablesum}" pattern="0.00"/></td>
            <td>${s.makeorganidname}</td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
	  <br>
	   
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Payable();"><span>应付款结算</span></a></li>
               <li><a href="javascript:PaymentLog();"><span>付款</span></a></li>
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
