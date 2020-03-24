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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}

	function Affirm(){
	if(checkid!=""){
		location.href("../warehouse/toTransStockAlterMoveAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Hidden(){
		if(checkid!=""){
			popWin2("../ditch/hiddenAlterMoveApplyAction.do?ID="+checkid);
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
          <td > 选择订购申请</td>
        </tr>
      </table>
       <form name="search" method="post" action="selectAlterMoveApplyAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td align="right">预计到货日期：</td>
            <td>
              <input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly> 
			</td>
			<td align="right">申请机构：</td>
			<td>
			<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back"> 
                <td width="90"><a href="javascript:Affirm();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;转为订购单</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
                <td width="69"><a href="javascript:Hidden();"><img src="../images/CN/hidden.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;隐藏</a></td>
          <td  class="SeparatePage"> <pages:pager action="../warehouse/selectAlterMoveApplyAction.do"/> 
        </tr>

</table>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
            <td width="12%" >编号</td>
            <td width="25%">调拨需求日期</td>
            <td width="25%">调出机构</td>
            <td width="25%">调入(制单)机构</td>
			<td width="13%">制单人</td>
		  </tr>
          <logic:iterate id="p" name="alpa" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');" onDblClick="Affirm();"> 
            <td >${p.id}</td>
            <td>${p.movedate}</td>
            <td><windrp:getname key='organ' value='${p.outorganid}' p='d'/></td>
            <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
			<td><windrp:getname key='users' value='${p.makeid}' p='d'/></td>
			</tr>
          </logic:iterate> 
        
      </table>
      </form>


</body>
</html>
