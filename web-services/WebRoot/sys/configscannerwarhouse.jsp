<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="ws" uri="ws"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language="JavaScript">
	var checkid="";
	var checkname="";
	function CheckedObj(obj,objid,objname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname=objname;
	}
	
	
	function addNew(){
		popWin("../sys/toAddScannerWarhouseAction.do?wid="+checkid+"&type=ADD",900,600);
		//var wid = document.getElementById("warehouseid").value;
		//popWin("../sys/toAddScannerWarhouseAction.do?wid="+wid+"&type=ADD",900,600);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("../sys/toUpdScannerWarhouseAction.do?wid="+checkid+"&type=EDIT&swid="+checkname,900,600);
		}else{
			alert("请选择你要操作的记录");
		}
	}
	function Delete(){
		var wid = document.getElementById("warehouseid").value;
		if(checkid!=""){
			if(window.confirm("确定删除该条记录吗？")){
				popWin("../sys/toDelScannerWarhouseAction.do?wid="+checkid+"&type=DELETE&swid="+checkname,900,600);
			}
			
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){
			return;
		}
		document.search.organid.value=p.id;
		document.search.orgname.value=p.organname;
		document.search.owname.value=p.wname;
		document.search.outwarehouseid.value=p.wid;
	}
	
	function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
	}
	
	function SelectScanner(){
		var d=showModalDialog("../sys/searchScannerImeiNAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( d == undefined ){
			return;
		}	
		document.search.scanneridSearch.value=d.id;
		document.search.scannerimein.value=d.scannerid;
	}
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
							action="../sys/toListScannerWarhouseAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td width="10%" height="20" align="right">
										机构名称：
									</td>
									<td width="24%">
										<input name="organid" type="hidden" id="organid" value="${organid}" />
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="必须录入调出机构!" value="${orgname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle"> </a>
									</td>

									<td width="10%" align="right">
										仓库名：
									</td>
									<td width="20%">
										<input type="hidden" name="outwarehouseid" id="outwarehouseid" value="${warehouseidSearch}">
										<input type="text" name="owname" id="owname" value="<windrp:getname p='d' key='warehouse' value='${warehouseidSearch}' />"
											onClick="selectW(this, 'w');"  readonly>
									</td>
									<!--<td width="5%" align="right">
										仓库编号:
									</td>
									<td width="10%">
										<input type="text" name="warehouseidSearch"
											value="${warehouseidSearch}" maxlength="16" size="30">
									</td>
									-->
									<td width="10%" align="right">
										采集器编号:
									</td>
									<td width="21%">
									<input type="hidden" name="scanneridSearch" id="scanneridSearch" value="${scanneridSearch}"/>
										<input type="text" name="scannerimein"
											value="${scannerimein}" maxlength="16" size="25" readonly>
										<a href="javascript:SelectScanner();"><img
												src="../images/CN/find.gif" width="17" height="18"
												border="0" align="absmiddle"> </a>
									</td>
									<td width="5%" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>

									<td></td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/sys/toAddScannerWarhouseAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img
												src="../images/CN/addnew.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>

								<ws:hasAuth operationName="/sys/toUpdScannerWarhouseAction.do">
									<td width="50" align="center">
										<a href="javascript:Update();"><img
												src="../images/CN/update.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;修改</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/sys/toDelScannerWarhouseAction.do">
									<td width="50" align="center">
										<a href="javascript:Delete();"><img
												src="../images/CN/delete.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;删除</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<%--<c:if test="${prompt == 'r'}">--%>
									<td class="SeparatePage">
										<pages:pager action="../sys/toListScannerWarhouseAction.do" />
									</td>
								<%--</c:if>--%>
								<%--
								<c:if test="${prompt == 'n'}">
										<td colspan="2"></td>
								</c:if>
								 --%>
							</tr>

						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<%--<c:if test="${prompt == 'r'}">--%>
						<div id="listdiv" style="overflow-y: auto; height: 600px;">
							<input id="warehouseid" type="hidden" value="${warhouseid }">
							<form method="post" name="listform" action="">
								<table class="sortable" width="100%" border="0" cellpadding="0"
									cellspacing="1">
									<tr align="center" class="title-top">
										<td width="20%">
											仓库编号
										</td>
										<td width="20%">
											机构名称
										</td>
										<td width="20%">
											仓库名称
										</td>
										<td width="20%">
											采集器编号
										</td>
										<td width="20%">
											采集器唯一号
										</td>

									</tr>
									<logic:iterate id="sw" name="scanwarhouses">
										<tr align="center" class="table-back-colorbar"
											onClick="CheckedObj(this,'${sw.warehouseid}','${sw.scannerid}');">
											<td>
												${sw.warehouseid}
											</td>
											<td>
												${sw.orgName}
											</td>
											<td>
												${sw.wareHouseName}
											</td>
											<td>
												${sw.scannerid}
											</td>
											<td>
												<windrp:getname key="scanner" value="${sw.scannerid}" p='d' />
											</td>
										</tr>
									</logic:iterate>
								</table>
							</form>
						</div>
					<%--</c:if>--%>
				</td>
			</tr>
		</table>

	</body>
</html>
