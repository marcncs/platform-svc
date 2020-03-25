<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	OtherShipmentDetail();
	}

	function addNew(){
	window.open("../machin/toAddRedShipmentBillAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		//document.all.top.src="toUpdSaleLogAction.do?ID="+checkid;
		window.open("toUpdRedShipmentBillAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function OtherShipmentDetail(){
		if(checkid!=""){
			document.all.submsg.src="../machin/redShipmentBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			window.open("../machin/delRedShipmentBillAction.do?OSID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=11", 500, 250);
	}
	
	function DownTxt(){
	excputform.action="../machin/txtPutOtherShipmentBillAction.do";
	excputform.submit();
	}
	
	function excput(){
		search.target="";
		search.action="../machin/excPutRedShipmentBillAction.do";
		search.submit();
		search.target="";
		search.action="../machin/listRedShipmentBillAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../machin/printListOtherShipmentBillAction.do";
		search.submit();
		search.target="";
		search.action="../machin/listRedShipmentBillAction.do";
	}
	
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	document.search.WarehouseID.value="";
	document.search.wname.value="";
	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}

function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.organid.value=p.id;
			document.search.orgname.value=p.organname;
	}
function Import() {
	window.open("../machin/toImportRedShipmentBillAction.do?incomeSort=5","","height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	
}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
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
						<form name="search" method="post"
							action="../machin/listRedShipmentBillAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">

									<td width="10%" align="right">
										出库机构：
									</td>
									<td>
										<input name="organid" type="hidden" id="organid" value="${organid}">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="必须录入调出机构!" value="${orgname}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td width="7%" align="right">
										出库仓库：
									</td>
									<td width="24%">
										<input type="hidden" name="WarehouseID" id="WarehouseID"
											value="${WarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'WarehouseID',$F('organid'),'rw')"
											value="${wname}" readonly>
									</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										制单机构：
									</td>
									<td>
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<%--<td align="right">
										制单部门：
									</td>
									<td>
										<input type="hidden" name="MakeDeptID" id="MakeDeptID"
											value="${MakeDeptID}">
										<input type="text" name="deptname" id="deptname" value="${deptname}"
											onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
											readonly>
									</td>
									<td align="right">
										制单人：
									</td>
									<td>
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname}" readonly>
									</td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td width="9%" align="right">
										出货仓库：
									</td>
									<td width="25%">
										<input type="hidden" name="WarehouseID" id="WarehouseID"
											value="${WarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'rw')"
											value="${wname}" readonly>
									</td>
									--%>

									<td width="8%" align="right">
										是否复核：
									</td>
									<td width="20%">
										<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
									</td>
									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td width="4%"></td>
								</tr>

								<tr class="table-back">
									<td width="10%" align="right">
										制单日期：
									</td>
									<td width="24%">
										<input name="BeginDate" value="${BeginDate}" readonly type="text"
											onFocus="javascript:selectDate(this)" size="12">
										-
										<input name="EndDate" value="${EndDate}" readonly type="text"
											onFocus="javascript:selectDate(this)" size="12">
									</td>
									<td align="right">
										是否记账：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="isaccount" p="y|f"
											value="${isaccount}" />
									</td>

									<td width="8%" align="right">
										&nbsp;
									</td>
									<td width="20%">
										&nbsp;
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>


							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
							<ws:hasAuth operationName="/machin/toAddRedShipmentBillAction.do">
								<td width="50">
									<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								
								<ws:hasAuth operationName="/machin/toUpdRedShipmentBillAction.do">
								<td width="50">
									<a href="javascript:Update();"><img src="../images/CN/update.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/delRedShipmentBillAction.do">
								<td width="50">
									<a href="javascript:Del();"><img src="../images/CN/delete.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/toImportRedShipmentBillAction.do">
									<td width="50">
										<a href="javascript:Import();"><img src="../images/CN/import.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;导入</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<!--  
            <td width="50" >
				<a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			-->
								<ws:hasAuth operationName="/machin/excPutRedShipmentBillAction.do">
								<td width="50">
									<a href="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<%--								
								<ws:hasAuth operationName="/machin/printListOtherShipmentBillAction.do">
								<td width="50">
									<a href="javascript:print();"><img src="../images/CN/print.gif"
											width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								--%>
								<td class="SeparatePage">
									<pages:pager action="../machin/listOtherShipmentBillAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width=>
									编号
								</td>
								<%--<td width="8%">
									相关单据
								</td>
								--%>
								<td>
									出库机构
								</td>
								<td >
									出库仓库
								</td>
								<td >
									出库类别
								</td>
								<td>
									制单机构
								</td>
								<td >
									制单人
								</td>
								<td >
									制单日期
								</td>
								<td >
									是否复核
								</td>
								<td>
									是否记账
								</td>
							</tr>
							<logic:iterate id="pi" name="alsb">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${pi.id}');">
									<td>
										${pi.id}
									</td>
									<td>
										<windrp:getname key='organ' value='${pi.organid }' p='d' />
									</td>
									<td>
										<windrp:getname key='warehouse' value='${pi.warehouseid}' p='d' />
									</td>
									<td>
										<windrp:getname key='ShipmentSort' value='${pi.shipmentsort}' p='f' />
									</td>
									<td>
										<windrp:getname key='organ' value='${pi.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${pi.makeid}' p='d' />
									</td>
									<td>
										<windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${pi.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${pi.isaccount}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:OtherShipmentDetail();"><span>红冲出库详情</span> </a>
									</li>
								</ul>
							</div>
							<div>
								<IFRAME id="submsg"
									style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
									name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"
									onload="setIframeHeight(this);"></IFRAME>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
