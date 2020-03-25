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
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery-1.4.2.min.js"> </SCRIPT>
		<script language="JavaScript">

		this.onload =function onLoadDivHeight(){
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
		}
	var checkid=0;
	var approvalFlag=0;
	var closeFlag=0;
	var tempflag=null;
	var linkModeFlag=0;
	function CheckedObj(obj,objid,flag,temp,closef,linkMode){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 approvalFlag = flag;
	 closeFlag = closef;
	 tempflag = temp;
	 linkModeFlag = linkMode;
	 submenu = getCookie("oCookie");
	 switch(submenu){
		case "1":Detail(); break;
		default:Detail();
	 }
	}

	var checkCount = 0;
	function checkedCount(){
		$("input[name='che']").each(function() {
			 if ($(this).is(':checked')) {
				 checkCount ++;
			}
		});
	}

	
	function addNew(){
		popWin("../erp/toAddProductPlanAction.do",900,600);
	}
	
	function UpdProductPlan(){
		checkedCount();
		if(checkCount >=2){
			alert("请选择一条记录操作!");
			checkCount = 0;
			return;
		}
		checkCount = 0;
		if(checkid>0){
			if(approvalFlag==1){
				alert("该计划已审批!不能修改!");
				return;
			}
			popWin("../erp/toUpdProductPlanAction.do?ID="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
		
	}
	
	function UpdProductPlanCovertCode(){
		checkedCount();
		if(checkCount >=2){
			alert("请选择一条记录操作!");
			checkCount = 0;
			return;
		}
		checkCount = 0;
		if(checkid>0){
			if(approvalFlag==1){
				alert("该计划已审批!不能修改!");
				return;
			}
			if(tempflag=='已生成'){
			  alert("该计划已生成条码,不能修改!");
			  return;
			}
			popWin("../erp/toUpdProductPlanAction.do?type=2&ID="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		} 
		
	}
	
	function UserRole(){
		if(checkid>0){
		
		popWin("listUserRoleAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	function Detail(){
	setCookie("oCookie","1");
		if(checkid>0){
		  document.all.submsg.src="listProductPlanDetailAction.do?actionType=1&ID="+checkid;
		  
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ResetPWD(){
		if(checkid>0){
			popWin("toResetPwdAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function AssignAreas(){
		if(checkid>0){
		popWin("toAssignAreasAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
		
	}
	

	function SelectProduct(){
			var oid = $('#organId').val();
			if(oid == "") {
				oid = '${organId}';
			}
			var p=showModalDialog("../common/selectSingleProductAction.do?oid="+oid,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.productId.value=p.id;
			document.search.productname.value=p.productname;
		}
	function UserVisit(){
	setCookie("UsMenu","1");
		if(checkid>0){
			document.all.submsg.src="..${ruleAuthUrl }?userid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	function MoveOrgan(){
	setCookie("UsMenu","2");
		if(checkid>0){
			document.all.submsg.src="listMoveCanuseOrganAction.do?UIDI="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function organVisit(){
		setCookie("UsMenu","3");
			if(checkid>0){
				document.all.submsg.src="..${visitAuthUrl }?userid="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
	function SetCall(){
		if(checkid>0){
			popWin("listUserCallEventAction.do?muid="+checkid,700,400);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		checkedCount();
		if(checkCount >= 2){
			alert("请选择一条记录操作!");
			checkCount = 0;
			return;
		}
		checkCount = 0;
		if(checkid>0){
			if(approvalFlag==1){
				alert("该计划已审批!不能删除!");
				return;
			}
			if ( confirm("你确认要删除编号为:"+checkid+"的生产计划吗?如果删除将不能恢复!") ){
				popWin2("../erp/delProductPlanAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
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
		var p=showModalDialog("../common/selectOrganAction.do?type=toller",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.search.organId.value;
			document.search.organId.value=p.id;
			document.search.oname.value=p.organname;

		if(oldOrgan != p.id) {
			clearProductList()
		}
	}
	function approve(){
		checkedCount();
		if(checkCount >=2){
			alert("请选择一条记录操作!");
			checkCount = 0;
			return;
		}
		checkCount = 0;
		if(checkid>0){
			if(approvalFlag==1){
			  alert("该生产计划已经审批!");
			  return;
			}
			if(confirm("确认要审批该记录吗?")){
				popWin2("../erp/approveProductPlanAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
		
	}
	function reProcess(){
		checkedCount();
		if(checkCount>=2){
			alert("请选择一条记录操作!");
			checkCount = 0;
			return;
		}
		checkCount = 0;
		if(checkid>0){
			if(approvalFlag!=1){
			  alert("该生产计划还未审批!");
			  return;
			}
			if(tempflag=='已生成' || tempflag==''){
			  alert("只用于处理生成打印任务失败的计划!");
			  return;
			}
			if(tempflag=='处理中'){
			  alert("业务处理中,请稍后!");
			  return;
			}
			if(confirm("确认要重新处理该记录吗?")){
				popWin2("../erp/approveProductPlanAction.do?type=reProcess&ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}  	
	}
		
    function closePlan(){
    	checkedCount();
    	if(checkCount>=2){
        	alert("请选择一条记录操作!");
        	checkCount = 0;
        	return;
        }
       	checkCount = 0;
		if(checkid>0){
			if(approvalFlag!=1){
			  alert("该生产计划还未审批!");
			  return;
			}
			if(tempflag!='已生成'){
			  alert("对应的打印任务还未生成!");
			  return;
			}
			if(closeFlag ==1){
			  alert("该生产计划已结束!");
			  return;
			}
			popWin4("../erp/listProductPlanDetailAction.do?close=1&productPlanId="+checkid+"&ID="+checkid,1000,650,"code");
//			if(confirm("确认要结束该生产计划吗?")){
//				popWin2("../erp/closeProductPlanAction.do?type=reProcess&ID="+checkid);
//			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
		
	function downCode(){
		var checkProduPlanId ="";
		var closeFlagSum = 0;
		var isTure ="是";
		$("input[name='che']").each(function() {
			 if ($(this).is(':checked')) {
				 var flag = $(this).parent().parent().find("td:last-child").text().trim();
				 if(!(flag==isTure)){
					 closeFlagSum++;
				 }
				 checkProduPlanId += $(this).val()+",";	 				 
			}
		});
		if(checkProduPlanId!=""){
			if(closeFlagSum >=1){
				alert("生产计划未结束,请结束该计划后下载!");
			    return;
			}
			popWin2("../erp/downloadFinaCodeAction.do?type=download&ID="+checkProduPlanId);

		}else{
		 	alert("请选择你要操作的记录!");
		}
	}		
    
	function releasecode(){
		if(checkid>0){
			popWin4("../erp/listProductPlanDetailAction.do?productPlanId="+checkid,1000,650,"code");
		}else{
			alert("请选择你要操作的记录!");
		}  	
	}	

	function Import(){		
		popWin("../users/toImportUsersAction.do",500,300);
	}
	
	//导出
	function excput() {
		search.action="../erp/exprotProductPlanAction.do";
		search.submit();
		search.action="../erp/listProductPlanAction.do";
	}


	function Check() {
		var pid = document.all("che");
		var checkall = document.all("checkall");
		if (pid == undefined) {
			return;
		}
		if (pid.length) {
			for (i = 0; i < pid.length; i++) {
				pid[i].checked = checkall.checked;
			}
		} else {
			pid.checked = checkall.checked;
		}
	}
	
	//标记上传
	function updIsUpload(){

		var checkProduPlanId ="";
		var closeFlagSum =0;
		var isTure ="是";
		$("input[name='che']").each(function() {
			 if ($(this).is(':checked')) {
				 var flag = $(this).parent().parent().find("td:last-child").text().trim();
				 if(!(flag==isTure)){
					 closeFlagSum++;
				 }
				 checkProduPlanId += $(this).val()+",";
			}
		});
		if(checkProduPlanId==""){
			alert("请选择需要标记上传的记录!");
			return;
		}else{
			if(closeFlagSum >=1){
				alert("生产计划未结束,请结束该计划后标记是否上传!");
			    return;
			}
			popWin2("../erp/ajaxIsUploadProductPlanAction.do?productPlanId="+checkProduPlanId);
		}
	}
	
		
	function downCovertCode(){
		var checkProduPlanId ="";
		var closeFlagSum = 0;
		var isTure ="已生成";
		$("input[name='che']").each(function() {
			 if ($(this).is(':checked')) {
				 var flag = $(this).parent().parent().find("td:nth-child(9)").text().trim();
				 if(!(flag==isTure)){
					 closeFlagSum++;
				 } 
				 checkProduPlanId += $(this).val()+",";	 				 
			}
		});
		if(checkProduPlanId!=""){
			if(closeFlagSum >=1){
				alert("暗码还未生成,请生成后下载!");
			    return;
			}
			popWin2("../erp/downloadCovertCodeAction.do?type=download&ID="+checkProduPlanId);

		}else{
		 	alert("请选择你要操作的记录!");
		}
	}	
	function updCovertCode(){
		if(checkid>0){
			var isTure ="已生成";
			//if(closeFlag != 0) {
			//	alert("计划已结束,无法修改!");
			//    return;
			//}
			if(tempflag != '已生成'){
				alert("暗码还未生成,请生成后再修改!");
			    return;
			}
			popWin4("../erp/toUpdCovertCodeAction.do?close=1&type=download&ID="+checkid,1000,650,"code");

		}else{
			alert("请选择你要操作的记录!");
		}
	}	
	function selectCartonSeq() {
		if(linkModeFlag != "1"){
			alert("该计划为后关联，请选择上传按钮！");
			return;
		}
		
		if(checkid>0){
			/*if(closeFlag ==1){
			  alert("该生产计划已结束!");
			  return;
			}*/
			if(tempflag != '已生成'){
				alert("条码还未生成,请生成后再修改!");
			    return;
			}
			popWin4("../erp/toSelectCartonSeqAction.do?ID="+checkid,1000,650,"code");

		}else{
			alert("请选择你要操作的记录!");
		} 
	}
	
	function downloadPlan() {
		if(checkid>0){
			if(tempflag != '已生成'){
				alert("条码还未生成,请生成后再下载!");
			    return;
			}
			popWin2("../erp/downloadSelectedCartonSeqAction.do?type=download&ID="+checkid);
			/* search.action="../erp/downloadProductPlanAction.do";
			search.submit();
			search.action="../erp/listProductPlanAction.do"; */
		}else{
			alert("请选择你要操作的记录!");
		} 
	}
	
	function importCartonSeq(){
		if(linkModeFlag != "2"){
			alert("该计划为前关联，请选择箱码关联按钮！");
			return;
		}
		
		if(checkid>0){
			if(tempflag != '已生成'){
				alert("条码还未生成,请生成后再修改!");
			    return;
			}
			popWin("../erp/toImportCartonSeqAction.do?ID="+checkid,600,250);
		}else{
			alert("请选择你要操作的记录!");
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
									${menusTrace}
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listProductPlanAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">

									<td width="8%" align="right"> 
										工厂： 
									</td>
									<td width="12%">
										<input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td width="8%" align="right">
										PO编号：
									</td>
									<td width="12%">
										<input type="text" id="PONO" name="PONO" value="${PONO}" maxlength="30" />
									</td>
								<td align="right">
									产品：
								</td>
								<td>
									<input name="productId" type="hidden" id="productId" value="${productId}"/>
									<input name="productname" type="text" id="productname" size="30"  value="${productname}"
									readonly><a href="javascript:SelectProduct();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>									
								<td></td>
								</tr>
																
							  <tr class="table-back">
							  		<td width="8%" align="right">
										物料批次号：
									</td>
									<td width="25%">
										<input type="text" id="mbatch" name="mbatch" value="${mbatch}" maxlength="30"/>
									</td>
									<td width="8%" align="right">
										产品批次号：
									</td>
									<td width="25%">
										<input type="text" id="pbatch" name="pbatch" value="${pbatch}" maxlength="30"/>
									</td>	
								<td align="right">
									生产日期：
								</td>
								<td>
								<input name="proBeginDate" type="text" id="proBeginDate" size="10" value="${proBeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="proEndDate" type="text" id="proEndDate" size="10" value="${proEndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td></td>
							  </tr>
							  
							  <tr class="table-back">
								<td align="right">
									分装日期：
								</td>
								<td>
								<input name="packBeginDate" type="text" id="packBeginDate" size="10" value="${packBeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="packEndDate" type="text" id="packEndDate" size="10" value="${packEndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								
								<td align="right">
									是否审批：
								</td>
								<td>
								 <windrp:select key="YesOrNo" name="approvalFlag" value="${approvalFlag}" p="y|f"/>
								</td>
								<td align="right">
									是否上传：
								</td>
								<td>
								 <windrp:select key="YesOrNo" name="isUpload" value="${isUpload}" p="y|f"/>
								</td>
							</tr>
						    <tr class="table-back">
							<td width="8%" align="right">
									关键字：
								</td>
								<td width="25%">
									<input type="text" name="KeyWord" value="${KeyWord}">
								</td>
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
								<ws:hasAuth operationName="/erp/toAddProductPlanAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/toUpdProductPlanAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:UpdProductPlan();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;修改</a>
									</td>
								</ws:hasAuth>
								<%--								
								<ws:hasAuth operationName="/erp/toUpdProductPlanAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:UpdProductPlanCovertCode();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;设置暗码</a>
									</td>
								</ws:hasAuth>--%>
								<ws:hasAuth operationName="/erp/delProductPlanAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Del();"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/approveProductPlanAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:approve();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;审批</a>
									</td> 
								</ws:hasAuth>
								
								<ws:hasAuth operationName="/erp/reApproveProductPlanAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:reProcess();"><img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;重新处理</a></td>
								</ws:hasAuth>
								
<!--								<ws:hasAuth operationName="/erp/closeProductPlanAction.do">-->
<!--									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>-->
<!--									<td width="70" align="center">-->
<!--										<a href="javascript:releasecode();">-->
<!--										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;释放码</a></td>-->
<!--								</ws:hasAuth>-->
								<ws:hasAuth operationName="/erp/updCovertCodeAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:updCovertCode();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;暗码编辑</a></td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/closeProductPlanAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:closePlan();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;结束计划</a></td>
								</ws:hasAuth>
								
								<ws:hasAuth operationName="/erp/downloadFinaCodeAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="60" align="center">
										<a href="javascript:downCode();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载码</a></td>
								</ws:hasAuth>
								
								<ws:hasAuth operationName="/erp/exprotProductPlanAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="50" align="center">
										<a href="javascript:excput();">
										<img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/ajaxIsUploadProductPlanAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:updIsUpload();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;标记上传</a></td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/toSelectCartonSeqAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="70" align="center">
										<a href="javascript:selectCartonSeq();">
										<img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;箱码关联</a></td>
								</ws:hasAuth> 
								<ws:hasAuth operationName="/erp/toImportCartonSeqAction.do">
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
									<td width="50" align="center">
										<a href="javascript:importCartonSeq();">
										<img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/downloadCovertCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:downloadPlan();"> <img
												src="../images/CN/outputExcel.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;下载计划</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../erp/listProductPlanAction.do" />
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
									<td width="2%" class="sorttable_nosort">
									<input type="checkbox" name="checkall" onClick="Check();"></td>
									<td>
										编号
									</td>
									<td>
										PO编号
									</td>
									<td>
										工厂
									</td>
																	
									<td>
										物料号
									</td>

									<td>
										产品名称
									</td>
									<td>
										生产箱数
									</td>

									<td>
										是否审批
									</td>
									<td>
										生成状态
									</td>
									<td>
										是否上传
									</td>
									<td>
										是否结束
									</td>										
								</tr>
								<logic:iterate id="c" name="configs">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${c.id},${c.approvalFlag},'${c.temp}','${c.closeFlag}','${c.linkMode}');">
										<td align="center">
											<input type="checkbox" name="che" id="che" value='${c.id}'>
										</td>
										<td align="left">
											${c.id}
										</td>
										<td align="left">
											${c.PONO}
										</td>										
										<td align="left">
											<windrp:getname key='organ' value='${c.organId}' p='d' />
										</td>
										
										<td align="left">
											${c.mcode}
										</td>
										<td align="left">
											${c.productname}
										</td>
										<td align="left">
											${c.boxnum}
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.approvalFlag}' p='f'/>
										</td>
										<td align="left">
											${c.temp}
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.isUpload}' p='f'/>
										</td>
										<td align="left">
										    <windrp:getname key='YesOrNo' value='${c.closeFlag}' p='f'/>
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<div id="tabs1">
								<ul>
									<li><a id="DetailUrl" href="javascript:Detail();"><span>详情</span></a></li>
								</ul>
						</div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
					</div>
				</td>
			</tr>
		</table>


	</body>
</html>
