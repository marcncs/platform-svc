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
	
	function addNew(){
		popWin("../ditch/toAddOrganWithdrawAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("../ditch/toUpdOrganWithdrawAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Inquire(){
		if(checkid!=""){
				location.href("../ditch/detailOrganWithdrawAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function OrganWithdrawDetail(){
		if(checkid!=""){
			document.all.submsg.src="../ditch/detailOrganWithdrawAction.do?ID="+checkid+"&isshow=${isshow}";
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的渠道退货单吗？如果删除将永远不能恢复!")){
				popWin("../ditch/delOrganWithdrawAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function excput(){
		search.target="";
		search.action="../ditch/excPutOrganWithdrawAction.do?isshow=${isshow}";
		search.submit();
		search.action="listOrganWithdrawAction.do";
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../ditch/printListOrganWithdrawAction.do?isshow=${isshow}";
		excputform.submit();
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			//clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	}
	function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.POrganID.value=p.id;
			document.search.POrganName.value=p.organname;
			
	}
	function SelectOrgan3(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.ReceiveOrganid.value=p.id;
			document.search.receivename.value=p.organname;
			
	}
	
	function oncheck(){
		search.action="listOrganWithdrawAction.do";
		search.submit();
	}

	this.onload = function abc(){
		//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
									${menusTrace}
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listOrganWithdrawAction.do" onSubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
								   	退货机构：
								</td>
								<td >
									<input name="POrganID" type="hidden" id="POrganID" value="${POrganID}">
									<input name="POrganName" type="text" id="POrganName" size="30" value="${POrganName}" 
									 readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
								</td>
								<td align="right">
									调入机构：
								</td>
								<td>
									<input name="ReceiveOrganid" type="hidden" id="ReceiveOrganid" value="${ReceiveOrganid}">
									<input name="receivename" type="text" id="receivename" size="30" value="${receivename}"
									readonly><a href="javascript:SelectOrgan3();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									是否复核：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
								</td>
								 <td align="right">
									是否签收：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsComplete" p="y|f" value="${IsComplete}" />
								</td >
								<td align="right">
									是否无单：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="isNoBill" p="y|f" value="${isNoBill}" />
								</td>
							</tr>
							<tr class="table-back">
								 <td align="right">
									制单日期：
							     </td>
								 <td>
									<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									 	onFocus="javascript:selectDate(this)" readonly>
										-
									<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								 </td>
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
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										 readonly>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">关键字：</td>
                                <td><input type="text" name="KeyWord" maxlength="60" value="${KeyWord}"></td>
                                <td></td>
                                <td></td>
                                <td></td>
								<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/ditch/toAddOrganWithdrawAction.do">
								<td width="50">
									<a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/ditch/toUpdOrganWithdrawAction.do">
								<td width="50">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/ditch/delOrganWithdrawAction.do">
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/ditch/excPutOrganWithdrawAction.do">
								<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
								</ws:hasAuth>
								<ws:hasAuth operationName="/ditch/printListOrganWithdrawAction.do">
								<td width="51">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
						           <pages:pager action="../ditch/listOrganWithdrawAction.do" />
								</td>
							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="12%">编号</td>
								<td width="12%">退货机构</td>
								<td width="12%">退货仓库</td>
								<td width="12%">调入机构</td>
								<td width="12%">调入仓库</td>
								<td width="12%">制单机构</td>
								<td width="8%">制单人</td>
								<td width="8%">制单日期</td>
								<td width="3%">是否复核</td>
								<td width="3%">是否签收</td>
							</tr>
							<logic:iterate id="o" name="list">
								
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${o.id}');">
									<td class="${o.isblankout==1?'td-blankout':''}">
										${o.id}
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										${o.porganname}
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${o.warehouseid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
<%--										<windrp:getname key='organ' value='${o.receiveorganid}' p='d' />--%>
										${o.inoname}  
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='warehouse' value='${o.inwarehouseid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${o.makeorganid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='users' value='${o.makeid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:dateformat value="${o.makedate}" p="yyyy-MM-dd"/>
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${o.isaudit}' p='f' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${o.iscomplete}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
											<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:OrganWithdrawDetail();"><span>渠道退货详情</span></a></li>
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
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="POrganID" id ="POrganID" value="${POrganID}">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="IsRatify" id ="IsRatify" value="${IsRatify}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="IsAffirm" id ="IsAffirm" value="${IsAffirm}">
<input type="hidden" name="IsComplete" id ="MakeOrganID" value="${IsComplete}">
</form>
	</body>
</html>
