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
var checkStatus="";
var materialCode=""
var primaryCodeStatus=""
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
function SelectOrgan(){
		var p=showModalDialog("../sap/selectPlantAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.plantCode.value=p.oecode;
			document.search.plantName.value=p.organname;
	}
function SelectProduct(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.materialCode.value=p.mCode;
			document.search.materialName.value=p.productname;
		}
function Detail(){
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="listPrintJobDetailAction.do?actionType=1&ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function PrintHistory(){
	setCookie("oCookie","2");
		if(checkid!=""){
			document.all.basic.src="listPrintJobDetailAction.do?actionType=2&ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function genCommonCode() {
	if(checkid!=""){
		if(primaryCodeStatus == -1) {
			alert("该任务无需生成小包装码!");
			return;
		} else if(primaryCodeStatus == 0) {
			alert("该任务小包装码还未生成!");
			return;
		}
		popWin("toGenerateCommonCode.do?ID="+checkid+"&mCode="+ materialCode,500,250);
	}else{
		alert("请选择你要操作的记录!");
	}
}
function CommonCode() {
	setCookie("oCookie","3");
	if(checkid!=""){
		document.all.basic.src="listCommonCodeLogAction.do?ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}
function CartonCode() {
	setCookie("oCookie","4");
	if(checkid!=""){
		document.all.basic.src="listPrintJobDetailAction.do?actionType=3&ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}
function excput(){
	if(checkid!=""){
		exportExcel.target="";
		exportExcel.action="excPutPrintJobAction.do?printJobId="+checkid;
		exportExcel.submit();
	}else{
		alert("请选择你要操作的记录!");
	}
}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function addNew(){
	popWin("../keyretailer/toAddSBonusSettingAction.do",1000,650);
}
function UpdSetting(){
	if(checkid!=""){
		popWin("../keyretailer/toUpdSBonusSettingAction.do?id="+checkid,1000,450);		
	}else{
		alert("请选择你要修改的记录!");
	}
}
function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的设置吗?") ){
				popWin2("../keyretailer/delSBonusSettingAction.do?id="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
function SelectSingleProductName(){
		var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		$j("#productName").val(p.productname);
	}
function Import(){ 
  	popWin("../keyretailer/toImportSBonusSettingAction.do",500,300);
}
function OutPut(){
	search.target="";
	search.action="../keyretailer/exportSBonusSettingAction.do";
	search.submit();
	search.action="../keyretailer/listSBonusSettingAction.do";
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
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listSBonusSettingAction.do"
								>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									产品名称：
								</td>
								<td width="25%">
									<input id="productName" name="productName"
														value="${productName}" readonly>
													<input type="hidden" name="countUnit" id="countUnit"
														value="${countUnit}">
													<a href="javascript:SelectSingleProductName();"><img
															src="../images/CN/find.gif" align="absmiddle" border="0">
													</a>
								</td>
								<td align="right">
									规格：
								</td>
								<td>
									<input type="hidden" name="spec" id="spec"
														value="${spec}">
													<input type="text" name="packsizename" id="packsizename"
														value="${spec}"
														onClick="selectDUW(this,'spec',$F('productName'),'psn','')"
														readonly>

								</td>
								<td width="10%" align="right">
									账户类型
								</td>
								<td width="26%">
									<windrp:select key="AccountType" name="accountType" value="${accountType}" p="y|f" />
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td align="right">
									是否激活：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="activeFlag" value="${activeFlag}" p="y|f" />
								</td>
								<td width="9%" align="right">
									关键字:
								</td>
								<td width="20%">
									<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
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
								<ws:hasAuth operationName="/keyretailer/toAddSBonusSettingAction.do">
									<td width="50">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/toUpdSBonusSettingAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdSetting();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/delSBonusSettingAction.do">
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
								<ws:hasAuth operationName="/keyretailer/exportSBonusSettingAction.do">
								<td width="50">
									<a href="javascript:OutPut()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/toImportSBonusSettingAction.do">
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
									<pages:pager action="../keyretailer/listSBonusSettingAction.do" />
								</td>
							</tr>
							</tbody>
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
								<td >
									年
								</td>
								<td >
									截止月份
								</td>
								<td >
									产品名称
								</td>
								<td >
									规格
								</td>
								<td >
									积分点
								</td>
								<td >
									计量单位数量
								</td>
								<td >
									计量单位
								</td>
								<td >
									账户类型
								</td>
								<td >
									是否激活
								</td>
								<td >
									最后修改日期
								</td>
							</tr>
							<logic:iterate id="pj" name="sBonusSettings">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}');">
									<td>
										${pj.year}
									</td>
									
									<td>
										${pj.month}
									</td>
									<td>
										${pj.productName}
									</td>
									<td>
										${pj.spec}
									</td>
									<td>
										<fmt:formatNumber value="${pj.bonusPoint}" pattern="#0.##"></fmt:formatNumber>
										
									</td>
									<td>
										${pj.amount}
									</td>
									<td>
										${pj.countUnitName}
									</td>
									<td>
										<windrp:getname key='AccountType' value='${pj.accountType}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${pj.activeFlag}' p='f' />
									</td>
									<td>
										${pj.modifiedDate}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
<%--						
						<div id="tabs1">
								<ul>
									<li><a id="DetailUrl" href="javascript:Detail();"><span>详细信息</span></a></li>
									<li><a id="PrintHistiryUrl" href="javascript:PrintHistory();"><span>打印历史</span></a></li>
									<li><a id="CommonCodeUrl" href="javascript:CommonCode();"><span>通用码</span></a></li>
									<li><a id="CartonCodeUrl" href="javascript:CartonCode();"><span>箱码</span></a></li>
								</ul>
							</div>
						<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 50%; Z-INDEX: 2;"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>--%>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
