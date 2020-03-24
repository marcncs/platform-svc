<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language=javascript>
	function CheckInput(){
		var scannerid =document.getElementById("scannerid").value.trim();
		if(scannerid==null||scannerid==""){
			alert("采集器编号不能为空!");
			return false;
		}
		return true;
	}
	
	function SelectScanner(){
		var d=showModalDialog("../sys/searchScannerImeiNAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( d == undefined ){
			return;
		}	
		document.referform.scannerid.value=d.id;
		document.referform.scannerimein.value=d.scannerid;
	}
  	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){
			return;
		}
		document.referform.organid.value=p.id;
		document.referform.orgname.value=p.organname;
		document.referform.owname.value=p.wname;
		document.referform.outwarehouseid.value=p.wid;
	}

	function SelectWareHouse(){
		var c=showModalDialog("../sys/searchWareHouseAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( c == undefined ){
			return;
		}	
		document.referform.WareHouseId.value=c.warhouseid;
	}	
	function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
	}

</script>
	</head>

	<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
		<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								采集器仓库配置
							</td>
						</tr>
					</table>
					<form name="referform" method="post"
						action="../sys/addScannerWarhouseAction.do?warehouseid=${sw.warehouseid}"
						onSubmit="return CheckInput();">
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
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td width="10%" height="20" align="right">
										机构名称：
									</td>
									<td width="24%">
										<input name="organid" type="hidden" id="organid" value="${organid}" />
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="必须录入调出机构!" value="${sw.orgName}" readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>

									<td width="10%" align="right">
										仓库名：
									</td>
									<td width="20%">
										<input type="hidden" name="outwarehouseid" id="outwarehouseid" />
										<input type="text" name="owname" id="owname"
											onClick="selectW(this,'w');" value="${sw.wareHouseName}" readonly>
										<span class="STYLE1">*</span>
									</td>
									<%--
	     <td width="10%" height="20" align="right">仓库编号：</td>
           <td width="23%">
	          <input name="WareHouseId" type="text" id="WareHouseId" maxlength="128" 
	          value="${warhouseid}" readonly>
	          <a href="javascript:SelectWareHouse();"><img src="../images/CN/find.gif" width="17" height="18" border="0" align="absmiddle"> </a>
	          <span class="text-red3">*</span></td>
	    --%>
									<c:if test="${type=='ADD'}">
										<td width="10%" align="right">
											采集器编号：
										</td>
										<td width="23%">
											<input name="scannerid" type="text" id="scannerid" maxlength="128"
												value="<windrp:getname key='scannerImeiN' value='${scannerid}' p='d'/>"
												readonly>
											<a href="javascript:SelectScanner();"><img
													src="../images/CN/find.gif" width="17" height="18" border="0"
													align="absmiddle"> </a>
											<span class="text-red3">*</span>
										</td>
										<td>
											<input name="type" type="hidden" value="ADD">
										</td>
									</c:if>
									<c:if test="${type=='EDIT'}">
										<td width="10%" align="right">
											采集器编号：
										</td>
										<td width="23%">
											<input type="hidden" name="scannerid" id="scannerid" value='${sw.scannerid}'/>
											<input name="scannerimein" type="text" id="scannerimein"
												maxlength="128"
												value="<windrp:getname key="scanner" value='${sw.scannerid}' p='d' />"
												readonly>
											<a href="javascript:SelectScanner();"><img
													src="../images/CN/find.gif" width="17" height="18"
													border="0" align="absmiddle"> </a>
											<span class="text-red3">*</span>
										</td>
										<td>
											<input name="type" type="hidden" value="EDIT">
										</td>
										<td>
											<input name="swid" type="hidden" value="${sw.id}">
										</td>
									</c:if>
									<td>
										<input type="hidden" name="warhouseid" value="${warhouseid}">
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center">
								<td width="28%">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
									<input name="cancel" type="button" id="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
