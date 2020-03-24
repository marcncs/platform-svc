<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
	 OrganWithdrawDetail();
	}
	
	function OrganWithdrawDetail(){
		if(checkid!=""){
			document.all.submsg.src="../warehouse/detailOrganWithdrawReceiveAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function print(){
		if(checkid!=""){
			popWin("../ditch/printOrganWithdrawAction.do?ID="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function DownTxt(){
	excputform.action="txtPurOrganWithdrawAction.do";
	excputform.submit();
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutOrganWithdrawReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOrganWithdrawReceiveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListOrganWithdrawReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOrganWithdrawReceiveAction.do";
	}
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=8", 500, 250);
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}
	function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.POrganID.value=p.id;
			document.search.POrganName.value=p.organname;
	}	
	function SelectReceiveOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.receiveorganid.value=p.id;
			document.search.receiveorganname.value=p.organname;
	}
	
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
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
							action="../warehouse/listOrganWithdrawReceiveAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<%--<tr class="table-back">
									<td align="right">

										编号：
									</td>
									<td>
										<input type="text" name="ID" value="${ID}" maxlength="60">
									</td>
									<td colspan="5"></td>
								</tr>
								--%><tr class="table-back">
									<td align="right">

										退货机构：
									</td>
									<td>
										<input name="POrganID" type="hidden" id="POrganID" value="${POrganID}">
										<input name="POrganName" type="text" id="POrganName"
											value="${POrganName}" size="30" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td align="right">
										入库机构：
									</td>
									<td>
										<input name="receiveorganid" type="hidden" id="receiveorganid"
											value="${receiveorganid}">
										<input name="receiveorganname" type="text" id="receiveorganname"
											value="${receiveorganname}" size="30" readonly>
										<a href="javascript:SelectReceiveOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td  align="right">
										入库仓库：
									</td>
									<td >
										<input type="hidden" name="InWarehouseID" id="InWarehouseID"
											value="${InWarehouseID}">
										<input type="text" name="wname" id="wname"
											onClick="selectDUW(this,'InWarehouseID',$F('receiveorganid'),'rw')"
											value="${wname}" readonly>
									</td>
									<td colspan="2"></td>
								</tr> 
								<tr class="table-back">
									<td align="right">
										制单机构：
									</td>
									<td>
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>
									<td align="right">
										制单人：
									</td>
									<td>
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname}" readonly>
									</td>
									<td colspan="3"></td>
								</tr>
								<tr class="table-back">
									<td align="right">
										制单日期：
									</td>
									<td>
										<input name="BeginDate" type="text" id="BeginDate"
											onFocus="javascript:selectDate(this)" value="${BeginDate}" size="10"
											readonly>
										-
										<input name="EndDate" type="text" id="EndDate" value="${EndDate}"
											onFocus="javascript:selectDate(this)" size="10" readonly>
									</td>
									<td align="right">
										是否签收：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="IsComplete" p="y|f"
											value="${IsComplete}" />
									</td>
									
									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" maxlength="60" value="${KeyWord}">
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
								<ws:hasAuth
									operationName="/warehouse/excPutOrganWithdrawReceiveAction.do">
									<td width="50">
										<a href="javascript:excput();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth
									operationName="/warehouse/printListOrganWithdrawReceiveAction.do">
									<td width="50">
										<a href="javascript:print();"><img src="../images/CN/print.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listOrganWithdrawReceiveAction.do" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="15%">
									编号
								</td>
								<td width="12%">
									退货机构
								</td>
								<td width="12%">
									退货仓库
								</td>
								<td width="12%">
									入库机构
								</td>
								<td width="12%">
									入库仓库
								</td>
								<td width="8%">
									制单人
								</td>
								<td width="10%">
									制单日期
								</td>
								<td width="7%">
									是否签收
								</td>
							</tr>
							<logic:iterate id="o" name="list">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${o.id}');">
									<td class="${o.isblankout==1?'td-blankout':''}">
										${o.id}
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${o.porganid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${o.warehouseid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${o.receiveorganid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${o.inwarehouseid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='users' value='${o.makeid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:dateformat value="${o.makedate}" p="yyyy-MM-dd" />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${o.iscomplete}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:OrganWithdrawDetail();"><span>渠道退货详情</span>
										</a>
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

					</div>
				</td>
			</tr>
		</table>
		<form name="excputform" method="post" action="">
			<input type="hidden" name="MakeOrganID" id="MakeOrganID"
				value="${MakeOrganID}">
			<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
			<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="hidden" name="POrganID" id="POrganID" value="${POrganID}">
			<input type="hidden" name="IsComplete" id="IsComplete" value="${IsComplete}">
			<input type="hidden" name="BeginDate" id="BeginDate" value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
		</form>
	</body>
</html>
