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
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	function addNew(){
	popWin("../erp/toAddCartonSeqLogAction.do",900,600);
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
									<bean:message key='tolling.title'/>>><bean:message key='tolling.cartonseqactivate.title'/>
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="../erp/listCartonSeqLogAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="8%" align="right"> 
										<bean:message key='tolling.applycode.plant'/>： 
									</td>
									<td width="26%">
										<input name="organId" type="hidden" id="organId" value="${organId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<td width="8%" align="right">
										<bean:message key='system.keyword'/>：
									</td>
									<td width="25%">
										<input type="text" name="KeyWord" value="${KeyWord}">
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
								<ws:hasAuth operationName="/erp/toAddCartonSeqLogAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.add'/></a>
									</td>
								</ws:hasAuth>
								<%--<ws:hasAuth operationName="/erp/toUpdApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:UpdApplyQrCode();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;修改</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/delApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Del();"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/auditApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Audit();"><img src="../images/CN/update.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;审核</a>
									</td>
								</ws:hasAuth>
								--%><td class="SeparatePage">
									<pages:pager language="en" action="../erp/listCartonSeqLogAction.do" />
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
										<bean:message key='system.id'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.plant'/>
									</td>
									<td>
										<bean:message key='product.materialcode'/>
									</td>
									<td>
										<bean:message key='product.name'/>
									</td>
									<td>
										<bean:message key='product.specification'/>
									</td>
									<td>
										<bean:message key='product.batch'/>
									</td>
									
									<td>
										<bean:message key='tolling.cartonseqactivate.producedate'/>
									</td>
									<td>
										<bean:message key='tolling.cartonseqactivate.packingdate'/>
									</td>
									<td>
										<bean:message key='tolling.cartonseqactivate.inspectiondate'/>
									</td>
									<td>
<%--										<bean:message key='tolling.cartonseqactivate.inspectionInstitution'/>--%>
									</td>
									
									<td>
										<bean:message key='tolling.cartonseqactivate.seqrange'/>
									</td>
									<td>
										<bean:message key='tolling.cartonseqactivate.issuingdate'/>
									</td>
								</tr> 
								<logic:iterate id="c" name="aqcs">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,${c.id});">
										<td align="left">
											${c.id}
										</td>
										<td align="left">
											${c.organname}
										</td>
										<td align="left">
											${c.mcode}
										</td>
										<c:choose>
							            	<c:when test="${'en' eq localeLanguage}">
							            		<td align="left">${c.productnameen}</td>
							            	</c:when>
							            	<c:otherwise>
							            		<td align="left">${c.productname}</td>
							            	</c:otherwise>
							            </c:choose>
										<c:choose>
							            	<c:when test="${'en' eq localeLanguage}">
							            		<td align="left">${c.packsizenameen}</td>
							            	</c:when>
							            	<c:otherwise>
							            		<td align="left">${c.specmode}</td>
							            	</c:otherwise>
							            </c:choose>
							            <td align="left">
											${c.batch}
										</td>
										<td align="left">
											<windrp:dateformat value='${c.productiondate}' p='yyyy-MM-dd' />
										</td>
										<td align="left">
											<windrp:dateformat value='${c.packingdate}' p='yyyy-MM-dd' />
										</td>
										<td align="left">
											<windrp:dateformat value='${c.inspectiondate}' p='yyyy-MM-dd' />
										</td>
										<td align="left">
<%--											${c.inspectioninstitution}--%>
										</td>
										<td align="left">
											${c.range}
										</td>
										<td align="left">
											${c.makedate}
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
