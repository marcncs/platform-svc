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
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<script type="text/javascript">

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

function SelectProduct(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.mcode.value=p.mCode;
			document.search.materialName.value=p.productname;
		}

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}

function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.organId.value=p.id;
			document.search.outOrganName.value=p.organname;
	}
	
function SelectOrgan2(){
	var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.search.oppOrganId.value=p.id;
		document.search.oname2.value=p.organname;
}

function addNew(){
	popWin("../keyretailer/toAddSBonusDetailAction.do?",890,450);	
}
function OutPut() {
	search.action = "../keyretailer/exportSBonusDetailAction.do";
	search.submit();
	search.action = "../keyretailer/listSBonusDetailAction.do";
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
						<form id="search" name="search" method="post" action="listSBonusDetailAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									当前机构：
								</td>
								<td width="25%">
									<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="outOrganName" type="text" id="outOrganName" size="30"
											value="${outOrganName}" readonly>
										<a href="javascript:SelectOutOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
								</td>
								<td align="right">
									对方机构：
								</td>
								<td>
									<input name="oppOrganId" type="hidden" id="oppOrganId"
											value="${oppOrganId}">
										<input name="oname2" type="text" id="oname2" size="30"
											value="${oname2}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>

								</td>
								<td width="10%" align="right">
									积分类型：
								</td>
								<td width="26%">
									<windrp:select key="BonusType" name="bonusType" p="y|f" value="${bonusType}" />
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
									产品：
								</td>
								<td>
									<input name="mcode" type="hidden" id="mcode" value="${mcode}">
									<input name="materialName" type="text" id="materialName" size="30"  value="${materialName}"
									readonly><a href="javascript:SelectProduct();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td width="9%" align="right">
									关键字： 
								</td>
								<td width="20%">
									<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
								</td>
								<td>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									区域：
								</td>
								<td>
								   <input type="hidden" name="regionId" id="regionId" value="${regionId}" >			
								   <windrp:satree id="regionId" name="regionName" value="${regionName}"/>
								</td>
								<td align="right">
									客户类型：
								</td>
								<td>
									<windrp:select key="DealerType" name="organModel" p="y|f" value="${organModel}" />
								</td>
								<td width="9%" align="right">
									年度： 
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
						<form name="exportExcel" method="post" action="excPutSBonusDetailAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody>
								<tr class="title-func-back">
									<ws:hasAuth operationName="/keyretailer/toAddSBonusDetailAction.do">
										<td width="50" align="center">
											<a href="javascript:addNew();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">调整</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="1" height="14">
										</td>
									</ws:hasAuth>
									<ws:hasAuth operationName="/keyretailer/exportSBonusDetailAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									</ws:hasAuth>
									<td class="SeparatePage">
										<pages:pager action="../keyretailer/listSBonusDetailAction.do" />
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
									大区
								</td>
								<td >
									地区
								</td>
								<td >
									小区
								</td>
								<td >
									年度
								</td>
								<td >
									当前机构名称
								</td>
								<td >
									对方机构名称
								</td>
								<td >
									积分类型
								</td>
								<td >
									分值
								</td>
								<td >
									物料号
								</td>
								<td >
									产品
								</td>
								<td >
									规格
								</td>
								<td >
									数量
								</td>
<%--								
								<td >
									上传编号
								</td>--%>
								<td >
									日期
								</td>
								<td >
									备注
								</td>
							</tr>
							<logic:iterate id="pj" name="sBonusDetails">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}');">
									<td>
										${pj.bigregion}
									</td>
									<td>
										${pj.middleregion}
									</td>
									<td>
										${pj.smallregion}
									</td>
									<td>
										${pj.year}
									</td>
									<td>
										${pj.organname}
									</td>
									<td>
										${pj.opporganname}
									</td>
									<td>
										<windrp:getname key='BonusType' value='${pj.bonustype}' p='f' />
									</td>
									<td>
										<fmt:formatNumber value="${pj.bonuspoint}" pattern="#0.##"></fmt:formatNumber>
									</td>
									<td>
										${pj.mcode}
									</td>
									<td>
										${pj.productname}
									</td>
									<td>
										${pj.spec}
									</td>
									<td>
										${pj.amount}
									</td>
<%--									
									<td>
										${pj.uploadid}
									</td>--%>
									<td>
										${pj.makedate}
									</td>
									<td>
										${pj.remark}
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
