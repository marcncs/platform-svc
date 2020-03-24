<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0; 
	var checkaudit=0;
	function CheckedObj(obj,objid,objaudit){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkaudit=objaudit;
	 
	}
	
	function addNew(){
	popWin("../machin/toAddApplyQrCodeAction.do",900,600);
	}
	
	function UpdApplyQrCode(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("该记录已审批,无法修改!");
				return;
			}
		popWin("../machin/toUpdApplyQrCodeAction.do?ID="+checkid,900,600);
		}else{
		alert("<bean:message key='sys.selectrecord'/>!");
		}
		
	}
	
	function Del(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("该记录已审批,无法删除!");
				return;
			}
			if ( confirm("你确认要删除该记录吗?") ){
				popWin2("../machin/delApplyQrCodeAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Audit(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("该记录已审批!");
				return;
			}
			if ( confirm("你确认要审批该记录吗?") ){
				popWin2("../machin/auditApplyQrCodeAction.do?ID="+checkid);
			}
		}else{
			alert("<bean:message key='sys.selectrecord'/>!");
		}
	}

function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.organId.value=p.id;
	document.search.oname.value=p.organname;
	//document.search.MakeDeptID.value="";
	//document.search.deptname.value="";

}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=toller&i18nFlag=true",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.search.organId.value;
			document.search.organId.value=p.id;
			document.search.oname.value=p.organname;

		if(oldOrgan != p.id) {
			clearProductList()
		}
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
									生产数据>>码申请
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="../machin/listApplyQrCodeAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="8%" align="right">
										关键字：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td width="8%" align="right"> 
<%-- 										<bean:message key='tolling.applycode.plant'/>：  --%>
									</td>
									<td width="26%">
										<%-- <input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a> --%>
									</td>
									
									<td colspan="3" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/machin/toAddApplyQrCodeAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/toUpdApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:UpdApplyQrCode();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;修改</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/delApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Del();"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/machin/auditApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:Audit();"><img src="../images/CN/update.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;审批</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../machin/listApplyQrCodeAction.do" />
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
									<td>
										编号 
									</td>
									<td>
										工厂
									</td>
									<td>
										产品物料号 
									</td>
									<td>
										产品名称
									</td>
									<td>
										产品规格 
									</td>
									<td>
										生成小包装数 
									</td>
									<td>
										是否已审核
									</td>
									<td>
										生成状态 
									</td>
									<td>
										申请日期 
									</td>
									<td>
										PO编号
									</td>
									<td>
										码文件 
									</td>
									<td >
										同步状态
									</td>
								</tr> 
								<logic:iterate id="c" name="aqcs">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${c.id},'${c.isaudit}');">
										<td align="left">
											${c.id}
										</td>
										<td align="left">
											${c.organname}
										</td>
										<td align="left">
											${c.mcode}
										</td>
										<td align="left">${c.productname}</td>
										<td align="left">${c.specmode}</td>
										<td align="left">
											${c.totalqty}
										</td>
										<td align="left">
											<windrp:getname language="en" key='YesOrNo' value='${c.isaudit}' p='f'/>
										</td>
										<td align="left">
											<windrp:getname language="en" key='QrStatus' value='${c.status}' p='f'/>
										</td>
										<td align="left">
											${c.makedate}
										</td>
										<td align="left">
											${c.pono}
										</td>
										<td align="left">
										<c:if test="${c.filepath != null}">
											<a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${c.filepath}'/>">[下载] </a>
										</c:if>
										</td>
										<td>
											<windrp:getname key='SyncStatus' value='${c.syncstatus}' p='f'/>
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>


	</body>
</html>
