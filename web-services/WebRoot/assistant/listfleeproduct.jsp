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
	var queryid="";
	function CheckedObj(obj,objid,objqueryid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 queryid=objqueryid;
	}
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}

function excput(){
	search.action="excPutFleeProductAction.do";
	search.submit();
	search.action="../assistant/listFleeProductAction.do";
}

function Del(){
	if(checkid!=""){
	if(window.confirm("您确认要删除编号为:"+queryid+"的记录吗？如果删除将永远不能恢复!")){
		popWin2("../assistant/delFleeProductAction.do?ID="+checkid);
		}
	}else{
	alert("请选择你要操作的记录!");
	}
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
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="../assistant/listFleeProductAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<%--
								<tr class="table-back">
									<td align="right">
										制单部门：
									</td>
									<td>
										<input type="hidden" name="MakeDeptID" id="MakeDeptID"
											value="${MakeDeptID }">
										<input type="text" name="deptname" id="deptname"
											onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
											value="${deptname }" readonly>
									</td>
									<td align="right">
										制单人：
									</td>
									<td>
										<input type="hidden" name="MakeID" id="MakeID" value="${MakeID }">
										<input type="text" name="uname" id="uname"
											onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
											value="${uname }" readonly>
									</td>
									
									<td></td>
									<td></td>
								</tr>
								--%>
								<tr class="table-back">
									<td align="right" width="10%">
										查询机构：
									</td>
									<td width="21%">
										<input name="MakeOrganID" type="hidden" id="MakeOrganID"
											value="${MakeOrganID }">
										<input name="oname" type="text" id="oname" value="${oname }" size="30"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle"> </a>
									</td>
									<%--<td width="9%" align="right">
										制单日期：
									</td>
									<td width="23%">
										<input name="BeginDate" type="text" id="BeginDate"
											value="${BeginDate }" size="10" onFocus="javascript:selectDate(this)">
										-
										<input name="EndDate" type="text" id="EndDate" value="${EndDate }"
											size="10" onFocus="javascript:selectDate(this)">
									</td>
									--%><td width="10%" align="right">
										是否查询：
									</td>
									<td width="16%">
										<windrp:select key="YesOrNo" name="IsDeal"
											value="${IsDeal}" p="y|f" />
									</td>
									<td width="12%" align="right">
										关键字：
									</td>
									<td width="16%">
										<input type="text" name="KeyWord" maxlength="60" value="${KeyWord }">
									</td>
									<td width="9%" align="right">
										&nbsp;
									</td>
									<td width="31%" class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>

							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<%--<td width="50">
									<a href="#" onClick="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								--%>
								<ws:hasAuth operationName="/assistant/excPutFleeProductAction.do">
								<td width="50">
									<a href="javascript:excput();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;导出</a>
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../assistant/listFleeProductAction.do" />
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
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top-lock">
									<td width="10%">
										查询号
									</td>
									<!--<td width="7%"  >查询编号</td>-->
									<td width="7%">
										箱码
									</td>
									<td width="5%">
										申请机构
									</td>
									<td width="5%">
										一级机构
									</td>
									<!--<td width="6%" >客户编号</td>-->
									<td width="4%">
										二级机构
									</td>
									<td width="8%">
										产品名称
									</td>
									<td width="4%">
										规格
									</td>
									<!--
            <td width="7%" >物流起始号</td>
            <td width="8%" >物流结束号</td>    
            -->
									<td width="4%">
										发货日期
									</td>
									<td width="3%">
										是否查询
									</td>
									<td width="7%">
										查询时间
									</td>
								</tr>
								<logic:iterate id="pi" name="alsb">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${pi.id}','${pi.queryid}');">
										<td>
											${pi.queryid}
										</td>
										<td>
											${pi.cartoncode }
										</td>
										<!--<td >${pi.queryid}</td>-->
										<td>
											<windrp:getname key='organ' value='${pi.makeorganid}' p='d' />
										</td>
										<td>
											<windrp:getname key='organ' value='${pi.firstorgan}' p='d' />
										</td>
										<td>
											<windrp:getname key='organ' value='${pi.secondorgan}' p='d' />
										</td>
										<!--<td>${pi.cid}</td>-->
										<td>
											${pi.productname}
										</td>
										<td>
											${pi.specmode}
										</td>
										<td>
											<windrp:dateformat value='${pi.deliverdate}' p='yyyy-MM-dd' />
											
										</td>
										<td>
											<windrp:getname key='YesOrNo' value='${pi.isdeal}' p='f' />
										</td>
										<td>
											<windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd HH:mm:ss' />
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
	</body>
</html>
