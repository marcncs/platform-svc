<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT> 
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-5)+"px";
}

var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function start(){
		if(checkid!=""){
			if ( confirm("你确认要运行编号为:"+checkid+"的任务吗?") ){
				popWin("../sys/operateTaskAction.do?operateId=0&ID="+checkid,500,300);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function stop(){
		if(checkid!=""){
			if ( confirm("你确认要挂起编号为:"+checkid+"的任务吗?") ){
				popWin("../sys/operateTaskAction.do?operateId=1&ID="+checkid,500,300);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function update(){
		if(checkid!=""){
			popWin("../sys/toUpdTaskAction.do?ID="+checkid,500,350);
		}else{
			alert("请选择你要操作的记录!");
		}
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">${menusTrace }</td>
        </tr>
      </table>
       <form name="search" method="post" action="../sys/listTaskAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td align="right" width="10%">任务名称：</td>
			<td align="left" width="60%"><input name="taskName" type="text" id="taskName" value="${taskName}"></td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<ws:hasAuth operationName="/sys/toUpdTaskAction.do">
			<td width="50" >
				<a  href="javascript:update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;设置</a>
			</td>		
			<td width="50" >
				<a  href="javascript:start();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;运行</a>
			</td>		
			<td width="50" >
				<a  href="javascript:stop();"><img src="../images/CN/download.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;挂起</a>
			</td>		
			</ws:hasAuth>
		  <td class="SeparatePage"><pages:pager action="../sys/listTaskAction.do"/></td>
		</tr>
	 </table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%"  cellpadding="0" cellspacing="1" >
        
          <tr align="center" class="title-top-lock"> 
            <td width="5%" >任务编号</td>
            <td width="20%">任务名称</td>
			<td width="5%">执行方式</td>
            <td width="15%">执行策略</td>
			<td width="20%" >任务描述</td>
			<td width="5%" >状态(内存)</td>
			<td width="5%" >状态(数据库)</td>
          </tr>
          <logic:iterate id="u" name="tList" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
            <td>${u.id}</td>
            <td>${u.taskName}</td>
            <td><windrp:getname key='taskType' value='${u.type}' p='d' /></td>
            <td>${u.displayName}</td>
            <td>${u.remark}</td>
            <td>${u.statusDisplay}</td>
            <td><windrp:getname key='taskStatus' value='${u.status}' p='d' /></td>
            </tr>
          </logic:iterate> 
       
    	</table>    
       </form>
	  </div> 
    </td>
  </tr>
</table>
</body>
</html>
