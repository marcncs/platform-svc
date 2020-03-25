<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function addNew(){
		popWin("toAddProductProviderAction.do?PDID=${PDID}",900,600);
	}

	function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?") ){
				popWin2("../purchase/delProductProvideAction.do?id="+checkid);
			}
		}else{
			alert("请选择你要操作的记?");
		}
	}
	
	function ProvideDetail(pid){
	popWin("../purchase/providerDetailAction.do?PID="+pid,900,600);
	}


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 供应商比较</td>
        </tr>
      </table>
      <form name="search" method="post" action="productProviderCompareAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	  
        <tr class="table-back">
          <td width="21%"  align="right"><input name="PDID" type="hidden" id="PDID" value="${PDID}">
            供应商行业： </td>
          <td width="27%"><windrp:select key="Vocation" name="Vocation" p="y|d" value="${Vocation}"/></td>
          <td width="11%" align="right">
          <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
        </tr>
		
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
	<td class="SeparatePage"> <pages:pager action="../purchase/productProviderCompareAction.do"/> 
	</td>
	</tr>
</table>
<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="12%">编号</td>
            <td width="17%">供应商</td>
            <td width="14%"> 报价 </td>
            <td width="15%">行业</td>
            <td width="15%">省</td>
            <td width="12%">市</td>
            <td width="15%">区</td>
          </tr>
          <logic:iterate id="p" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td><a href="javascript:ProvideDetail('${p.pid}')">${p.providername}</a></td>
            <td>${p.unitprice}</td>
            <td>${p.vocationname}</td>
            <td>${p.provincename}</td>
            <td>${p.cityname}</td>
            <td>${p.areasname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
