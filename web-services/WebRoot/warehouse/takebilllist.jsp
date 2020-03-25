<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {     
	 obj.parentNode.childNodes[i].className="table-back-colorbar";
	 		
	 	
	 		
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 Detail(); 
	 //get TT focus---RichieYu---20100601
	 document.all.submsg.focus();
	}

	
	function UploadIdcode(){
		window.open("../common/toUploadIdcodeOutAction.do?billsort=1","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function NoBillUploadIdcode(){
		window.open("../common/toUploadIdcodeOutAction.do?billsort=17","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function NoBillUpload(){
		window.open("../scanner/toUploadIdcodeAction.do","newwindow","height=300,width=600,top=300,left=350,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function Detail(){
		if(checkid!=""){ 
			document.all.submsg.src="../warehouse/takeTicketDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CloseBill(){
		if(checkid!=""){
		window.open("auditTakeBillAction.do?id="+checkid,"","height=300,width=400,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function CancelCloseBill(){
		if(checkid!=""){
		window.open("cancelAuditTakeBillAction.do?id="+checkid,"","height=300,width=400,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function SelectCustomer(){
		//var os = document.search.ObjectSort.value;
		var os = 0;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( o==undefined){return;}
		document.search.OName.value=o.organname;
		document.search.OID.value=o.id;
		}
		if(os==1){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( c == undefined ){return;}
		document.search.OName.value=c.cname;
		document.search.OId.value=c.id;
		}
		if(os==2){		
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){return;}
		document.search.OName.value=p.pname;
		document.search.OId.value=p.id;
		}

	}
	
	function SelectInOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( o==undefined){return;}
		document.search.inOname.value=o.organname;
		document.search.inOId.value=o.id;

	}
function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.search.MakeOrganID.value=p.id;
document.search.oname.value=p.organname;
clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}

function DownTxt(){
	excputform.target="";
	excputform.action="txtPutTakeTicketAction.do";
	excputform.submit();
}

function print(){
	search.target="_blank";
	search.action="printTakeBillAction.do";
	search.submit();
	search.target="";
	search.action="../warehouse/listTakeBillAction.do";
}
function excput(){
	search.target="";
	search.action="../warehouse/excPutTakeBillAction.do";
	search.submit();
	search.target="";
	search.action="../warehouse/listTakeBillAction.do";
}


this.onload =function onLoadDivHeight(){

/*
tommy 2015-12-25 计算有误， 多扣掉30px
document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight )+"px";
*/
document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight - 30)+"px";


}
	//RichieYu---20100602---reset TT bill
	function resetTT(){
		var returnValue = openwindow("../warehouse/selectAndResetTT.do",null,800,500);
	}
 
 
 function printBox(){
		if(checkid!=""){
		window.open("../warehouse/printPackingBillAction.do?type=1&ttid="+checkid,"","height=400,width=500,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}

 function Import() {
		window
				.open(
						"../warehouse/toImportTtidForIdcodeAction.do",
						"",
						"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

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
						<form name="search" method="post"
							action="../warehouse/listTakeBillAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="10%" align="right">
										出库机构：
									</td>
									<td width="25%">
										<input name="OName" type="text" id="OName" value="${OName}" readonly>
										<input type="hidden" name="OID" id="OID" value="${OID}" readonly>
										<a href="javascript:SelectCustomer();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0"> </a>
									</td>
									<td width="10%" align="right">
										入库机构：
									</td>
									<td width="25%">
										<input name="inOname" type="text" id="inOname" value="${inOname}"
											readonly>
										<input name="inOId" type="hidden" id="inOId" value="${inOId}"
											readonly>
										<a href="javascript:SelectInOrgan();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0"> </a>
									</td>
									<td colspan="3"></td>
								</tr>
								<tr class="table-back">
									<td width="8%" align="right">
										是否复核：
									</td>
									<td width="25%">
										<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
									</td>
									<td width="9%" align="right">
										单据类型：
									</td>
									<td width="25%">
										<windrp:select key="BSort" name="BSort" value="${BSort}" p="y|f" />
									</td>
									<td align="right">
										是否无单：
									</td>
									<td>
										<windrp:select key="YesOrNo" name="isNoBill" p="y|f" value="${isNoBill}" />
									</td>
									<td></td>
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
									<td width="10%" align="right">
										制单日期：
									</td>
									<td width="25%">
										<input name="BeginDate" type="text"
											onFocus="javascript:selectDate(this)" size="12" value="${BeginDate}"
											readonly>
										-
										<input name="EndDate" type="text"
											onFocus="javascript:selectDate(this)" value="${EndDate}" size="12"
											readonly>
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
									<td></td>
								</tr>
								<tr class="table-back">
									<td width="9%" align="right">
										关键字：
									</td>
									<td width="22%">
										<input type="text" name="KeyWord" value="${KeyWord}">
									</td>
									<td colspan="4"></td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/warehouse/excPutTakeBillAction.do">
									<td width="50">
										<a href="javascript:excput();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/warehouse/toImportTtidForIdcodeAction.do">
								<td width="130">
										<a href="javascript:Import();"><img
												src="../images/CN/import.gif" width="16" height="16"
												border="0" align="absmiddle">&nbsp;已复核单据条码导入</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<%--  
								<ws:hasAuth operationName="/warehouse/printTakeBillAction.do">
									<td width="50">
										<a href="javascript:print();"><img src="../images/CN/print.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								 --%>
								<td class="SeparatePage">
									<pages:pager action="../warehouse/listTakeBillAction.do" />
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
								<td width="13%">
									编号
								</td>
								<td>
									单据类型
								</td>
								<td>
									出库机构
								</td>
								<td>
									出库仓库
								</td>
								<td>
									入库机构
								</td>
								<td>
									入库仓库
								</td>
								<td>
									收货人
								</td>
								<td>
									电话
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单人
								</td>
								<td width="3%">
									是否关闭
								</td>
								<td width="3%">
									是否作废
								</td>
								<td width="3%">
									是否查看
								</td>
							</tr>

							<logic:iterate id="s" name="also">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
									<td class="${s.isblankout==1?'text2-red':''}" title="${s.id}">
										${s.billno} 
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='BSort' value='${s.bsort}' p='f' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										${s.oname}
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='warehouse' value='${s.warehouseid}' p='d' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										${s.inOname}
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='warehouse' value='${s.inwarehouseid}' p='d' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										${s.rlinkman}
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										${s.tel}
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='organ' value='${s.makeorganid}' p='d' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='users' value='${s.makeid}' p='d' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='YesOrNo' value='${s.isaudit}' p='f' />
									</td>
									<td class="${s.isblankout==1?'text2-red':''}">
										<windrp:getname key='YesOrNo' value='${s.isblankout}' p='f' />
									</td>
									<td class="${s.isread==1?'text2-red':''}">
										<windrp:getname key='YesOrNo' value='${s.isread}' p='f' />
									</td>

								</tr>
							</logic:iterate>
						</table>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li>
										<a href="javascript:Detail();"><span>${menu }详情</span>
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
			<input type="hidden" name="ObjectSort" id="ObjectSort" value="${ObjectSort}">
			<input type="hidden" name="OName" id="OName" value="${OName}">
			<input type="hidden" name="IsAudit" id="IsAudit" value="${IsAudit}">
			<input type="hidden" name="IsBlankOut" id="IsBlankOut" value="${IsBlankOut}">
			<input type="hidden" name="BeginDate" id="BeginDate" value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
		</form>
	</body>
</html>
