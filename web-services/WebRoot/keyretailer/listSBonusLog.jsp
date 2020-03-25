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

this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
}
function SelectOrgan(){
	var p=showModalDialog("../keyretailer/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.searchform.organId.value=p.id;
		document.searchform.organName.value=p.organname;
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
						<form id="searchform" name="searchform" method="post" action="listSBonusLogAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="table-back">
								<td align="right">
									区域：
								</td>
								<td>
								   <input type="hidden" name="regionId" id="regionId" value="${regionId}" >			
								   <windrp:satree id="regionId" name="regionName" value="${regionName}"/>
								</td>
								<td align="right">
									机构：
								</td>
								<td>
									<input name="organId" type="hidden" id="organId" value="${organId}">
									<input name="organName" type="text" id="organName" size="30"
										value="${organName}" readonly>
									<a href="javascript:SelectOrgan();"><img
											src="../images/CN/find.gif" width="18" height="18" border="0"
											align="absmiddle"> </a>
								</td>
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
								<td>
								</td>
							</tr>
							<tr class="table-back">
								<td width="10%" align="right">
									积分类型：
								</td>
								<td width="25%">
									<windrp:select key="BonusType" name="bonusType" p="y|f" value="${bonusType}" />
								</td>
								<td width="10%" align="right">
									关键字：
								</td>
								<td width="26%">
									<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
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
						<form name="exportExcel" method="post" action="excPutSBonusLogAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<td class="SeparatePage"> 
									<pages:pager action="../keyretailer/listSBonusLogAction.do" />
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
									机构名称
								</td>
								<td >
									编号
								</td>
								<td >
									单据号
								</td>
								<td >
									积分类型
								</td>
								<td >
									错误信息
								</td>
								<td >
									日期
								</td>
							</tr>
							<logic:iterate id="pj" name="sBonusLogs">
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
										${pj.organname}
									</td>
									<td>
										${pj.id}
									</td>
									<td>
										${pj.billno}
									</td>
									<td>
										<windrp:getname key='BonusType' value='${pj.bonustype}' p='f' />
									</td>
									<td>
										${pj.logmsg}
									</td>
									<td>
										${pj.makedate}
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
