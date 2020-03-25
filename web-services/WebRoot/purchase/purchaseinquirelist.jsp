<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	//Detail();
	}
	
	function addNew(){
		popWin("toAddPurchaseInquireAction.do",900,600);
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="purchaseInquireDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

	function SelectProvide(){
	var p=showModalDialog("toSelectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
</script>

</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td> 采购询价记录 </td>
        </tr>
      </table>
       <form name="search" method="post" action="listPurchaseInquireAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	 
        <tr class="table-back"> 
          <td width="9%"  align="right">询价日期：</td>
          <td width="26%" ><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}"  size="12" onFocus="javascript:selectDate(this)" readonly>
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)" readonly></td>
        <td width="8%"  align="right">是否复核：</td>
          <td width="24%" ><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
          <td width="8%" align="right">关键字：</td>
          <td width="25%"><input type="text" name="KeyWord" value="${KeyWord}">
            </td>
            <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
        </tr>
		
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td  class="SeparatePage"><pages:pager action="../purchase/listPurchaseInquireAction.do"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="40">编号</td>
            <td>询价标题</td>
            <td >供应商</td>
            <td >供应商联系人</td>
            <td width="100">制单日期</td>
            <td width="60"> 有效天数 </td>
            <td > 制单人 </td>
            <td width="60">是否复核</td>
          </tr>
          <logic:iterate id="p" name="alpi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${p.id});"> 
            <td ><a href="purchaseInquireDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.inquiretitle}</td>
            <td>${p.providename}</td>
            <td>${p.plinkman}</td>
            <td>
            	<windrp:dateformat value="${p.makedate}" p="yyyy-MM-dd"/>
            </td>
            <td>${p.validdate}</td>
            <td><windrp:getname key='users' value='${p.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${p.isaudit}' p='f'/></td>
          </tr>
          </logic:iterate> 
      </table>
    </td>
  </tr>
</table>
</body>
</html>
