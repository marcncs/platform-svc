<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
var checkcname="";
var submenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
		Adjust();
	}
	
	function addNew(){
	window.open("../finance/toAddCashBankAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Adjust(){
		if(checkid!=""){
			document.all.submsg.src="../finance/listCashBankAdjustAction.do?id="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
			
	}
	
	function Update(){
		if(checkid!=""){
		window.open("toUpdCashBankAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		window.open("../finance/delLoanObjectAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772">账务管理>>现金银行 </td>
        </tr>
      </table>
      <form name="search" method="post" action="listCashBankAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right">所属机构：</td>
            <td width="26%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="8%" align="right">关键字：</td>
            <td width="29%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="9%" align="right">&nbsp;</td>
            <td width="19%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" align="right"></td>
          </tr>
        
      </table>
      </form>
	   <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr class="title-func-back">
           <td width="50"><a href="#" onClick="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
           <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
           <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
           <td style="text-align: right;"><pages:pager action="../finance/listCashBankAction.do"/></td>
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
            <td width="16%" >编号</td>
			<td width="25%">所属机构</td>
			<td width="32%">现金银行名称</td>
			<td width="27%">当前金额</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}','${s.cbname}');"> 
            <td>${s.id}</td>
			<td>${s.makeorganidname}</td>
			<td>${s.cbname}</td>
			<td align="right"><windrp:format value='${s.totalsum}'  p="###,##0.00"/></td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
       
      </table>
       </form>
	  <br>
	  <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Adjust();"><span>现金银行调整</span></a></li>
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
