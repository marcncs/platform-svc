<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid,proDate,batchNo,primaryCode,cartonCode,covertCode,productionLine,printDate){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 obj.className="event";
	 checkid=objid;
	 
	Detail(proDate,batchNo,primaryCode,cartonCode,covertCode,productionLine,printDate);
	}
	
	function addNew(type){
		popWin("../machin/toUploadProduceReportAction.do?type="+type,600,250);
	}
	function exportIncome(){
		popWin("../machin/produceProductIncomeAction.do",500,400);
	}
   

	function Detail(proDate,batchNo,primaryCode,cartonCode,covertCode,productionLine,printDate){
		//if(checkid!=""){
		if(covertCode=="#ERR") {
			covertCode="";
		}
			document.all.submsg.src="../machin/detailUploadProduceReportAction.do?ID="+checkid+"&proDate="+proDate+"&batchNo="+batchNo+"&primaryCode="+primaryCode+"&cartonCode="+cartonCode+"&covertCode="+covertCode+"&productionLine="+productionLine+"&printDate="+printDate;
		//}else{
		//	alert("请选择你要操作的记录!");
		//}
	}
	

	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	

</script>

	</head>
	<body>
		<input type="hidden" id="proDate" value="">
		<input type="hidden" id="batchNo" value="">
		<input type="hidden" id="primaryCode" value="">
		<input type="hidden" id="cartonCode" value="">
		<input type="hidden" id="covertCode" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="blank" method="get"	action=""></form>
						<form name="search" method="post"
								action="../machin/listUploadProduceReportAction.do">
						<input type="hidden" name="search" id="search" value="true"/>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									产品编码：
								</td>
								<td width="25%">
									<input name="productId" type="text" id="productId" value="${productId}">
								</td>
								<td width="10%" align="right">
									产品名称：
								</td>
								<td width="25%">
									<input name="productName" type="text" id="productName" value="${productName}">
								</td>
								<td align="right">
									生产时间：
								</td>
								<td>
									<input name="BeginDate" type="text" readonly="readonly" value="${BeginDate}"
										onFocus="javascript:selectDate(this)" size="10">
									-
									<input name="EndDate" type="text" readonly="readonly" value="${EndDate}"
										onFocus="javascript:selectDate(this)" size="10">

								</td>
								<td>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									批号：
								</td>
								<td>
									<input name="batchNumber" type="text" id="batchNumber" value="${batchNumber}">
								</td>
								<td align="right">
									小包装码：
								</td>
								<td>
									<input name="primaryCode" type="text" id="primaryCode" value="${primaryCode}">
								</td>
								<td align="right">
									外箱条码：
								</td>
								<td>
									<input name="cartonCode" type="text" id="cartonCode" value="${cartonCode}">
								</td>
								<td></td>
								
							</tr>
							<tr class="table-back">
								<td align="right">
									暗码：
								</td>
								<td>
									<input name="covertCode" type="text" id="covertCode" value="${covertCode}">
								</td>
								<td align="right">
									小包装二维码：
								</td>
								<td >
									<input name="tdCode" type="text" id="tdCode" value="${tdCode}">
								</td>
								
								<td align="right" >
									
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
							<tr class="title-func-back">
							
								<td width="80">
								</td>
								
								<td width="1">
								</td>
								<c:if test="${list != null}">
								<td class="SeparatePage">
									<pages:pager action="../machin/listUploadProduceReportAction.do" />
								</td>
								</c:if>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top" >
							<td width="6%">产品编码</td>
							<td>产品名称</td>
<%--							<td>生产日期</td>--%>
							<td>批号</td>
							<td>二维码</td>
							<td>小包装码</td>
							<td>外箱条码</td>
							<td>暗码</td>
							<td>产线编号</td>
							<td>生产时间</td>
							<td>上传日志编号</td>
							
							</tr>
							<c:if test="${list != null}">
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.productId}','${p.printDate}','${p.batchNumber}','${p.primaryCode}','${p.cartonCode}','${p.covertCode}','${p.productionLine}','${p.printDate}');">
									<td>${p.productId}</td>
									<td>${p.productName}</td>
<%--									<td><windrp:dateformat value="${p.printDate}" p="yyyy-MM-dd"/></td>									--%>
									<td>${p.batchNumber}</td>
									<td>${p.tdCode}</td>
									<td>${p.primaryCode}</td>
									<td>${p.cartonCode}</td>
									<td>
									<c:choose>
										<c:when test="${p.covertCode == '#ERR'}">
											未读取或错误
										</c:when>
										<c:otherwise>
											${p.covertCode}
										</c:otherwise>
									</c:choose>
									</td>
									<td>${p.productionLine}</td>
									<td>${p.printDate}</td>
									<td>${p.uploadPrId}</td>
								</tr>
							</logic:iterate>
							</c:if>
						</table>
						<br />
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="#"><span>详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div> </td>
			</tr>
		</table>
	</body>
</html>
