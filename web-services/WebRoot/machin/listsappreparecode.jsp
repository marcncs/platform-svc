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
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 obj.className="event";
	 checkid=objid;
	 TcodeDetail();
	}
	function TcodeDetail(){
		if(checkid!=""){
			document.all.submsg.src="../machin/listSapPrepareCodeAction.do?dcode="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function addNew(type){
		popWin("../machin/toUploadProduceReportAction.do?type="+type,600,250);
	}
	function exportIncome(){
		popWin("../machin/produceProductIncomeAction.do",500,400);
	}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=toller",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.search.organId.value;
			document.search.organId.value=p.id;
			document.search.oname.value=p.organname;

		if(oldOrgan != p.id) {
			clearProductList()
		}
	}

	function Detail(proDate,batchNo,primaryCode,cartonCode,covertCode,productionLine,printDate){
		//if(checkid!=""){
		if(covertCode=="#ERR") {
			covertCode="";
		}
			document.all.submsg.src="../machin/detailSapPrepareCodeActionAction.do?ID="+checkid+"&proDate="+proDate+"&batchNo="+batchNo+"&primaryCode="+primaryCode+"&cartonCode="+cartonCode+"&covertCode="+covertCode+"&productionLine="+productionLine+"&printDate="+printDate;
		//}else{
		//	alert("请选择你要操作的记录!");
		//}
	}

	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function showNum(){
		var countAll = "<%=request.getAttribute("countAll")%>";
		var countUsed = "<%=request.getAttribute("countUsed")%>";
		var countPur = "<%=request.getAttribute("countPur")%>";
		var countRelease = "<%=request.getAttribute("countRelease")%>";
		alert("当前查询:\n已用数量为:"+countUsed+"\n可用数量为:"+countPur+"\n释放数量为:"+countRelease+"\n总数量为:"+countAll);
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
								action="../machin/listSapPrepareCodeAction.do">
						<input type="hidden" name="search" id="search" value="true"/>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td  align="right">
									编码：
								</td>
								<td width="20%">
									<input name="code" type="text" id="code" value="${code}">
								</td>
								<td  align="right">
									所属工厂：
								</td>
								<td width="20%">
										<input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
								<td  align="right">
									导入时间：
								</td>
								<td colspan="5" width="30%">
									<input name="BeginDate" type="text" readonly="readonly" value="${BeginDate}"
										onFocus="javascript:selectDate(this)" size="10">
									-
									<input name="EndDate" type="text" readonly="readonly" value="${EndDate}"
										onFocus="javascript:selectDate(this)" size="10">

								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									生产计划编号：
								</td>
								<td>
									<input name=productPlanId type="text" id="productPlanId" value="${productPlanId}">
								</td>
								<td align="right">
									是否使用：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="isuse" p="y|f"
										value="${isuse}" />
								</td>
								
								<td align="right">
									释放状态：
								</td>
			<td>
				<select name="isRelease" >	
				<c:choose>			
					<c:when test="${isRelease == 1}">
            			<option value="1" selected>已释放</option>
            			<option value="0">请选择</option>
            		</c:when>
            		<c:otherwise>
            			<option value="0" selected>请选择</option>
            			<option value="1">已释放</option>
            		</c:otherwise>
            	</c:choose>
            	 </select>	
			</td>
								
								<td align="right">
									是否托码：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="istcode" p="y|f"
										value="${istcode}" />
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
									
									<td width="80" >
										<a href="javascript:showNum();"><img src="../images/CN/update.gif"
										 width="16" height="16" border="0" align="absmiddle">&nbsp;数量统计</a></td>
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
										 
										 
										
								<c:if test="${list != null}">
								<td class="SeparatePage">
									<pages:pager action="../machin/listSapPrepareCodeAction.do" />
								</td>
								</c:if>
								<c:if test="${list == null}">
								<td class="SeparatePage">
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
							<td >编码</td>
							<td>所属工厂</td>
							<td>生产计划编号</td>
							<td>上传日志编号</td>
							<td>上传用户</td>
							<td>上传时间</td>
							<td>是否使用</td>
							<td>释放状态</td>
							
							</tr>
							<c:if test="${list != null}">
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.code}');">
									<td>${p.code}</td>
									<td align="left">
											<windrp:getname key='organ' value='${p.organid}' p='d' />
									</td>
									<td>
									<c:if test="${p.productPlanId==0}"></c:if>
									<c:if test="${p.productPlanId>0}">${p.productPlanId}</c:if>
									</td>
									<td>${p.sapLogID}</td>
									<td>${p.username}</td>
									<td>${p.modifiedDate}</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isuse}' p='f' />
									</td>
									<td>
											<c:if test="${empty p.isRelease or p.isRelease == 0 }"></c:if>
							         	  	<c:if test="${p.isRelease == 1}">已释放</c:if>
									</td>
								</tr>
							</logic:iterate>
							</c:if>
						</table>
						<br />
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:TcodeDetail();"><span>箱托对应详情</span> </a>
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
					 </td>
			</tr>
		</table>
	</body>
</html>
