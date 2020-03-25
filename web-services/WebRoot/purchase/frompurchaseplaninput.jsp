<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}

	function Affirm(){
		if(checkid!=0){
			location.href("../purchase/toTransPurchaseBillAction.do?PPID="+checkid);			
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Hidden(){
		if(checkid!=""){
			window.open("../purchase/hiddenPurchasePlanAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

</script>
	</head>

	<body>

		<table width="100%" border="1" cellpadding="0" cellspacing="1"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								选择采购计划
							</td>
						</tr>
					</table>
					<form name="search" method="post"
							action="selectPurchasePlanAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="11%" align="right">
								单据日期：
							</td>
							<td width="89%">
								<input name="BeginDate" type="text" id="BeginDate" size="10"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10"
									onFocus="javascript:selectDate(this)" readonly>
							</td>
                            <td width="8%" class="SeparatePage" >
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="120">
								<a href="javascript:Affirm();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle"> 转为采购订单</a>
						  </td>
                          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td width="60">
								<a href="javascript:window.close();"><img src="../images/CN/return.gif" width="16" height="16" border="0" align="absmiddle"></a> <a href="javascript:Hidden();">隐藏</a>
						  </td>

							<td style="text-align: right;">
								<pages:pager action="../purchase/selectPurchasePlanAction.do" />
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						
						<tr align="center" class="title-top">
							<td width="12%">
								编号
							</td>
							<td width="19%">
								采购类型
							</td>
							<td width="34%">
								计划日期
							</td>
							<td width="19%">
								计划部门
							</td>
							<td width="16%">
								计划人
							</td>
						</tr>
						<logic:iterate id="p" name="alpa">
							<tr align="center" class="table-back-colorbar"
								onClick="CheckedObj(this,'${p.id}');">
								<td>${p.id}</td>
								<td>
									
									<windrp:getname key="PurchaseSort" p="d" value="${p.purchasesort}"/>
								</td>
								<td>
									<windrp:dateformat value='${p.plandate}' p='yyyy-MM-dd'/>
								</td>
								<td>
									<windrp:getname key='dept' value='${p.plandept}' p='d'/> 
								</td>
								<td>
									<windrp:getname key='users' value='${p.planid}' p='d'/>
								</td>
							</tr>
						</logic:iterate>

					</table>
					

				</td>
			</tr>
		</table>
	</body>
</html>
