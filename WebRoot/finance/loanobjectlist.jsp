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
var submenu=0;
var loid=0;
	function CheckedObj(obj,objid,objcname,objloid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 loid=objloid;
	 submenu = getCookie("LoanMenu");
	 switch(submenu){
		case "1":Loan(); break;
		case "2":Reckoning(); break;
		default:Loan();
	 }
	}
	
	function addNew(){
	window.open("../finance/toAddLoanObjectAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function SetAwake(){
		if(checkid!=""){
		window.open("../finance/toSetLoanAwakeAction.do?ID="+loid,"","height=250,width=300,top=150,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Loan(){
	setCookie("LoanMenu","1");
		if(checkid!=""){
			document.all.submsg.src="../finance/listLoanAction.do?uid="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
			
	}
	
	function Reckoning(){
	setCookie("LoanMenu","2");
		if(checkid!=""){
			document.all.submsg.src="../finance/listReckoningAction.do?uid="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
			
	}
	
	function Update(){
		if(checkid!=""){
		window.open("toUpdPayableObjectAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
		window.open("../finance/delLoanObjectAction.do?ID="+loid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
				clearUser("MakeID","uname");
				clearUser("UID","uname2");
	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
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
          <td width="772">账务管理>>个人借支 </td>
        </tr>
      </table>
      <form name="search" method="post" action="listLoanObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		  <td width="12%" align="right">制单机构：</td>
            <td width="23%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="9%"  align="right">借款人：</td>
            <td width="22%">
              <input type="hidden" name="UID" id="UID" value="${UID}">
            <input type="text" name="uname2" id="uname2" onClick="selectDUW(this,'UID',$F(MakeOrganID),'ou')" value="${uname2}" readonly>
            </td>
            
            <td width="7%" align="right">制单人：</td>
            <td width="23%"><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
              <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F(MakeOrganID),'ou')" value="${uname}" readonly></td>
            <td width="4%">&nbsp;</td>
          </tr>
		  <tr class="table-back"> 
            <td width="9%"  align="right">提醒日期：</td>
            <td width="22%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)"  size="12" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12" value="${EndDate}" readonly>
  <a href="javascript:SelectUsers();"></a></td>
            <td width="12%" align="right">&nbsp;</td>
            <td width="23%">&nbsp;</td>
            <td width="7%" align="right">&nbsp;</td>
            <td width="23%">&nbsp;</td>
            <td width="4%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" align="right"></td>
		  </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="80"><a href="javascript:SetAwake();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;设置提醒</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td style="text-align: right;"><pages:pager action="../finance/listLoanObjectAction.do"/></td>
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
            <td width="13%" >借款人</td>
			<td width="29%">累计借款金额</td>
			<td width="15%">累计清算还款金额</td>
            <td width="20%">应还款</td>
            <td width="13%">提醒日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.uid}','${s.uidname}',${s.id});"> 
            <td >${s.uidname}</td>
			<td align="right"><windrp:format value='${s.totalloansum}' p="###,##0.00"/></td>
			<td align="right"><windrp:format value='${s.totalbacksum}' p="###,##0.00"/></td>
            <td align="right"><windrp:format value='${s.waitbacksum}' p="###,##0.00"/></td>
            <td>${s.promisedate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
        
      </table>
      </form>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Loan();"><span>借款</span></a></li>
               <li><a href="javascript:Reckoning();"><span>清算</span></a></li>
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
