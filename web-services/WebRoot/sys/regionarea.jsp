<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	function CheckedObj(obj,objid,objcname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 
	}

	function addNew(psid){
		if(psid){
			popWin("../sys/toAddRegionAreaAction.do?PSID="+psid,900,500);
		}else{
			alert("请选择区域!");
		}
	}
	
	function Del(psid){
		if(psid){
			var pidStr = "";
			var pids = document.getElementsByName("pid");
			var count = 0;
			for(var i=0;i<pids.length;i++ ) {
				if(pids[i].checked) {
					count++;
					pidStr = pidStr + pids[i].value + ","
				}
			}
			if( count<=0 ){
				alert("请选择要解除关联的记录!");
			} else {
				if ( confirm("你确认要解除关联吗?") ){
					pidStr = pidStr.substring(0, pidStr.length -1);
					popWin2("../sys/delRegionAreaAction.do?pid="+pidStr);
				}
			}
		}else{
			alert("请选择区域!");
		}
	}
	
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}	
 //全选框点击事件
	function checkAll(){ 
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
		 
	}
 	 
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
		<div id="bodydiv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">行政区域
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
      <form name="search" method="post" action="../sys/listRegionAreaAction.do">
      <input type="hidden" name="OtherKey" value="${OtherKey}">
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
  
  	<tr class="table-back"> 
      <td width="8%"  align="right">关键字：</td>
      <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="30"></td>
	<td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
</table> 
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<ws:hasAuth operationName="/sys/toAddRegionAreaAction.do">
		<td width="100"><a href="javascript:addNew('${OtherKey}');"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;关联行政区域</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/sys/delRegionAreaAction.do">
		<td width="70"><a href="javascript:Del('${OtherKey}');"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;解除关联</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<td class="SeparatePage"> <pages:pager action="../sys/listRegionAreaAction.do"/> 
	</td>
	</tr>
</table>
	  </div>
	</td>
</tr>
<tr>
	<td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
          	<td width="16px" class="sorttable_nosort"><input type="checkbox" id="checkAll" onclick="checkAll()"></td>
            <td>行政区域编号</td>
            <td>行政区域名称</td>
          </tr>         
          <logic:iterate id="p" name="regionAreas" > 
          <tr class="table-back-colorbar"> 
            <td width="16px"><input type="checkbox" value="${p.id}" name="pid"></td>
            <td>${p.areaid}</td>
            <td>${p.areaname}</td>
          </tr>
          </logic:iterate> 
      </table>
      <br>
	  </div>
	  </td>
  </tr>
</table> 
</body>
</html>
