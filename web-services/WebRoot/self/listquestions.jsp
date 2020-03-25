<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDateTime.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/selectduw.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
			this.onload = function abc(){
				document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
			}
	
			var checkid=0;
			function CheckedObj(obj,objid){
				for(i=0; i<obj.parentNode.childNodes.length; i++)
				{
				   obj.parentNode.childNodes[i].className="table-back-colorbar";
				}
				
				obj.className="event";
				checkid=objid;
				submenu = getCookie("CookieProvide");
				 switch(submenu){
				 	//case 0: Detail() break
					case "1":Detail(); break;
					case "2":respond(); break;
					Detail();
				 }
			}
	
			function addNew(){
				popWin("../self/toAddQuestionsAction.do",1000,600);
			}

			function Update(){
				if(checkid>0){
					popWin("../self/toUpdQuestionsAction.do?ID="+checkid,1000,600);
				}else{
					alert("请选择你要操作的记录!");
				}
			}
	
			function Del(){
				if(checkid>0){
					if(window.confirm("您确认要删除编号为："+checkid+" 的常见问题吗？如果删除将永远不能恢复!")){
						popWin2("../self/delQuestionsAction.do?ID="+checkid);
					}
				}else{
					alert("请选择你要操作的记录!");
				}
			}
	
			function Detail(){
			setCookie("CookieProvide","1");
				if(checkid>0){
				document.all.submsg.src="../self/detailQuestionsAction.do?ID="+checkid;
				}else{
				alert("请选择你要操作的记录!");
				}
			}
			
			function respond(){
			setCookie("CookieProvide","2");
				if(checkid>0){
					document.all.submsg.src="../self/listRespondAction.do?QID="+checkid;
				}else{
					alert("请选择你要操作的记录!");
				}
			}
			function SelectOrgan(){
				var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
				if ( p==undefined){return;}
					document.search.MakeOrganID.value=p.id;
					document.search.oname.value=p.organname;
					clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
			}
	
	</script>
	</head>

	<body>
		<div id="div1">
			<table width="100%" height="40" border="0" cellpadding="0"
				cellspacing="0" class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						我的办公桌&gt;&gt;常见问题
					</td>
				</tr>
			</table>
<form name="search" method="post" action="listQuestionsAction.do">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="table-back">
				
				<tr class="table-back">
					<td align="right">
						制单机构：
					</td>
					<td>
						<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30"  value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle">
									</a>
					</td>
					<td align="right">
						制单部门：
					</td>
					<td>
						<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
						<input type="text" name="deptname" id="deptname"
							onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
							value="${deptname}" readonly>
					</td>
					<td align="right">
						制单人：
					</td>
					<td>
						<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">

						<input type="text" name="uname" id="uname"
							onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
							value="${uname}" readonly>
					</td>
				</tr>
				<tr>
					<td align="right">
						制单日期：
					</td>
					<td>
						<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" 
										onFocus="javascript:selectDate(this)" readonly>
									-
						<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" 
							onFocus="javascript:selectDate(this)" readonly>
					</td>
					<td width="8%" align="right">
						关键字：
					</td>
					<td width="32%">
						<input type="text" name="Content" value="${Content}">
					</td>
					<td></td>
					<td style="text-align: right;">
						<input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
					</td>
				</tr>
				
			</table>
			</form>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="title-func-back">
					<td width="50">
						<a href="#" onClick="javascript:addNew();"><img
								src="../images/CN/addnew.gif" width="16" height="16" border="0"
								align="absmiddle">&nbsp;新增</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td width="50">
						<a href="javascript:Update();"><img
								src="../images/CN/update.gif" width="16" height="16" border="0"
								align="absmiddle">&nbsp;修改</a>
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
					<td style="text-align: right;">
						<pages:pager action="../self/listQuestionsAction.do" />
					</td>
				</tr>
			</table>
		</div>
		<div id="abc" style="overflow-y: auto; height: 600px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
				<tr align="center" class="title-top">
					<td width="8%">
						编号
					</td>
					<td width="15%">
						标题
					</td>
					<td width="15%">
						内容
					</td>
					<td width="8%">
						回帖数量
					</td>
					<td style="width: 15%;">
						制单机构
					</td>
					<td style="width: 12%;">
						制单部门
					</td>
					<td style="width: 12%;">
						制单人
					</td>
					<td style="width: 12%;">
						制单时间
					</td>
				</tr>
				<logic:iterate id="q" name="list">
					<tr class="table-back-colorbar" onClick="CheckedObj(this,${q.id});">
						<td>
							${q.id }
						</td>
						<td><div title="${q.title}" style="width:150; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${q.title}</div>
							
						</td>
						<td><div title="${q.content}" style="width:150; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${q.content}</div>
							
						</td>
						<td>
							${q.number }
						</td>
						<td>
							<windrp:getname key='organ' value='${q.makeorganid}' p='d' />
						</td>
						<td>
							<windrp:getname key='dept' value='${q.makedeptid}' p='d' />
						</td>
						<td>
							<windrp:getname key='users' value='${q.makeid}' p='d' />
						</td>
						<td>
							<windrp:dateformat value='${q.makedate}' p='yyyy-MM-dd' />
						</td>
					</tr>
				</logic:iterate>
			</table>
			<br />
			
				<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>问题详情</span></a></li>
               <li><a href="javascript:respond();"><span>解决方案</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
			
		</div>

	</body>
</html>
