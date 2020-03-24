<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>批准机构转仓审核</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	StockMoveDetail();
	}
	
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="ratifyMoveApplyDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
				clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
	
	function SelectOrganout(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.OutOrganID.value=p.id;
			document.search.outoname.value=p.organname;
	}
	
	function SelectOrganout2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.InOrganID.value=p.id;
			document.search.inoname.value=p.organname;
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutMoveApplyAction.do?ISAUDIT=yes";
		search.submit();
		search.action="listRatifyMoveApplyAction.do";
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../warehouse/printListMoveApplyAction.do?ISAUDIT=yes";
		excputform.submit();
	}
	
	this.onload = function onLoadDivHeight(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
							<td width="772">
								销售管理>>机构转仓申请审核
							</td>
						</tr>
					</table>
					<form name="search" method="post"
							action="listRatifyMoveApplyAction.do">
					<input type="hidden" name="isSearch" id ="isSearch" value="${isSearch}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="12%" align="right">
								调出机构：
							</td>
							<td width="23%">
								<input name="OutOrganID" type="hidden" id="OutOrganID" value="${OutOrganID}">
								<input name="outoname" type="text" id="outoname" size="30" value="${outoname}" 
									readonly><a href="javascript:SelectOrganout();"><img
										src="../images/CN/find.gif" width="18" height="18" border="0"
										align="absmiddle">
								</a>

							</td>
							<td width="12%" align="right">
								调入机构：
							</td>
							<td width="23%">
								<input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
								<input name="inoname" type="text" id="inoname" size="30" value="${inoname}" 
									readonly><a href="javascript:SelectOrganout2();"><img
										src="../images/CN/find.gif" width="18" height="18" border="0"
										align="absmiddle">
								</a>
							</td>
							<td width="9%" align="right">
								制单人：
							</td>
							<td width="24%">
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
								<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly>
							</td>
							<td></td>
						</tr>

						<tr class="table-back">
							
							
							<td align="right">是否批准：</td>
							<td><windrp:select key="ConfirmStatus" name="IsRatify" p="y|f" value="${IsRatify}"/>
							<td align="right">关键字：
							</td>
							<td><input type="text" name="KeyWord" value="${KeyWord}">
						  </td>
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
						<ws:hasAuth operationName="/warehouse/excPutMoveApplyAction.do">
						<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
							</ws:hasAuth>
<%--							
							<td width="50">
								<a href="javascript:print();"><img
										src="../images/CN/print.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;打印</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							--%>
							<td class="SeparatePage">
								<pages:pager action="../warehouse/listRatifyMoveApplyAction.do" />
							</td>
						</tr>
					</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1"
						class="sortable">
						<tr align="center" class="title-top">
							<td width="12%">
								编号
							</td>
							<td width="5%">
								类型
							</td>
							<td width="10%">
								需求日期
						  </td>
							<td width="17%">
								调出机构
							</td>
							<td width="15%">
								调入机构
							</td>
							<td width="10%">
								制单人
							</td>
							<td width="7%">
								是否批准
							</td>
						</tr>
						<logic:iterate id="sa" name="als">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');">
								<td class="${sa.isblankout==1?'td-blankout':''}">
									${sa.id}
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:getname key="MoveType" p="f" value="${sa.moveType}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:dateformat value="${sa.movedate}" p="yyyy-MM-dd"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="organ" p="d" value="${sa.outorganid}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="organ" p="d" value="${sa.inorganid}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="users" p="d" value="${sa.makeid}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="ConfirmStatus" p="f" value="${sa.isratify}"/>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
					<div style="width:100%">
				      	<div id="tabs1">
				            <ul>
				              <li><a href="javascript:StockMoveDetail();"><span>详情</span></a></li>
				            </ul>
				          </div>
				          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
							frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
				      </div>
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="OutOrganID" id ="OutOrganID" value="${OutOrganID}">
<input type="hidden" name="IsRatify" id ="OutOrganID" value="${IsRatify}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="IsBlankout" id ="IsBlankout" value="${IsBlankout}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
	</body>
</html>
