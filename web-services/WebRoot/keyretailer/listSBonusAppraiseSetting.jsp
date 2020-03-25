<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>积分管理列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<script type="text/javascript">

var year="";
var fromorganid="";
var toorganid=""
function CheckedObj(obj,objyear,objfromorganid,objtoorganid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
	year=objyear;
	fromorganid=objfromorganid;
	toorganid=objtoorganid;
}
function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
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

function SelectProduct(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.materialCode.value=p.mCode;
			document.search.materialName.value=p.productname;
		}

function excput(){
	search.target = "";
	search.action = "../keyretailer/exputAddSBonusAppraiseSettingAction.do";
	search.submit();
	search.target = "";
	search.action = "../keyretailer/listSBonusAppraiseSettingAction.do";
	
}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function addSBonusAppraise(){
	popWin("../keyretailer/toAddSBonusAppraiseAction.do?type=1&id="+checkid+"&accountId="+checkaccountid,1000,650);
}


function UpdSetting(issupport){
	if(year!=""){
		popWin("../keyretailer/updSBonusAppraiseSettingAction.do?year="+year+"&foid="+fromorganid+"&toid="+toorganid+"&isSupported="+issupport,500,250);	
	}else{
		alert("请选择你要修改的记录!");
	}
}

function comSetting() {
	if(year!=""){
		popWin("../keyretailer/updSBonusAppraiseSettingAction.do?year="+year+"&foid="+fromorganid+"&toid="+toorganid+"&type=complete",500,250);	
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
function SelectOrgan(id,name){
	var p=showModalDialog("../keyretailer/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.getElementById(id).value=p.id;
	document.getElementById(name).value=p.organname;
}
function comSBonus(){
  	popWin("../keyretailer/toConfirmSBonusAction.do",500,300);
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
									关键零售商>>支持度评价设置
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listSBonusAppraiseSettingAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									年度：
								</td>
								<td width="25%">
									<input type="text" name="year" value="${year}" maxlength="4" onkeyup="checknum(this)"/>
								</td>
								<td align="right">
									上级机构：
								</td>
								<td>
									<input name="outOrganId" type="hidden" id="outOrganId" value="${outOrganId}">
									<input name="outOrganName" type="text" id="outOrganName" size="30"
										value="${outOrganName}" readonly>
									<a href="javascript:SelectOrgan('outOrganId','outOrganName');"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
								</td>
								<td align="right">
									下级客户：
								</td>
								<td>
									<input name="inOrganId" type="hidden" id="inOrganId" value="${inOrganId}">
									<input name="inOrganName" type="text" id="inOrganName" size="30"
										value="${inOrganName}" readonly>
									<a href="javascript:SelectOrgan('inOrganId','inOrganName');"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
								</td>
								<td>
								</td>
							</tr>
							<tr class="table-back">
								<td width="10%" align="right">
									关键字：
								</td>
								<td width="26%">
									<input type="text" name="KeyWord" value="${KeyWord}"
										maxlength="30" />
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td>
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<form name="exportExcel" method="post" action="excPutPrintJobAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<%--							
								<ws:hasAuth operationName="/keyretailer/toAddSBonusSettingAction.do">
									<td width="50">
										<a href="javascript:adjust();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;调整</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/toUpdSBonusAppraiseAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdSetting();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>--%>
								
								<%--<ws:hasAuth operationName="/keyretailer/toAddSBonusAppraiseAction.do">
								<td width="50" align="center">
									<a href="javascript:addSBonusAppraise();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								--%>
								<ws:hasAuth operationName="/keyretailer/updSBonusAppraiseSettingAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdSetting('1');"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">支持</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="75" align="center">
									<a href="javascript:UpdSetting('2');"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">不支持</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/updSBonusAppraiseSettingAction.do">
								<td width="50" align="center">
									<a href="javascript:comSetting();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">确认</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../keyretailer/listSBonusAppraiseSettingAction.do" />
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
									年份
								</td>
								<td >
									上级机构
								</td>
								<td >
									下级客户
								</td>
								<td >
									目标积分
								</td>
								<td >
									当前积分
								</td>
								<td >
									积分完成率
								</td>
								<td >
									协议支持度
								</td>
								<td >
									最终积分系数
								</td>
								<td >
									最终积分
								</td>
								<td >
									客户是否已确认
								</td>
							</tr>
							<logic:iterate id="pj" name="sBonusAppraises">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.year}','${pj.fromorganid}','${pj.toorganid}');">
									<td>
										${pj.year}
									</td>
									<td>
										${pj.fromorganname}
									</td>
									<td>
										${pj.toorganname}
									</td>
									<td>
										<fmt:formatNumber value="${pj.targetbonus}" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										<fmt:formatNumber value="${pj.curbonus}" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										${pj.completerate}%
									</td>
									<td> 
										<c:if test="${pj.issupported != 1 and pj.issupported != 2}">
										未设置
										</c:if>
										<c:if test="${pj.issupported == 1}">
										支持
										</c:if>
										<c:if test="${pj.issupported == 2}">
										不支持 
										</c:if>
									</td>
									<td>
										<fmt:formatNumber value="${pj.rebaterate}" pattern="##0.##"></fmt:formatNumber>
									</td>
									<td>
										<fmt:formatNumber value="${pj.rebate}" pattern="###,##0.#"></fmt:formatNumber>
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${pj.isconfirmed}' p='f' />
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
