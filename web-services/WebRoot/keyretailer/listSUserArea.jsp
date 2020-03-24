<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印任务列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript">
//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
var checkid="";
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function addNew(){
	if("${pid}" == -1) {
		alert("请选择你要修改的区域!");
		return;
	}
	popWin("../keyretailer/toAddSUserAreaAction.do?pid=${pid}",1000,650);
}
function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的设置吗?") ){
				popWin2("../keyretailer/delSUserAreaAction.do?id="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function Import(){ 
  	popWin("../keyretailer/toImportSUserAreaAction.do",500,300);
}
function OutPut(){
	search.target="";
	search.action="../keyretailer/exportSUserAreaAction.do";
	search.submit();
	search.action="../keyretailer/listSUserAreaAction.do";
}
</script>
  </head>
  
  <body>
    <table width="100%" height="50%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									区域用户
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listSUserAreaAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<input type="hidden" name="pid" value="${pid}">
							<tr class="table-back">
								<td width="9%" align="right">
									关键字:
								</td>
								<td width="20%">
									<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<ws:hasAuth operationName="/keyretailer/toAddSUserAreaAction.do">
									<td width="50">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<%--								
								<ws:hasAuth operationName="/keyretailer/toUpdSBonusSettingAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdSetting();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>--%>
								<ws:hasAuth operationName="/keyretailer/delSUserAreaAction.do">
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">删除 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<%--								
								<ws:hasAuth operationName="/sap/excPutPrintJobAction.do">
								<td width="50">
									<a href="javascript:excput()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>--%>
								<ws:hasAuth operationName="/keyretailer/exportSUserAreaAction.do">
								<td width="50">
									<a href="javascript:OutPut()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/toImportSUserAreaAction.do">
								<td width="50">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">导入 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage"> 
									<pages:pager action="../keyretailer/listSUserAreaAction.do" />
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 100%;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							
							<tr align="center" class="title-top">
								<td >
									用户编号
								</td>
								<td >
									用户名
								</td>
								<td >
									用户姓名
								</td>
								<td >
									用户类型
								</td>
								<td >
									所属机构
								</td>
								<td >
									区域代码
								</td>
								<td >
									区域名称
								</td>
							</tr>
							<logic:iterate id="pj" name="sUserAreas">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}');">
									<td>
										${pj.userid}
									</td>
									
									<td>
										${pj.loginname}
									</td>
									<td>
										${pj.realname}
									</td>
									<td>
										${pj.userTypeName}
									</td>
									<td>
										${pj.organname}
									</td>
									<td>
										${pj.areaid}
									</td>
									<td>
										${pj.areaname}
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
