<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>物流信息详细</title>
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
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
 	Detail();
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
		var p=showModalDialog("selectPlantAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.plantCode.value=p.id;
			document.search.plantName.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
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
function genCommonCode() {
	if(checkid!=""){
		if(checkStatus == 0) {
			alert("该任务还未打印!");
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
function excput(){
	if(checkid!=""){
		exportExcel.target="";
		exportExcel.action="excPutPrintJobAction.do?printJobId="+checkid;
		exportExcel.submit();
	}else{
		alert("请选择你要操作的记录!");
	}
}
		</script>
  </head>
  
  <body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								${menu }详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									
								</table>
							</td>
						</tr>
					</table>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										基本信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td  align="right">
									送货单号：
								</td>
								<td >
									${nf.deliveryNo}
								</td>
								<td  align="right">
									订单类型：
								</td>
								<td >
									${nf.deliveryOrderType}
								</td>
								<td  align="right">
									送货公司：
								</td>
								<td >
									${nf.logisticCompany}
								</td>
							</tr>
							<tr>
								<td align="right">
									送货出发地：
								</td>
								<td>
									${nf.deliveryPlace}
								</td>
								<td align="right">
									出发时间：
								</td>
								<td>
									<windrp:dateformat value="${nf.deliveryDate}" p="yyyy-MM-dd" />
								</td>
								<td align="right">
									估计到达时间
								</td>
								<td>
									<windrp:dateformat value="${nf.estimateDate}" p="yyyy-MM-dd" />
								</td>
							</tr>
							<tr>
								<td align="right">
									重量：
								</td>
								<td>
									${nf.quantity }
								</td>
								<td align="right">
									件数：
								</td>
								<td>
									${nf.casesNo }
								</td>
								<td align="right">
									收货机构代码：
								</td>
								<td>
									${nf.shipToCode}
								</td>
							</tr>
							
							<tr>
								<td align="right">
									收货机构名称：
								</td>
								<td >
									${nf.shipToCompany}
								</td>
								<td align="right">
									送货目的地：
								</td>
								<td >
									${nf.shipToAddress}
								</td>
								<td align="right">
									联系人名：
								</td>
								<td >
									${nf.consigneeName}
								</td>
							</tr>
							<tr>
								<td align="right">
									联系人手机：
								</td>
								<td >
									${nf.consigneeMobile}
								</td>
								<td align="right">
									短信发送状态：
								</td>
								<td >
									<windrp:getname key='SmsSendStatus' value='${nf.sendStatus}' p='f' />
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										物理信息详细列表
									</td>
								</tr>
							</table>
						</legend>

						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="11%">
									发货单行号
								</td>
								<td width="11%">
									物料号
								</td>
								<td width="25%">
									产品
								</td>
								<td width="8%">
									规格
								</td>
								
								<td width="6%">
									重量
								</td>
								<td width="9%">
									件数
								</td>
							</tr>
							<logic:iterate id="p" name="nfd">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${p.lineItem}
									</td>
									<td>
										${p.materialCode}
									</td>
									<td>
										${p.products}
									</td>
									<td>
										${p.packSize}
									</td>
									<td>
										${p.quantity}
									</td>
									<td>
										${p.casesNo}
									</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
</html>
