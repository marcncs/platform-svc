<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDateTime.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
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
		popWin("../self/toAddDocAction.do",1000,600);
	}

	function Update(){
		if(checkid>0){
			popWin("../self/toUpdDocAction.do?ID="+checkid,1000,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除编号为："+checkid+" 的文件吗？如果删除将永远不能恢复!")){
			popWin2("../self/delDocAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}

</script>
	</head>

	<body>
		<div id="div1">
			<table width="100%" height="27" border="0" cellpadding="0"
				cellspacing="0" class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						文件列表
						<input type="hidden" name="ID" value="${id}">
					</td>
				</tr>
			</table>
			<form name="search" method="post" action="listDocAction.do">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				<tr class="table-back">
					<td align="right">
						关键字：
					</td>
					<td>
						<input type="text" name="KeyWord" value="${KeyWord}">
					</td>
					<td align="right">
						时间段：
					</td>
					<td>
						<input type="text" name="BeginDate" value="${BeginDate}" size="10"
							onFocus="javascript:selectDate(this)" readonly="readonly">
						-
						<input type="text" name="EndDate" value="${EndDate}" size="10"
							onFocus="javascript:selectDate(this)" readonly="readonly">
					</td>
					<td class="SeparatePage">
						<input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
					</td>
				</tr>
				
			</table>
			</form>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="title-func-back">
					<td width="50">
						<a href="javascript:addNew();"><img
								src="../images/CN/addnew.gif" width="16" height="16" border="0"
								align="absmiddle">&nbsp;新增</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td width="50">
						<a href="javascript:Del()"><img src="../images/CN/delete.gif"
								width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td class="SeparatePage">
						<pages:pager action="../self/listDocAction.do" />
					</td>
				</tr>
			</table>
		</div>
		<div id="abc" style="overflow-y: auto; height: 600px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
				<tr align="center" class="title-top">
					<td width="6%">
						编号
					</td>
					<td >
						下载
					</td>
					<td>
						文件描述
					</td>
					<td width="15%">
						创建日期
					</td>
					<td width="11%">
						创建人
					</td>
					<td width="14%">
						文件类型
					</td>
				</tr>
				<logic:iterate id="pb" name="alpbsls">
					<tr class="table-back-colorbar"
						onClick="CheckedObj(this,${pb.id});">
						<td>
							${pb.id}
						</td>
						<td title="点击右键下载">
							<img src="../images/CN/beizheng.gif" border="0">
							<a href="../common/downloadfile.jsp?filename=${pb.realpathname}">${pb.realpathname }</a>
					  </td>
						<td>
							<div title="${pb.describe}">${pb.describe}</div>
						</td>
						<td>
							<windrp:dateformat value="${pb.makedate}" p="yyyy-MM-dd" />
						</td>
						<td>
							${pb.makeidname}
						</td>
						<td>
							${pb.sortname}
						</td>
					</tr>
				</logic:iterate>
			</table>
		</div>
	</body>
</html>
