<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>物流短信列表</title>
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
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
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
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="listNotificationDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function SmsDetail() {
	setCookie("oCookie","2");
	if(checkid!=""){
		document.all.basic.src="listNotificationSmsAction.do?ID="+smsId;
	}else{
		alert("请选择你要操作的记录!");
	}
}

//导出
function excput() {
	search.target = "";
	search.action = "../notification/exputlistSmsAction.do";
	search.submit();
	search.target = "";
	search.action = "../notification/listSmsAction.do";
}

this.onload = function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
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
						<form name="search" method="post" action="listSmsAction.do">
						<input type="hidden" name="type" id="type"  value="${type}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="table-back">
								<td width="10%" align="right">
									编号：
								</td>
								<td width="25%">
									<input type="text" name="id" value="${id}"
										maxlength="30" />
								</td>
								<td align="right">
									手机号：
								</td>
								<td>
									<input type="text" name="mobileNo" value="${mobileNo}"
										maxlength="30" />
								</td>
								<td align="right">
									发送日期：
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
									发送状态：
								</td>
								<td>
									<windrp:select key="SmsSendStatus" name="sendStatus" p="y|f" value="${sendStatus}" />
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" maxlength="60" value="${KeyWord}">
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
							<tbody>
								<tr class="title-func-back">
									<td width="50"><a href="javascript:excput();"><img
										src="../images/CN/outputExcel.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导出</a></td>
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td class="SeparatePage">
										<pages:pager action="../notification/listSmsAction.do" />
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
								<td width="70">
									编号
								</td>
								<td width="90">
									手机号
								</td>
								<td>
									短信内容
								</td>
								<td width="150">
									发送时间
								</td>
								<td width="60">
									发送状态
								</td>
							</tr>
							<logic:iterate id="sms" name="sms">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${sms.id}');">
									<td>
										${sms.id}
									</td>
									<td>
										${sms.mobileNo}
									</td>
									<td>
										${sms.content}
									</td>
									<td>
										${sms.sendTime}
									</td>
									<td>
										<windrp:getname key='SmsSendStatus' value='${sms.sendStatus}' p='f'/>
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
