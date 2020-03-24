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

var checkid="";
var checkaccountid="";
var materialCode=""
var primaryCodeStatus=""
function CheckedObj(obj,objid,objaccountid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
 	checkaccountid=objaccountid;
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
	search.action = "../keyretailer/exputAddSBonusAppraiseAction.do";
	search.submit();
	search.target = "";
	search.action = "../keyretailer/listSBonusAppraiseAction.do";
	
}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function addSBonusAppraise(){
	popWin("../keyretailer/toAddSBonusAppraiseAction.do?type=1&id="+checkid+"&accountId="+checkaccountid,1000,650);
}


function UpdSetting(){
	if(checkaccountid!=""){
		popWin("../keyretailer/toUpdSBonusAppraiseAction.do?accountId="+checkaccountid,1000,450);	
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
function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.organId.value=p.id;
			document.search.companyName.value=p.organname;
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
						<form name="search" method="post" action="listSBonusAppraiseAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									姓名：
								</td>
								<td width="25%">
									<input type="text" name="name" value="${name}" maxlength="30" />
								</td>
								<td align="right">
									单位名称：
								</td>
								<td>
									<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="companyName" type="text" id="companyName" size="30"
											value="${companyName}" readonly>
										<a href="javascript:SelectOutOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>

								</td>
								<td width="10%" align="right">
								</td>
								<td width="26%">
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
								--%><ws:hasAuth operationName="/keyretailer/toUpdSBonusAppraiseAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdSetting();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">调整</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/exputAddSBonusAppraiseAction.do">
								<td width="50">
									<a href="javascript:excput()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								</ws:hasAuth>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../keyretailer/listSBonusAppraiseAction.do" />
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
									积分账号
								</td>
								<td >
									姓名
								</td>
								<td >
									单位名称
								</td>
								<td >
									单位地址
								</td>
								<td >
									机构编号
								</td>
								<td >
									手机号码
								</td>
								<td >
									所属区域
								</td>
								<td >
									年度
								</td>
								<td >
									目标积分
								</td>
								<td >
									当前总积分
								</td>
								<td >
									积分评价系数
								</td>
								<td >
									实际积分
								</td>
							</tr>
							<logic:iterate id="pj" name="sBonusAppraises">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}','${pj.accountid}');">
									<td>
										${pj.accountid}
									</td>
									<td>
										${pj.name}
									</td>
									<td>
										${pj.companyname}
									</td>
									<td>
										${pj.address}
									</td>
									<td>
										${pj.organid}
									</td>
									<td>
										${pj.mobile}
									</td>
									<td>
									    ${pj.areaname}
									</td>
									<td>
										${pj.period}
									</td>
									<td>
										${pj.targetpoint}
									</td>
									<td>
										${pj.bonuspoint}
									</td>
									<td>
										<fmt:formatNumber value="${pj.appraise}" pattern="##0.##"></fmt:formatNumber>
									</td>
									<td>
										<fmt:formatNumber value="${pj.actualpoint}" pattern="###,##0.#"></fmt:formatNumber>
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
