<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	OrderDetail();
	}

function addNew(){
	window.open("toAddPeddleOrderAction.do","","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdPeddleOrderAction.do?ID="+checkid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../sales/toReferPeddleOrderAction.do?PeddleID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function PeddleOrder(){
		if(checkid!=""){
			window.open("../sales/printPeddleOrderAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Complete(){
		if(checkid!=""){
			window.open("../sales/completePeddleOrderAction.do?slid="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delPeddleOrderAction.do?SOID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function OrderDetail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/peddleOrderDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function CheckPeddleOrder(){
		if(checkid!=""){
		window.open("toCheckPeddleOrderAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

	function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(c==undefined){return;}
		document.search.CID.value=c.cid;
		document.search.cname.value=c.cname;
		}
		
		function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
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
        <td width="772"> 零售管理&gt;&gt;零售单</td>
        <td width="772" valign="bottom">&nbsp;</td>
      </tr>
    </table>
    <form name="search" method="post" action="listPeddleOrderAction.do">
    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="12%"  align="right">会员名称：</td>
            <td width="25%"><input name="CID" type="hidden" id="CID" value="">
              <input name="cname" type="text" id="cname" value="" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
            <td width="9%" align="right">制单机构：</td>
            <td width="22%"><input name="MakeOrganID" type="hidden" id="MakeOrganID">
              <input name="oname" type="text" id="oname" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></td>
            <td width="10%" align="right">制单部门：</td>
            <td width="19%"><input type="hidden" name="MakeDeptID" id="MakeDeptID">
              <input type="text" name="dname" id="dname"
										onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d')"
										value="请选择" readonly></td>
            <td width="3%">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID">
              <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="请选择" readonly></td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12" readonly>
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12" readonly></td>
            <td align="right">是否作废：</td>
            <td><windrp:select key="YesOrNo" name="IsBlankOut" p="y|f" /></td>
            <td>&nbsp;</td>
          </tr>		
		  <tr class="table-back">
            <td  align="right">是否挂帐：</td>
            <td><windrp:select key="YesOrNo" name="IsAccount" p="y|f" /></td>
            <td align="right">是否对账：</td>
            <td><windrp:select key="YesOrNo" name="IsDayBalance" p="y|f" /></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord"></td>
            <td><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </span></td>
		  </tr>		 
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:SaleIndent();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td style="text-align: right;"><pages:pager action="../sales/listPeddleOrderAction.do" />
          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="6%"  >编号</td>
            <td width="22%" >客户名称</td>
            <td width="15%" >制单日期</td>
            <td width="8%" >总金额</td>
			<td width="8%" >是否挂帐</td>
			<td width="8%" >是否日结</td>
            <td width="8%" >是否作废</td>
            <td width="8%" >制单人</td>    
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td class="${s.isblankout==1?'text2-red':''}" >${s.id}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.cname} <a href="../sales/listMemberAction.do?CID=${s.cid}"><img src="../images/CN/go.gif" width="10" height="10" border="0"></a></td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.makedatename}</td>
            <td class="${s.isblankout==1?'text2-red':''}" align="right"><fmt:formatNumber value="${s.totalsum}" pattern="0.00"/></td>
            <td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isaccountname}</td>
			<td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isdaybalancename}</td>
			<td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isblankoutname}</td>
            <td class="${s.isblankout==1?'text2-red':''}" align="center">${s.makeidname} </td>  
          </tr>
          </logic:iterate>
      </table>
	  <br>
      <table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:OrderDetail();">详情</a></td>
    </tr>
</table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
