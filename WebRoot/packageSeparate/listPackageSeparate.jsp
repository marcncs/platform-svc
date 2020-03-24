<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	PackSeparateDetail();
	}
	
	function addNew(){
	window.open("../warehouse/toAddPackSeparateAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdPackSeparateAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PackSeparateDetail(){
		if(checkid!=""){
			document.all.submsg.src="packSeparateDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			window.open("../warehouse/delPackSeparateAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
function excput(){
		search.target="";
		search.action="../warehouse/excPutOtherIncomeAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOtherIncomeAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListOtherIncomeAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOtherIncomeAction.do";
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=12", 500, 250);
	}
	
	function DownTxt(){
	excputform.action="txtPutOtherIncomeAction.do";
	excputform.submit();
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.organId.value=p.id;
	document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","MakeDeptID","makeId","uname");
	document.search.warehouseId.value="";
	document.search.wname.value="";
	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}

</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<SCRIPT language=javascript>

</SCRIPT>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
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
							action="../warehouse/listPackSeparateAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td align="right">
										入库机构：
									</td>
									<td>
										<input name="organId" type="hidden" id="organId"
											value="${organId}">
										<input name="oname" type="text" id="oname" size="30"
											value="${oname}" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
									<td align="right">
										制单人：
									</td>
									<td>
										<input type="hidden" name="makeId" id="makeId"
											value="${makeId}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'makeId','PackSeparate','ddu','makeId')"
											value="${uname}" readonly>
									</td>
									<td align="right">
<%--										制单部门：--%>
									</td>
									<td>
										
										<input type="hidden" name="MakeDeptID" id="MakeDeptID"
											value="${MakeDeptID}">
										<%--
										<input type="text" name="deptname" id="deptname"
											value="${deptname}"
											onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
											readonly>
										--%>
									</td>
									
									<td></td>
								</tr>
								<tr class="table-back">
									<td width="7%" align="right">
										仓库：
									</td>
									<td width="24%">
										<input type="hidden" name="warehouseId" id="warehouseId"
											value="${warehouseId}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'warehouseId',$F('organId'),'rw')"
											value="${wname}" readonly>
									</td>
									<td width="10%" align="right">
										入库日期：
									</td>
									<td width="21%">
										<input name="BeginDate" type="text" id="BeginDate"
											value="${BeginDate}" readonly size="10"
											onFocus="javascript:selectDate(this)">
										-
										<input name="EndDate" type="text" id="EndDate"
											value="${EndDate}" readonly size="10"
											onFocus="javascript:selectDate(this)">
									</td>
									<td width="10%" align="right">
										是否复核：
									</td>
									<td width="19%">
										<windrp:select key="YesOrNo" name="isAudit" p="y|f"
											value="${isAudit}" />
									</td>
									<td width="9%"></td>
								</tr>

								<tr class="table-back">
									<td width="7%" align="right">
										关键字：
									</td>
									<td width="24%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td align="right">
<%--										是否记账：--%>
									</td>
									<td>
										<%--
										<windrp:select key="YesOrNo" name="isaccount" p="y|f"
											value="${isaccount}" />
										--%>
									</td>
									
									<td width="10%" align="right">
										&nbsp;
									</td>
									<td>
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
								<ws:hasAuth operationName="/warehouse/toAddPackSeparateAction.do">
								<td width="50">
									<a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/toUpdPackSeparateAction.do">
								<td width="50">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/delPackSeparateAction.do">
								<td width="50">
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<%--  
            <td width="50" >
				<a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			
								<td width="50">
									<a href="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								--%>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listPackSeparateAction.do" />
								</td>
							</tr>
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
									<td width="12%">
										编号
									</td>
									<td width="13%">
										入货机构
									</td>
									<td width="13%">
										入货仓库
									</td>
									<td width="25%">
										制单机构
									</td>
									<td width="11%">
										制单人
									</td>
									<td width="12%">
										制单日期
									</td>
									<td width="8%">
										是否复核
									</td>
									<%--            <td width="7%">是否记账</td>--%>
								</tr>
								<logic:iterate id="pi" name="ps">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${pi.id}');">
										<td>
											${pi.id}
										</td>
										<td>
<%--											<windrp:getname key='organ' value='${pi.organId}' p='d' />--%>
											${pi.organname}
										</td>
										<td>
											<windrp:getname key='warehouse' value='${pi.warehouseid}'
												p='d' />
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
										<%--            <td><windrp:getname key='YesOrNo' value='${pi.isaccount}' p='f'/></td>--%>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:PackSeparateDetail();"><span>详细信息</span>
										</a>
									</li>
								</ul>
							</div>
							<div>
								<IFRAME id="submsg"
									style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
									name="submsg" src="../sys/remind.htm" frameBorder="0"
									scrolling="no" onload="setIframeHeight(this);"></IFRAME>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<%--
<form  name="excputform" method="post" action="">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
--%>
	</body>
</html>
