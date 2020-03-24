<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}

	function addNew(){
		popWin("../sales/toAddLinkManAction.do",900,400);
	}

	function Update(){
		if(checkid>0){
			popWin("updLinkManAction.do?id="+checkid,900,400);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="linkManDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的联系人记录吗？如果删除将永远不能恢复!")){
				popWin2("../sales/delLinkmanAction.do?LID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>

					<table width="100%" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								联系人
							</td>
						</tr>
					</table>
<form name="search" method="post" action="listLinkManAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="8%" align="right">
								关键字：
							</td>
							<td>
								<input type="text" name="KeyWord" value="${KeyWord}">
								
							</td>
							<td align="right">
								<INPUT TYPE="hidden" name="Cid" value="${cid}">
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
							<td class="SeparatePage">
								<pages:pager action="../sales/listLinkManAction.do" />
						</tr>
					</table>
<FORM METHOD="POST" name="listform" ACTION="">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						
						<tr align="center" class="title-top">
							<td width="5%">编号 </td>
							<td width="20%">
								联系人姓名
							</td>
							<td width="8%">
								联系人性别
							</td>
							<td width="15%">
								部门
							</td>
							<td width="12%">
								职务
							</td>
							<td width="16%">
								办公电话
							</td>
							<td width="14%">
								手机
							</td>
							<td width="10%">
								是否主联系人
							</td>
						</tr>
						<logic:present name="usList">
							<c:set var="count" value="0" />
							<logic:iterate id="l" name="usList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${l.id});">
									<td>
										${l.id}
									</td>
									<td>
										${l.name}
									</td>
									<td>
										${l.sex}
									</td>
									<td>
										${l.department}
									</td>
									<td>
										${l.duty}
									</td>
									<td>
										${l.officetel}
									</td>
									<td>
										${l.mobile}
									</td>
									<td>
										${l.ismain}
									</td>
								</tr>
								<c:set var="count" value="${count+1}" />
							</logic:iterate>
						</logic:present>
						
					</table>
					</form>
					<br />
					
					<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>联系人详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
					
	</body>
</html>
