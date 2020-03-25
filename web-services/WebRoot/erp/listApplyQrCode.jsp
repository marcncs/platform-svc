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
	popWin("../erp/toAddApplyQrCodeAction.do",900,600);
	}
	
	function UpdApplyQrCode(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("<bean:message key='msg.upd.alreadyapproved'/>!");
				return;
			}
		popWin("../erp/toUpdApplyQrCodeAction.do?ID="+checkid,900,600);
		}else{
		alert("<bean:message key='sys.selectrecord'/>!");
		}
		
	}
	
	function Del(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("<bean:message key='msg.del.alreadyapproved'/>!");
				return;
			}
			if ( confirm("<bean:message key='msg.del'/>?") ){
				popWin2("../erp/delApplyQrCodeAction.do?ID="+checkid);
			}
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}

	function Audit(){
		if(checkid>0){
			if(checkaudit == 1) {
				alert("<bean:message key='msg.alreadyapproved'/>!");
				return;
			}
			if ( confirm("<bean:message key='msg.approve'/>?") ){
				popWin2("../erp/auditApplyQrCodeAction.do?ID="+checkid);
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
									<bean:message key='tolling.title'/>>><bean:message key='tolling.applycode'/>
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listApplyQrCodeAction.do">
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
								<ws:hasAuth operationName="/erp/toAddApplyQrCodeAction.do">
									<td width="50" align="center">
										<a href="javascript:addNew();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.add'/></a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/toUpdApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:UpdApplyQrCode();"><img
												src="../images/CN/update.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;<bean:message key='system.update'/></a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/delApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="50" align="center">
										<a href="javascript:Del();"><img src="../images/CN/delete.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.del'/></a>
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/erp/auditApplyQrCodeAction.do">
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
									<td width="70" align="center">
										<a href="javascript:Audit();"><img src="../images/CN/update.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.approve'/></a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager language="en" action="../erp/listApplyQrCodeAction.do" />
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
										<bean:message key='tolling.applycode.cartoncount'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.covertcode'/>
									</td> 
									<td>
										<bean:message key='tolling.applycode.approval'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.completion'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.issuingdate'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.po'/>
									</td>
									<td>
										<bean:message key='tolling.applycode.codefile'/>
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
											${c.totalqty}
										</td>
										<td align="left">
											<windrp:getname language="en" key='YesOrNo' value='${c.needcovertcode}' p='f'/>
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
											<a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${c.filepath}'/>">[<bean:message key='system.download'/>] </a>
										</c:if>
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
