<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>APP 更新列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript">

var checkid="";
var checkStatus="";
var materialCode=""
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
 	//Detail();
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
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.shipToCode.value=p.id;
			document.search.shipToCompany.value=p.organname;
	}
function SelectProduct(){
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.materialCode.value=p.mCode;
			document.search.materialName.value=p.productname;
		}
function Detail(){
		if(checkid!=""){
			document.all.basic.src="listNotificationDetailAction.do?ID="+checkid;
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

function addNew(type){
	if(type == 1) {
		popWin("../app/toUploadAppUpdateAction.do?type="+type,600,350);
	} else {
		popWin("../app/toUploadAppImageAction.do?type="+type,600,350);
	}
	
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
						<form name="search" method="post" action="listAppUpdateAction.do"
								onsubmit="return oncheck();">
						<input name="makeOrganID" type="hidden" id="makeOrganID" value="${MakeOrganID}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									发布名称：
								</td>
								<td>
									<input type="text" name="publishName" value="${publishName}"
										maxlength="30" />
								</td>
								<td width="10%" align="right">
									名称：
								</td>
								<td>
									<input type="text" name="appName" value="${appName}"
										maxlength="30" />
								</td>
								<td align="right">
									发布时间:
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									版本：
								</td>
								<td>
									<input type="text" name="appVersion" value="${appVersion}"
										maxlength="30" />
								</td>
								<td width="9%" align="right">
									发布人： 
								</td>
								<td width="20%">
									<input type="hidden" name="publisher" id="publisher" value="${publisher}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'publisher',$F('makeOrganID'),'ou')" readonly>
								</td>
								<td></td>
								<td></td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<ws:hasAuth operationName="/app/toUploadAppUpdateAction.do">
								<td width="80"><a href="javascript:addNew(1);"><img	src="../images/CN/upload.gif" width="16" height="16"	border="0" align="absmiddle">&nbsp;APP上传</a>
								</td>
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/app/toUploadAppImageAction.do">
								<td width="150"><a href="javascript:addNew(2);"><img	src="../images/CN/upload.gif" width="16" height="16"	border="0" align="absmiddle">&nbsp;产品盾牌图片上传</a>
								</td>
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../app/listAppUpdateAction.do" />
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
								<td>
									编号
								</td>
								<td>
									发布名称
								</td>
								<td>
									名称
								</td>
								<td>
									版本
								</td>
								<td>
									发布时间
								</td>
								<td>
									发布人
								</td>
								<td>
									下载次数
								</td>
								<td>
									文件
								</td>
							</tr>
							<logic:iterate id="au" name="appUpdates">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${au.id}');">
									<td>
										${au.id}
									</td>
									<td>
										${au.publishName}
									</td>
									<td>
										${au.appName}
									</td>
									<td>
										${au.appVersion}
									</td>
									<td>
										${au.publishDate}
									</td>
									<td>
										<windrp:getname key='users' value='${au.publisher}' p='d'/>
									</td>
									<td>
										${au.downloadCount}
									</td>
									<td>
										<a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${au.filePath}' />&appid=${au.id}">[下载]</a>
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
								</ul>
							</div>
						<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 50%"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
									--%>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
