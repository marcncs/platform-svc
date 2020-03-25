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
function Delete(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin("../sap/delPrintJobAction.do?printJobId="+checkid,500,250);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
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
	popWin("../keyretailer/toAddSalesAreaCountryAction.do?pid=${pid}",1000,650);
}

function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的设置吗?") ){
				popWin2("../keyretailer/delSalesAreaCountryAction.do?id="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function Import(){ 
  	popWin("../keyretailer/toImportSalesAreaCountryAction.do",500,300);
}
function OutPut(){
	search.target="";
	search.action="../keyretailer/exportSalesAreaCountryAction.do";
	search.submit();
	search.action="../keyretailer/listSalesAreaCountryAction.do";
}
</script>
  </head>
  
  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
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
									区域机构
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listSalesAreaCountryAction.do">
						<input type="hidden" name="pid" value="${pid}">
						<input type="hidden" name="rank" value="${rank}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
						<form name="exportExcel" method="post" action="excPutSBonusSettingAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<c:if test="${rank == 2}">
									<ws:hasAuth operationName="/keyretailer/toAddSalesAreaCountryAction.do">
									<td width="100">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;关联行政区域</a>
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
								</c:if>
								<ws:hasAuth operationName="/keyretailer/delSalesAreaCountryAction.do">
									<td width="80">
										<a href="javascript:Del()"><img
												src="../images/CN/delete.gif" width="16" height="16"
												border="0" align="absmiddle">解除关联 </a>
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
								<ws:hasAuth operationName="/keyretailer/exportSalesAreaCountryAction.do">
								<td width="50">
									<a href="javascript:OutPut()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<%--<ws:hasAuth operationName="/keyretailer/toImportSalesAreaCountryAction.do">
								<td width="50">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">导入 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								--%><td class="SeparatePage"> 
									<pages:pager action="../keyretailer/listSalesAreaCountryAction.do" />
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
								<td>
									大区
								</td>
								<td>
									地区
								</td>
								<td>
									小区
								</td>
								<td>
									省
								</td>
								<td>
									市
								</td>
								<td>
									区/县
								</td>
							</tr>
							<logic:iterate id="pj" name="salesAreaCountries">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}');">
									<td>
										${pj.name1}
									</td>
									<td>
										${pj.name2}
									</td>
									<td>
										${pj.name3}
									</td>
									<td>
										${pj.province}
									</td>
									<td>
										${pj.city}
									</td>
									<td>
										${pj.area}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
