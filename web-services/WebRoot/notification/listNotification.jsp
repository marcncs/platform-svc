<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>物流信息列表</title>
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
var smsId=""
function CheckedObj(obj,objid,objsmsid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
 	smsId=objsmsid;

 	 pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	case "1":
		 	if(document.getElementById("DetailUrl")){
		 		Detail(); break;
			}
		case "2":
			if(document.getElementById("SmsUrl")){
				SmsDetail();break;
			}
		default:Detail();
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
						<form name="search" method="post" action="listNotificationAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									运货单号：
								</td>
								<td width="25%">
									<input type="text" name="deliveryNo" value="${deliveryNo}"
										maxlength="30" />
								</td>
								<td align="right">
									送货机构：
								</td>
								<td>
									<input type="text" name="logisticCompany" value="${logisticCompany}"
										maxlength="30" />
								</td>
								<td width="10%" align="right">
									出发时间：
								</td>
								<td>
								<input name="BeginDeliveryDate" type="text" id="BeginDate" size="10" value="${BeginDeliveryDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDeliveryDate" type="text" id="EndDate" size="10" value="${EndDeliveryDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									估计到达时间：
								</td>
								<td>
								<input name="BeginEstimateDate" type="text" id="BeginDate" size="10" value="${BeginEstimateDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndEstimateDate" type="text" id="EndDate" size="10" value="${EndEstimateDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td align="right">
									收货机构：
								</td>
								<td>
									<input name="shipToCode" type="hidden" id="shipToCode" value="${shipToCode}">
									<input name="shipToCompany" type="text" id="shipToCompany" size="30"  value="${shipToCompany}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td width="9%" align="right">
									联系人： 
								</td>
								<td width="20%">
									<input type="text" name="consigneeName" value="${consigneeName}"
										maxlength="30" />
								</td>
								<td class="SeparatePage">
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									联系人手机：
								</td>
								<td>
									<input type="text" name="consigneeMobile" value="${consigneeMobile}"
										maxlength="30" />
								</td>
								<td width="9%" align="right">
<%--									短信发送状态：--%>
								</td>
								<td width="20%">
<%--									<windrp:select key="SmsSendStatus" name="sendStatus" p="y|f" value="" />--%>
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
								<td class="SeparatePage">
									<pages:pager action="../notification/listNotificationAction.do" />
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
									送货单号
								</td>
								<td>
									送货机构
								</td>
								<td>
									出发时间
								</td>
								<td>
									估计到达时间
								</td>
								<td>
									重量
								</td>
								<td>
									件数
								</td>
								<td>
									收货机构
								</td>
								<td>
									联系人
								</td>
								<td>
									联系人手机
								</td>
							</tr>
							<logic:iterate id="nf" name="notifications">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${nf.id}','${nf.smsId}');">
									<td>
										${nf.deliveryNo}
									</td>
									<td>
										${nf.logisticCompany}
									</td>
									<td>
										${nf.deliveryDate}
									</td>
									<td>
										${nf.estimateDate}
									</td>
									<td>
										${nf.quantity}
									</td>
									<td>
										${nf.casesNo}
									</td>
									<td>
										${nf.shipToCompany}
									</td>
									<td >
										${nf.consigneeName}
									</td>
									<td >
										${nf.consigneeMobile}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
						<div id="tabs1">
								<ul>
									<li><a id="DetailUrl" href="javascript:Detail();"><span>详细信息</span></a></li>
									<li><a id="SmsUrl" href="javascript:SmsDetail();"><span>短信信息</span></a></li>
								</ul>
							</div>
						<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 50%"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
