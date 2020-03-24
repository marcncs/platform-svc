<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
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
	
	function addNew(){
	popWin("../erp/toAddUnitInfoAction.do",900,600);
	}
	
	function UpdUnitInfo(){
		if(checkid>0){
		popWin("../erp/toUpdUnitInfoAction.do?ID="+checkid,900,600);
		}else{
		alert("请选择你要操作的记录!");
		}
		
	}
	
	function UserRole(){
		if(checkid>0){
		
		popWin("listUserRoleAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function ResetPWD(){
		if(checkid>0){
			popWin("toResetPwdAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function AssignAreas(){
		if(checkid>0){
		popWin("toAssignAreasAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
		
	}
	
	function Detail(){
	setCookie("UsMenu","0");
		if(checkid>0){
			document.all.submsg.src="listUsersDetailAction.do?userid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function UserVisit(){
	setCookie("UsMenu","1");
		if(checkid>0){
			document.all.submsg.src="..${ruleAuthUrl }?userid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	function MoveOrgan(){
	setCookie("UsMenu","2");
		if(checkid>0){
			document.all.submsg.src="listMoveCanuseOrganAction.do?UIDI="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function organVisit(){
		setCookie("UsMenu","3");
			if(checkid>0){
				document.all.submsg.src="..${visitAuthUrl }?userid="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
	function SetCall(){
		if(checkid>0){
			popWin("listUserCallEventAction.do?muid="+checkid,700,400);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if ( confirm("你确认要删除编号为:"+checkid+"的托盘信息吗?如果删除将不能恢复!") ){
				popWin2("../erp/delUnitInfoAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.organId.value=p.id;
	document.search.oname.value=p.organname;
	//document.search.MakeDeptID.value="";
	//document.search.deptname.value="";

}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=toller",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.search.organId.value;
			document.search.organId.value=p.id;
			document.search.oname.value=p.organname;

		if(oldOrgan != p.id) {
			clearProductList()
		}
	}


function ConfigScanner(){
	if(checkid>0){
		popWin("../sys/toConfigScannerUserAction.do?ID="+checkid,700,400);
	}else{
	alert("请选择要配置的用户!");
	}
}
function Import(){		
	popWin("../users/toImportUsersAction.do",500,300);
}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listUnitInfoAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="8%" align="right"> 
										工厂： 
									</td>
									<td width="26%">
										<input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td width="8%" align="right">
										关键字：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td colspan="3" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/erp/toAddUnitInfoAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/toUpdUnitInfoAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:UpdUnitInfo();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;修改</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/delUnitInfoAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Del();"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../erp/listUnitInfoAction.do" />
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<td>
										编号
									</td>
									<td>
										工厂
									</td>
									<td>
										产品编码
									</td>
									<td>
										产品物料号
									</td>
									<td>
										规格明细
									</td>
									<td>
										产品名称
									</td>
									<td>
										单位
									</td>
									<td>
										对应数量
									</td>
									<td>
										标签类型
									</td>
									<td>
										修改日期
									</td>
									<td>
										修改人员
									</td>
									<td>
										是否可用
									</td>
									<td>
										是否需要分装
									</td>
									<td>
										是否生成暗码
									</td>
								</tr> 
								<logic:iterate id="c" name="configs">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${c.id});">
										<td align="left">
											${c.id}
										</td>
										<td align="left">
											<windrp:getname key='organ' value='${c.organId}' p='d' />
										</td>
										<td align="left">
											${c.productId}
										</td>
										<td align="left">
											${c.mcode}
										</td>
										<td align="left">
											${c.specmode}
										</td>
										<td align="left">
											${c.productName}
										</td>
										<td align="left">
											<windrp:getname key='CountUnit' value='${c.unitId}' p='d'/>
										</td>
										<td align="left">
											${c.unitCount}
										</td>
										
										<td align="left">
											<c:if test="${c.labelType!=null}">
												<windrp:getname key='labelType' value='${c.labelType}' p='f'/>
											</c:if>
										</td>
										
										<td align="left">
											${c.formatDate}
										</td>
										<td align="left">
											<windrp:getname key='users' value='${c.modifiedUserID}' p='d'/>
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.isactive}' p='f'/>
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.needRepackage}' p='f'/>
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.needCovertCode}' p='f'/>
										</td>
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
