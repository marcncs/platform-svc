<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>暗码上传报告</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
		var checkid=0;
		var checklineno="";
		var checkmcode="";
		var checkbatch="";
		var checkbegindate="";
		var checkenddate="";
	function CheckedObj(obj,objid,objlineno,objmcode,objbatch,objbegindate,objenddate){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checklineno=objlineno;
	 checkmcode=objmcode;
	 checkbatch=objbatch;
	 checkbegindate=objbegindate;
	 checkenddate=objenddate;
	 ErrorLogList();
	}

	function ErrorLogList(){
			//if(checkid!="" || checklineno!="" || checkmcode!="" || checkbatch!=""){
				document.all.basic.src="listCovertErrorLogAction.do?isDetail=1&id="+checkid+"&lineNo="+checklineno+"&materialCode="+checkmcode+"&batch="+checkbatch+"&BeginDate="+checkbegindate+"&EndDate="+checkenddate;
			//}else{
			//	alert("请选择你要操作的记录!");
			//}
		}

	function OutPut(){
		search.action="excputCovertUploadReportAction.do";
		search.submit();
		search.action="../sap/listCovertUploadReportAction.do";
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的日志吗？如果删除将永远不能恢复!")){
				popWin("../machin/delRecordAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Reset(){
		if(checkid!=""){
			if(window.confirm("您确认要重置编号为："+checkid+" 的日志吗？")){
				popWin("../machin/resetRecordAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}	
function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.materialCode.value=p.nccode;
	document.search.productName.value=p.productname;
	}

	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	

</script>

	</head>
	<body>
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
						<form name="search" method="post"
								action="../sap/listCovertUploadReportAction.do">
						<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									生产线：
								</td>
								<td>
									<input type="text" name="lineNo" maxlength="60" value="${lineNo}">
									<input type="checkbox" name="column" value="line_No">
								</td>
								<td align="right">
										产品：
								</td>
								<td>
									<input name="materialCode" type="hidden" id="materialCode"
										value="${materialCode}">
									<input id="productName" name="productName"
										value="${productName}" readonly>
									<a href="javascript:SelectSingleProduct();"><img
											src="../images/CN/find.gif" align="absmiddle" border="0">
									</a>
									<input type="checkbox" name="column" value="material_Code, product_Name,product_Id">
								</td>
<%--								<td align="right">
									上传人：
								</td>
								<td>
									<input type="hidden" name="uploadUser" id="uploadUser" value="${uploadUser}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'uploadUser',$F('MakeOrganID'),'ou')" readonly>
								</td>
								<td align="right">
									生产类型：
								</td>
								<td>
									<windrp:select key="RecodeType" name="recodeType" p="y|f" value="${recodeType}"/>
								</td>
								--%>
								<td align="right">
									批号：
								</td>
								<td>
									<input type="text" name="batch" maxlength="60" value="${batch}">
									<input type="checkbox" name="column" value="batch,material_Code, product_Name,product_Id">
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr class="table-back">
<%--							
								<td align="right">
									生产打印日期：
								</td>
								<td>
								<input name="PBeginDate" type="text" id="PBeginDate" size="10" value="${PBeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="PEndDate" type="text" id="PEndDate" size="10" value="${PEndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								--%>
								<td align="right">
									上传日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
<%--								
								<td align="right">
									上传日志编号：
								</td>
								<td>
									<input type="text" name="uploadPrId" maxlength="60" value="${uploadPrId}">
								</td>
								--%>
								<td></td>
								<td></td>
								<td></td>
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
							<tr class="title-func-back">
								<%-- <td width="50">
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Reset();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;重置</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								--%>
								<ws:hasAuth operationName="/sap/excputCovertUploadReportAction.do">
									<td width="50">
										<a href="#" onClick="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../sap/listCovertUploadReportAction.do" />
								</td>
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
							<tr align="center" class="title-top">
<%--							<td>编号</td>--%>
							<td>生产线</td>
							<td>产品名称</td>
							<td>规格</td>
							<td>批号</td>
							<td>总记录数</td>
							<td>错误记录数</td>
							<td>正确率</td>
							<td>上传次数</td>
<%--							<td>错误记录数</td>--%>
<%--							<td>上传人</td>--%>
							<td>上传时间</td>
<%--							<td>上传日志编号</td>--%>
							</tr>
							<logic:iterate id="p" name="cur">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}','${p.lineNo}','${p.materialCode}','${p.batch}','${BeginDate}','${EndDate}');">
<%--									<td>${p.id}</td>--%>
									<td>${p.lineNo}</td>
									<td>${p.productName}</td>
									<td>${p.packSizeName}</td>
									<td>${p.batch}</td>
<%--									<td><windrp:getname key='RecodeType' value='${p.recodeType}' p='f'/></td>--%>
<%--									<td><windrp:dateformat value="${p.printDate}" p="yyyy-MM-dd"/></td>--%>
									<td>${p.totalCount}</td>
									<td>${p.errorCount}</td>
									<td>${p.accuracy}</td>
									<td>${p.uploadCount}</td>
<%--									<td>${p.errorCount}</td>--%>
<%--									<td><windrp:getname key='users' value='${p.uploadUser}' p='d'/></td>--%>
									<td><windrp:dateformat value="${p.uploadDate}" p="yyyy-MM-dd HH:mm:ss"/></td>
<%--									<td>${p.uploadPrId}</td>--%>
									
<%--									<td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>--%>
<%--									<td><windrp:getname key='DealState' value='${p.isdeal}' p='f'/></td>--%>
								</tr>
							</logic:iterate>
						</table>
						<br>
						<div id="tabs1">
								<ul>
									<li><a id="ErrorLogUrl" href="javascript:ErrorLogList();"><span>错误信息</span></a></li>
								</ul>
							</div>
						<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 50%; Z-INDEX: 2;"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
