<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
	  pdmenu = getCookie("warehousecookie");
	 switch(pdmenu){
	 	case "0":Detail(); break;
	 	case "1":WarehoseBit(); break;		
	 	case "2":RuleUserWh(); break;		
		default:Detail();
	 }
	}
	
	function addNew(){
		popWin("toAddWarehouseAction.do?OID=${OID}",700,400);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("toUpdWarehouseAction.do?ID="+checkid+"&OID=${OID}",700,400);
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}
	
	function Detail(){
		setCookie("warehousecookie","0");
		if(checkid!=""){
			document.all.submsg.src="warehouseDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function WarehoseBit(){
		setCookie("warehousecookie","1");
		if(checkid!=""){
			document.all.submsg.src="listWarehouseBitAction.do?WID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function RuleUserWh(){
		setCookie("warehousecookie","2");
		if(checkid!=""){
			document.all.submsg.src="listRuleUserWHAction.do?WID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Safety(){
		if(checkid!=""){
			document.all.submsg.src="../users/listOrganSafetyIntercalateAction.do?WID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Visit(){
		if(checkid!=""){
			popWin("toVisitWarehouseAction.do?ID="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
	if(checkid!=""){
		if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin2("../sys/delWarehouseAction.do?ID="+checkid);
		}
	}else{
	alert("请选择你要操作的记录!");
	}
}

	function ConfScanner(){
		if(checkid!=""){
			popWin("toConfigScannerWarhouseAction.do?ID="+checkid,700,400);
		}else{
		alert("请选择要配置的仓库!");
		}
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
          <td width="772"> 仓库列表</td>
        </tr>
      </table>
       <form name="search" method="post" action="../sys/listWarehouseAction.do">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
        <input type="hidden" name="OID" value="${OID}">
    <tr class="table-back"> 
      <td width="8%"  align="right">关键字：</td>
      <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
      <td align="right"></td>
      <td class="SeparatePage"> <input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
  	
	</table>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			<%--
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
			<%--
            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
			<a href="javascript:Visit();"><img src="../images/CN/xuke.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;许可</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="90" align="center">
			<a href="javascript:ConfScanner();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;配置采集器</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			 --%>			
		  <td class="SeparatePage"><pages:pager action="../sys/listWarehouseAction.do"/></td>						
		</tr>
	</table>
	 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="100px" >编号</td>
            <td width="100px">内部编号</td>
            <td style="width: auto;">仓库名称</td>
            <td width="100px">负责人</td>
            <td width="120px">联系电话</td>
            <td width="70px">仓库属性</td>
            <td width="50px">是否可用</td>
          </tr>
          <logic:iterate id="w" name="wls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${w.id}');"> 
            <td height="20">${w.id}</td>
            <td>${w.nccode }</td>
            <td>${w.warehousename}</td>
            <td>${w.username}</td>
            <td>${w.warehousetel}</td>
            <td><windrp:getname key='WarehouseProperty' value='${w.warehouseproperty}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${w.useflag}' p='f'/></td> 
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>仓库详情</span></a></li>
            <li><a href="javascript:WarehoseBit();"><span>仓位</span></a></li>
            <li><a href="javascript:RuleUserWh();"><span>管辖用户</span></a></li>
          </ul>
        </div>
      </div>	
	  
    </td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'phonebook','listdiv')"></IFRAME>
</body>
</html>
