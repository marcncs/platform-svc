<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="ws" uri="ws"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
		<script language="JavaScript">
		var $j = jQuery.noConflict(true);
	var checkid = "";
	function CheckedObj(obj, objid) {

		for (i = 0; i < obj.parentNode.childNodes.length; i++) {
			obj.parentNode.childNodes[i].className = "table-back-colorbar";
		}

		obj.className = "event";
		checkid = objid;
	}

	function SelectCustomer() {
		showModalDialog("toSelectProvideAction.do", null,
				"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.search.CID.value = getCookie("pid");
		document.search.cname.value = getCookie("pname");
	}

	function excput() {
		search.action = "ecxPutFWNReportAction.do";
		search.submit();
		search.action = "../assistant/listFWNReportAction.do";
	}

	this.onload = function onLoadDivHeight() {
		document.getElementById("listdiv").style.height = (document.body.clientHeight - document
				.getElementById("bodydiv").offsetHeight)
				+ "px";
	}

	function checkValue(){
		if(!Validator.Validate(document.search,2) ){
			return false;
		}
	}
	
	function SelectSingleProductName(){
		var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.search.ProductName.value=p.productname;
		document.search.packSizeName.value="";
		document.search.packsizename.value="";
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
						<form name="search" id="search" method="post" onSubmit="return checkValue();"
							action="../assistant/listFWNReportInitAction.do">

							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										查询码：
									</td>
									<td>
										<input type="text" name="proNumber" id="proNumber" value=${proNumber }>
									</td>
									<td width="15%" align="right">
										箱码：
									</td>
									<td width="20%">
										<input type="text" name="cartonCode" id="cartonCode" value=${cartonCode }>
									</td>
									<td width="8%" align="right">
										唯一码：
									</td>
									<td width="27%">
										<input type="text" name="primaryCode" id="primaryCode" value=${primaryCode }>
									</td>
									<td>
										
									</td>
								</tr>
								<tr class="table-back">
									<td align="right">
										产品名称：
									</td>
									<td>
										<input id="ProductName" name="ProductName"
											value="${ProductName}" readonly>
										<a href="javascript:SelectSingleProductName();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0">
										</a>
									</td>
									<td align="right">
										规格：
									</td>
									<td>
										<input type="hidden" name="packSizeName" id="packSizeName"
											value="${packSizeName}">
										<input type="text" name="packsizename" id="packsizename" value="${packsizename}"
											onClick="selectDUW(this,'packSizeName',$F('ProductName'),'psn','')" readonly>
									</td>
									<td  align="right">
										查询时间：
									</td>
									<td>
										<input type="text" name="BeginDate" size="10"
											onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
										-
										<input type="text" name="EndDate"
											onFocus="javascript:selectDate(this)" value="${EndDate}" size="10"
											readonly>
									</td>
									<td>
									</td>
								</tr>
								
								<tr class="table-back">
									<td align="right">
									查询次数：
									</td>
									<td>
										<input type="text" name="qcount" size="8" dataType="Number" msg="请输入正确的查询次数！"
											maxlength="5" value="${qcount}">
										次以上(包括)
									</td>
									<td align="right">
										查询方式：
									</td>
									<td>
										<select name="queryMode">
											 <option value="">请选择</option>
								             <option ${queryMode=='1'?'selected':''} value="1">网页查询</option>
								             <option ${queryMode=='2'?'selected':''} value="2">APP查询</option>
								             <option ${queryMode=='3'?'selected':''} value="3">短信查询</option>
								             <option ${queryMode=='4'?'selected':''} value="4">官网查询</option>
								        </select>
									</td>
									<td  align="right">
										查询电话/IP：
									</td>
									<td>
										<input type="text" name="ipAddr" id="ipAddr" value=${ipAddr }>
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
							<ws:hasAuth operationName="/assistant/ecxPutFWNReportAction.do">
								<td width="50">
									<a href="#" onClick="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;导出</a>
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../assistant/listFWNReportInitAction.do" />
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
								<td >
									查询码
								</td>
								<td>
									箱码
								</td>
								<td>
									mPin码
								</td>
								<td>
									唯一码
								</td>
								<td>
									产品名称							
								</td>
								<td>
									规格
								</td>
								<td >
									查询日期
								</td>
								
								<td >
									查询方式
								</td>
								<!--<td width="16%">
									地区
								</td>
								-->
								<!--<td width="13%">
									查询方式
								</td>
								-->
								<td>
									查询电话/IP
								</td>
								<td>
									查询省份
								</td>
								<td>
									查询城市
								</td>
								<td >
									查询次数
								</td>
								<%--<td width="8%" >属性</td> --%>
							</tr>
							<c:forEach var="f" items="${lq}">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${f.id}');">
									<td>
										${f.pronumber}
									</td>
									<td>${f.cartoncode }</td>
									<td>${f.mpin }</td>
									<td>${f.primarycode }</td>
									<td>${f.productname}</td>
									<td>${f.specmode }</td>
									<td>
										${f.finddt}
									</td>
									<td>
										<windrp:getname key="QueryMode" value="${f.findtype}" p="d" />

									</td>
									<td>
										${f.telnumber }
									</td>
									<td>
										${f.areas }
									</td>
									<td>
										${f.city }
									</td>
									<td>
										${f.querynum}
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
