<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>机构转仓申请</title>
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
	
	function addNew(){
		popWin("../warehouse/toAddMoveApplyAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("../warehouse/toUpdMoveApplyAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="moveApplyDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutMoveApplyAction.do?ISAUDIT=no";
		search.submit();
		search.action="listMoveApplyAction.do";
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../warehouse/printListMoveApplyAction.do?ISAUDIT=no";
		excputform.submit();
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 的转仓申请 吗？如果删除将永远不能恢复!")){
			popWin2("../warehouse/delMoveApplyAction.do?ID="+checkid);
			}
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

	function SelectOrganIn(){
		var p=showModalDialog("../common/selectMoveOrganAction.do?rank="+'${organRank}'+"&organType="+'${organType}'+"&OID="+'${MakeOrganID}',null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.InOrganID.value=p.id;
			document.search.inoname.value=p.organname;
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
							<td>
								  销售管理>>机构转仓申请 
							</td>
						</tr>
					</table>
					<form name="search" method="post" action="listMoveApplyAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="12%" align="right">
								调出机构：
							</td>
							<td width="23%">
								<input name="OutOrganID" type="hidden" id="OutOrganID" value="${OutOrganID}">
								<input name="outoname" type="text" id="outoname" size="30" value="${outoname}" 
								readonly><a href="javascript:SelectOrganout();"><img 
								src="../images/CN/find.gif" width="18" height="18"
								 border="0" align="absmiddle"></a>
								
							</td>
							<td align="right">
								调入机构：
							</td>
							<td>
								<input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
								<input name="inoname" type="text" id="inoname" size="30" value="${inoname}" 
								readonly><a href="javascript:SelectOrganIn();"><img 
								src="../images/CN/find.gif" width="18" height="18"
								 border="0" align="absmiddle"></a>
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
							<td align="right">
								是否批准：
							</td>
							<td>
								<windrp:select key="ConfirmStatus" name="IsRatify" p="y|f" value="${IsRatify}"/>
							</td>
							<td  align="right">
								关键字：
							</td>
							<td >
								<input type="text" name="KeyWord" value="${KeyWord}">&nbsp;
							</td>
							<td></td>
							<td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<ws:hasAuth operationName="/warehouse/toAddMoveApplyAction.do">
							<td width="50">
								<a href="javascript:addNew();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/warehouse/toUpdMoveApplyAction.do">
							<td width="50">
								<a href="javascript:Update();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/warehouse/delMoveApplyAction.do">
							<td width="50">
								<a href="javascript:Del()"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
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
								<pages:pager action="../warehouse/listMoveApplyAction.do" />
							</td>
						</tr>
					</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="GET" name="listform" ACTION="">
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
								机构转仓需求日期
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
<!--							<td width="7%">-->
<!--								是否复核-->
<!--							</td>-->
							<td width="7%">
								是否批准
							</td>
						</tr>
						<logic:iterate id="sa" name="als">
							<tr class="table-back-colorbar"
								onClick="CheckedObj(this,'${sa.id}');">
								<td class="${sa.isblankout==1?'td-blankout':''}">
									${sa.id}
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:getname key="MoveType" p="f" value="${sa.movetype}"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:dateformat value="${sa.movedate}" p="yyyy-MM-dd"/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
<%--									<windrp:getname key="organ" p="d" value="${sa.outorganid}"/>--%>
									${sa.outoname}
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
<%--								<windrp:getname key="organ" p="d" value="${sa.inorganid}"/>--%>
									${sa.inoname} 
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key="users" p="d" value="${sa.makeid}"/>
								</td>
<!--								<td class="${sa.isblankout==1?'td-blankout':''}">-->
<!--									<windrp:getname key="YesOrNo" p="f" value="${sa.isaudit}"/>-->
<!--								</td>-->
								<td class="${sa.isblankout==1?'td-blankout':''}">
          							<windrp:getname key="ConfirmStatus" p="f" value="${sa.isratify}"/>
								</td>
							</tr>
						</logic:iterate>
						
					</table>
					</form>
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
<input type="hidden" name="IsRatify" id ="IsRatify" value="${IsRatify}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="IsBlankOut" id ="IsBlankOut" value="${IsBlankOut}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
	</body>
</html>
