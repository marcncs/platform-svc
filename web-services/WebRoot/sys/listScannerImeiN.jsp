<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="ws" uri="ws"%>
<html>
<base target="_self">
	<head>
		<title>ScannerList--扫描器列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	var scannerImeiN={id:"",scannerid:""};
	function CheckedObj(obj,objid,objcname,objscannerid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 scannerImeiN.scannerid=objscannerid;
	 scannerImeiN.id=objid;
	 scannerImeiN.scannerid=objscannerid;
	}

	function Affirm(){
		if(checkid!=""){
		window.returnValue=scannerImeiN;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<table width="102%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%"  border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									采集器管理&gt;&gt;采集器
									<input type="hidden" name="ID" value="${id}">
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="../sys/searchScannerImeiNAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td  align="right">
										编号:
									</td>
									<td >
										<input type="text" name="idSearch" value="${idSearch}"
											maxlength="16" size="30">
									</td>
									<td  align="right">
										型号:
									</td>
									<td >
										<input type="text" name="modelSearch" value="${modelSearch}"
											maxlength="16" size="30">
									</td>
									<td  align="right">
										系统版本:
									</td>
									<td >
										<input type="text" name="osVersionSearch"
											value="${osVersionSearch}" maxlength="16" size="30">
									</td>
								</tr>
								<tr class="table-back">
									<td  align="right">
										采集器编号:
									</td>
									<td >
										<input type="text" name="scannerImeiNSearch"
											value="${scannerImeiNSearch}" maxlength="16" size="30">
									</td>
									<td  align="right">
										状态是否可用:
									</td>
									<td >
										<windrp:select key="YesOrNo" name="statusSearch"
											value="${statusSearch}" p="y|f" />
									</td>
									<td  align="right">
										安装日期:
									</td>
									<td >
										<input type="text" name="installDateSearch"
											value="${installDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
								</tr>
								<tr class="table-back">
									<td  align="right">
										软件版本:
									</td>
									<td >
										<input type="text" name="appVersionSearch"
											value="${appVersionSearch}" maxlength="16" size="30">
									</td>
									<td align="right">
										更新日期:
									</td>
									<td >
										<input type="text" name="appVerUpDateSearch"
											value="${appVerUpDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr class="table-back">
									<td  align="right">
										名字:
									</td>
									<td >
										<input type="text" name="scannerNameSearch"
											value="${scannerNameSearch }" maxlength="16" size="30">
									</td>
									<td  align="right">
										最后更新日期:
									</td>
									<td >
										<input type="text" name="lastUpDateSearch"
											value="${lastUpDateSearch}" maxlength="16" size="30"
											onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td></td>
									<td align="right">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="#" onClick="javascript:Affirm();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;取消</a>
								</td>
								<td class="SeparatePage"><pages:pager action="../sys/searchScannerImeiNAction.do" /></td>
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
									<td >
										编号
									</td>
									<td >
										型号
									</td>
									<td >
										系统版本
									</td>
									<td >
										采集器编号
									</td>
									<td >
										状态
									</td>
									<td >
										安装日期
									</td>
									<td >
										软件版本
									</td>
									<td >
										更新日期
									</td>
									<td >
										名字
									</td>
									<td >
										最后更新日期
									</td>
								</tr>
								<%-- 输出Scanner表中的数据 --%>
								<logic:iterate id="p" name="scanner">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${p.id}','${p.model}','${p.scannerImeiN}');"
										onDblClick="Affirm();">
										<td>
											${p.id}
										</td>
										<td>
											${p.model}
										</td>
										<td>
											${p.osVersion}
										</td>
										<td>
											${p.scannerImeiN}
										</td>
										<td>
											<windrp:getname key='YesOrNo' value='${p.status}' p='f' />
										</td>
										<td>
											${p.installDate}
										</td>
										<td>
											${p.appVersion}
										</td>
										<td>
											${p.appVerUpDate}
										</td>
										<td>
											${p.scannerName}
										</td>
										<td>
											${p.lastUpDate}
										</td>
									</tr>
								</logic:iterate>
							</table>
						</form>
					</div>
		</table>
		<br>
		
	</body>
</html>
