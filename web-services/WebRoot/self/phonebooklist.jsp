<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
		Detail(); 
		}
		
		function addNew(){
			popWin("../self/toAddNewPhoneBookAction.do",1000,600);
		}
	
		function Update(){
			if(checkid>0){
				popWin("../self/toUpdPhoneBookAction.do?ID="+checkid,1000,600);
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function Detail(){
			if(checkid>0){
				document.all.submsg.src="../self/phoneBookDetailAction.do?ID="+checkid;
			}else{
			alert("请选择你要操作的记录!");
			}
		}
		
		function Del(){
			if(checkid>0){
				if(window.confirm("您确认要删除编号为："+checkid+" 的记录吗？如果删除将永远不能恢复!")){
					popWin2("../self/delPhoneBookAction.do?ID="+checkid);
				}
			}else{
			alert("请选择你要操作的记录!");
			}
		}
	
	
		this.onload = function abc(){
				document.getElementById("abc").style.height = (document.body.offsetHeight  - document.getElementById("div1").offsetHeight+50)+"px" ;
			}
			
		function Check(){
			var phone = document.getElementById("Phone");
			if(phone.value != "" && !phone.value.isMobileOrTel()){
				alert("请输入正确的电话号码!");
				return false;
			}
			return true;
		}

			
</SCRIPT>
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" height="27" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									电话薄
									<input type="hidden" name="ID" value="${id}">
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listPhoneBookAction.do"
							onsubmit="return Check();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td  align="right">
									 姓名：
								</td>
								<td>
									<input type="text" name="Name"  value="${Name}">	
								</td>
								<td  align="right">
									电话：
								</td>
								<td>
									<input type="text" name="Phone" value="${Phone}" >	
								</td>
								<td width="12%" align="right">
									关键字：
								</td>
								<td >
									<input type="text" name="KeyWord" value="${KeyWord}" >
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
									<pages:pager action="../self/listPhoneBookAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="9%">
									编号
								</td>
								<td width="15%">
									姓名
								</td>
								<td width="7%">
									性别
								</td>
								<td width="23%">
									电话
								</td>
								<td width="29%">
									手机
								</td>
								<td width="13%">
									分类
								</td>
							</tr>
							<logic:iterate id="pb" name="alpbsls">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${pb.id}')">
									<td>
										${pb.id}
									</td>
									<td title="点击查看详情">
										<a href="javascript:Update();">${pb.name}</a>
									</td>
									<td>
										<windrp:getname key="Sex" value="${pb.sex}" p="f" />
									</td>
									<td>
										${pb.phone}
									</td>
									<td>
										${pb.mobile}
									</td>
									<td>
										${pb.sortname}

									</td>
								</tr>
							</logic:iterate>
							
						</table>
						 
						<br />
						
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>电话本详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
				
									</div>
								</td>
							</tr>
						</table>
	</body>
</html>
