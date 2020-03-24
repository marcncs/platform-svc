<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<script language="JavaScript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}

function OutPut(){
	search.action="excPutWlmIdcodeLogAction.do";
	search.submit();
	search.action="../assistant/listWlmIdcodeLogAction.do";
}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
						<form name="search" method="post"
							action="../assistant/listWlmIdcodeLogAction.do">

							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										查询机构：
									</td>
									<td>
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID }">
										<input name="oname" type="text" id="oname" value="${oname }" size="30"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<!--<td align="right">制单部门：</td>
            <td><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID }">
            <input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" value="${deptname }" readonly></td>
            -->
									<td align="right">
										查询人：
									</td>
									<td>
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID }">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname }" readonly>
									</td>
									<td width="9%" align="right">
										查询日期：
									</td>
									<td width="20%">
										<input name="BeginDate" type="text" id="BeginDate"
											value="${BeginDate }" size="10" onFocus="javascript:selectDate(this)">
										-
										<input name="EndDate" type="text" id="EndDate" size="10"
											value="${EndDate }" onFocus="javascript:selectDate(this)">
									</td>

								</tr>
								<tr class="table-back">
									<td align="right">
										查询分类：
									</td>
									<td>
										<windrp:select key="WlmQuerySort" name="QuerySort"
											value="${QuerySort}" p="y|f" />
									</td>
									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" value="${KeyWord }" maxlength="60">
									</td>
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
								<ws:hasAuth operationName="/assistant/excPutWlmIdcodeLogAction.do">
									<td width="50">
										<a href="#" onClick="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../assistant/listWlmIdcodeLogAction.do" />
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow: auto; height: 600px; width: 100%;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="120%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top-lock">
									<td width="2%">
										查询编号
									</td>
									<td width="6%">
										查询码
									</td>
									<td width="6%">
										箱码
									</td>
									<td width="4%">
										查询机构
									</td>
									<td width="4%">
										查询省份
									</td>
									<!--<td width="10%">
										仓库编号
									</td>
									<td width="4%">
										仓库名称
									</td>
									<td width="6%">
										内部编码
									</td>
									<td width="9%">
										客户名称
									</td>
									-->
									<td width="3%">
										产品编号
									</td>
									<td width="7%">
										产品名称
									</td>
									<td width="3%">
										规格
									</td>

									<td width="5%">
										查询时间
									</td>
									<td width="3%">
										查询人
									</td>
									<td width="4%">
										查询分类
									</td>
									<td width="6%">
										实际销售机构
									</td>
								</tr>
								<logic:iterate id="pi" name="alsb">
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${pi.id}');">
										<td>
											${pi.id }
										</td>
										<td>
											${pi.wlmidcode}
										</td>
										<td>
											${pi.cartoncode}
										</td>
										<td>
											<windrp:getname key='organ' value='${pi.makeorganid}' p='d' />
										</td>
										<td>
											<windrp:getname key='countryarea' value='${pi.province}' p='d' />
										</td>

										<!--<td>
											${pi.warehouseid}
										</td>
										<td>
											<windrp:getname key='warehouse' value='${pi.warehouseid}' p='d' />
										</td>
										<td>
											${pi.syncode}
										</td>
										<td>
											${pi.cname}
										</td>
										-->
										<td>
											${pi.productid}
										</td>
										<td>
											${pi.productname}
										</td>
										<td>
											${pi.specmode}
										</td>

										<td>
											<windrp:dateformat value='${pi.makedate}' />
										</td>
										<td>
											<windrp:getname key='users' value='${pi.makeid}' p='d' />
										</td>
										<td>
											<windrp:getname key='WlmQuerySort' value='${pi.querysort}' p='f' />
										</td>
										<td>
											<windrp:getname key='organ' value='${pi.organid}' p='d' />
										</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>
		<form name="excputform" method="post" action="">
			<input type="hidden" name="MakeOrganID" id="MakeOrganID"
				value="${MakeOrganID}">
			<input type="hidden" name="oname" id="oname" value="${oname}">
			<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="hidden" name="BeginDate" id="BeginDate" value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
			<input type="hidden" name="QuerySort" id="QuerySort" value="${QuerySort}">
		</form>
	</body>
</html>
