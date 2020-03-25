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
	 SupplySaleMoveDetail();
	}
	
	function addNew(){
		popWin("toAddSupplySaleMoveAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("../ditch/toUpdSupplySaleMoveAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function SupplySaleMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="../ditch/detailSupplySaleMoveAction.do?ID="+checkid+"&ISAUDIT=${ISAUDIT}";
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的代销单吗？如果删除将永远不能恢复!")){
				popWin("../ditch/delSupplySaleMoveAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function ToInput(){
		popWin("../ditch/selectSupplySaleApplyAction.do",1000,650);
	}
	
	function excput(){
		search.target="";
		search.action="../ditch/excPutSupplySaleMoveAction.do";
		search.submit();
		search.action="listSupplySaleMoveAction.do";s
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../ditch/printListSupplySaleMoveAction.do";
		excputform.submit();
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
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.InOrganID.value=p.id;
			document.search.oname1.value=p.organname;
	}
	
	function SelectOrgan3(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.SupplyOrganID.value=p.id;
			document.search.oname2.value=p.organname;
	}
	
	
</script>
	</head>
		<body >
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
									
									渠道管理>>代销
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listSupplySaleMoveAction.do" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									是否复核：
								</td>
								<td width="27%">
									<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
								</td>
								<td width="10%" align="right">
									是否批准：
								</td>
								<td width="21%">
									<windrp:select key="YesOrNo" name="IsRatify" p="y|f" value="${IsRatify}" />
								</td>
								<td width="10%" align="right">
									需求日期：
								</td>
								<td width="19%">
									<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" 
										onFocus="javascript:selectDate(this)" readonly>
									-
									<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" 
										onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td width="3%">&nbsp;</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30" value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>

								</td>
								<td align="right">制单部门：</td>
								<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>
								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										 readonly>
								</td>
								<td>&nbsp;</td>
								
								
							</tr>
							<tr class="table-back">
								<td align="right">
									调入机构：
								</td>
								<td >
									<input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
									<input name="oname1" type="text" id="oname1" size="30" value="${oname1}" readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									申请机构：
								</td>
								<td >
									<input name="SupplyOrganID" type="hidden" id="SupplyOrganID" value="${SupplyOrganID}">
									<input name="oname2" type="text" id="oname2" size="30" value="${oname2}" readonly><a href="javascript:SelectOrgan3();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">关键字：</td>
								<td><input type="text" name="KeyWord" id="KeyWord" value="${KeyWord}"></td>
								<td align="right" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
							<td width="120" align="center">
								<a href="javascript:ToInput();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle"> 代销申请导入(${wi})</a>
							</td>
							<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
								width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
								<td width="51">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../ditch/listSupplySaleMoveAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="12%">编号</td>
								<td>需求日期</td>
								<td>申请机构</td>
								<td>调入机构</td>
								<td>调入仓库</td>
								<td>调出仓库</td>
								<td>制单机构</td>
								<td>制单人</td>
								<td width="7%">是否复核</td>
								<td width="7%">是否发货</td>
								<td width="7%">是否作废</td>
							</tr>
							<logic:iterate id="a" name="list">
								
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${a.id}');">
									<td class="${a.isblankout==1?'td-blankout':''}">
										${a.id}
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:dateformat value="${a.movedate}" p="yyyy-MM-dd"/>
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${a.supplyorganid}' p='d' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${a.inorganid}' p='d' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${a.inwarehouseid}' p='d'/>
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${a.outwarehouseid}' p='d'/>
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${a.makeorganid}' p='d' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='users' value='${a.makeid}' p='d' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${a.isaudit}' p='f' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${a.isshipment}' p='f' />
									</td>
									<td class="${a.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${a.isblankout}' p='f' />
									</td>
									
								</tr>
							</logic:iterate>
						</table>
						<br />
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:SupplySaleMoveDetail();"><span>渠道代销详情</span></a></li>
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
<input type="hidden" name="IsRatify" id ="IsRatify" value="${IsRatify}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeID" value="${MakeDeptID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="InOrganID" id ="InOrganID" value="${InOrganID}">
<input type="hidden" name="SupplyOrganID" id ="SupplyOrganID" value="${SupplyOrganID}">
</form>
	</body>
</html>
