<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
	
	function addNewCalendar(){	
		popWin("addnewcalendar.do",1000,600);

	}
	
	function Update(){
		if(checkid>0){
			popWin("updCalendarAction.do?ID="+checkid,1000,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
	if(checkid>0){
		if(confirm("你确定要删除编号为："+checkid+" 的日程吗？")==true){
			popWin2("../self/delCalendarAction.do?ID="+checkid);
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
				<div id="div1">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								当日日程
							</td>
						</tr>
					</table>
					<form name="search" method="post"
							action="listcurdaycalendar.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="11%" align="right">
								提醒日期：
							</td>
							<td width="35%">
								<input name="BeginDate" type="text" id="BeginDate"
									value="${BeginDate}" size="10"
									onFocus="javascript:selectDate(this)" readonly="readonly">
								-
								<input name="EndDate" type="text" id="EndDate"
									value="${EndDate}" size="10"
									onFocus="javascript:selectDate(this)" readonly="readonly">
							</td>
							<td width="12%" align="right">
								内容：
							</td>
							<td width="42%">
								<input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}">
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
								<a href="javascript:addNewCalendar();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td width="50">
								<a href="javascript:Update();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td width="50">
								<a href="javascript:Del()"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td width="60" align="center">
								<a href="../self/listAllCalendarAction.do">所有日程</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellspacing="1" class="sortable">
						<tr class="title-top-lock">
							<td width="20">&nbsp;</td>
							<td width="80">编号</td>
							<td width="150">
								提醒时间
							</td>
							<td>
								内容
							</td>
						</tr>
						<logic:iterate id="cda" name="curDayAwake">
							<tr class="table-back-colorbar"
								onClick="CheckedObj(this,${cda.id});">
								<td width="10">
									<c:choose>
										<c:when test="${cda.isawake==0}">
											<IMG SRC="../images/CN/colok.gif" WIDTH="17" HEIGHT="16"
												BORDER="0" ALT="">
										</c:when>
										<c:otherwise>
											<IMG SRC="../images/CN/yes.gif" WIDTH="14" HEIGHT="12"
												BORDER="0" ALT="">
										</c:otherwise>
									</c:choose>
								</td>
								<td>${cda.id}</td>
								<td width="150">
									<windrp:dateformat value='${cda.awakedatetime}'
										p='yyyy-MM-dd HH:mm:ss' />
								</td>
								<td>
									${cda.awakecontent}
								</td>
							</tr>
						</logic:iterate>
					</table>
				</div>
				</td>
			</tr>
		</table>
	</body>
</html>
