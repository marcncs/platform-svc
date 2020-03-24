<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/selectduw.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
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

			//新增
			function addNew(){
				popWin("toAddUserTargetAction.do",800,300);
			}
			
			//修改
			function Update(){
				if(checkid>0){
					var TargetType = document.search.TargetType.value;
					popWin("toUpdUserTargetAction.do?objid="+checkid + "&TargetType=" + TargetType,800,300);
				}else{
					alert("请选择你要操作的记录!");
				}
			}
			//删除
			function Del(){
				if(checkid>0){
				if(window.confirm("您确认要删该记录吗？如果删除将永远不能恢复!")){
					var TargetType = document.search.TargetType.value;
					popWin2("delUserTargetAction.do?tid="+checkid + "&TargetType=" + TargetType);
					}
				}else{
					alert("请选择你要操作的记录!");
				}
			}
			function SelectName(){
				var objsort = document.search.objsort;
				SelectCustomer();
			}
		
			function SelectCustomer(){
				var u=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
				if(u==undefined){return;}
				document.search.uid.value=u.uid;
				document.search.uname.value=u.loginname;
			}
		
		
	
			this.onload = function abc(){
				document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
			}
			
			//导入
			function Import(){
				popWin("../users/toImportUserTargetAction.do",500,300);
			}

</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
									系统设置>>指标设置
								</td>
							</tr>
						</table>
					<form name="search" method="post" action="listUserTargetAction.do">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" height="20" align="right">
									<input type="hidden" name="objsort" id="objsort"
										value="${objsort}">
									
									指标类型：
								</td>
								<td width="27%">
									<select name="TargetType" dataType="Require" msg="必须选择文件类型!">
									    <logic:iterate id="type" name="typelist">
						          			<option value="${type.tagsubkey }" <c:if test="${type.tagsubkey==TargetType }">selected="selected"</c:if>>${type.tagsubvalue }</option>
						          		</logic:iterate>
									</select>
								
								<!-- 
									<windrp:select key="TargetType" name="TargetType" p="y|f" value="${TargetType}"/>
									
									<input name="uid" type="hidden" id="uid" value="${uid}">
									<input name="uname" type="text" id="uname"  value="${uname}"
										readonly><a href="javascript:SelectName();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
									-->
								</td>
								
								<td align="right">关键字：</td>
								<td><input type="text" name="KeyWord" maxlength="30" value="${KeyWord}"/></td>
								
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
									<a href="#" onClick="javascript:addNew();"><img
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
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								
								<td width="50">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;导入 </a>
								</td>

								<td class="SeparatePage">
									<pages:pager action="../users/listUserTargetAction.do" />
								</td>

							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td >
									对象编号
								</td>
								<td >
									对象名称
								</td>
								<td >
									对象类型
								</td>
								<td>
									有效日期
								</td>
								<td>
									进口粉指标
								</td>
								<td>
									国产成人粉指标
								</td>
								<td>
									国产婴儿粉指标
								</td>
							</tr>
							<logic:iterate id="ut" name="utList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${ut.id});">
									<td>
										${ut.objcode}
									</td>
									<td>
										${ut.objname}
									</td>
									<td>
										${ut.targettypename}
									</td>
									<td>
										${ut.usefuldate}
									</td>
									<td>
										${ut.importtarget}
									</td>
									<td>
										${ut.chmantarget}
									</td>
									<td>
										${ut.chbabytarget}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</div>
						<br>
				</td>
			</tr>
		</table>

	</body>
</html>
