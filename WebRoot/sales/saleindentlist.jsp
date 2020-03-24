<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
		var checkid="";
		function CheckedObj(obj,objid){
		
			for(i=0; i<obj.parentNode.childNodes.length; i++)
			{
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
			}
			 
			obj.className="event";
			checkid=objid;
			OrderDetail();
		}
	
		function addNew(){
			popWin("toAddSaleIndentAction.do",1000,650);
		}
	
		function Update(){
			if(checkid!=""){
			popWin("toUpdSaleIndentAction.do?ID="+checkid,1000,650);
			}else{
				alert("请选择你要操作的记录!");
			}
		}

		function Refer(){
			if(checkid!=""){
				popWin2("../sales/toReferSaleIndentAction.do?SaleID="+checkid);
			}else{
			alert("请选择你要操作的记录!");
			}
		}

		function excput(){
			search.target="";
			search.action="../sales/excPutSaleIndentAction.do";
			search.submit();
		}function oncheck(){
			search.target="";
			search.action="../sales/listSaleIndentAction.do";
			search.submit();
		}
		function print(){
			search.target="_blank";
			search.action="../sales/printListSaleIndentAction.do";
			search.submit();
		}
		
	
		function Complete(){
			if(checkid!=""){
				popWin2("../sales/completeSaleIndentAction.do?slid="+checkid);
			}else{
			alert("请选择你要操作的记录!");
			}
		}


		function Del(){
			if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+"的零售订单吗？如果删除将永远不能恢复!")){
				popWin2("../sales/delSaleIndentAction.do?SOID="+checkid);
				}
			}else{
			alert("请选择你要操作的记录!");
			}
		}
	
		function OrderDetail(){
			if(checkid!=""){
				document.all.submsg.src="../sales/saleIndentDetailAction.do?ID="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
			
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(c==undefined){return;}
			document.search.CID.value=c.cid;
			document.search.cname.value=c.cname;
		}
			
		function SelectOrgan(){
			var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","dname","MakeID","uname");
		}
			
		this.onload =function onLoadDivHeight(){
			document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
		}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									零售管理&gt;&gt;零售预订单
								</td>
							</tr>
						</table>
<form name="search" method="post"
								action="listSaleIndentAction.do" onSubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="11%" align="right">
									会员名称：</td>
								<td width="27%">
									<input name="CID" type="hidden" id="CID" value="${CID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img
											align="absmiddle" src="../images/CN/find.gif" width="18"
											height="18" border="0"> </a></td>
								<td width="10%" align="right">
									是否复核：</td>
								<td width="23%">
									<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
								<td width="9%" align="right">
									是否结案：</td>
								<td width="17%">
									<windrp:select key="YesOrNo" name="IsEndcase"  p="y|f" value="${IsEndcase}" /></td>
							    <td width="3%">&nbsp;</td>
							</tr>
							<tr class="table-back">
								<td align="right">制单机构：</td>
								<td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a> </td>
								<td align="right">制单部门：</td>
								<td><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
									<input type="text" name="dname" id="dname"
										onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
										value="${dname}" readonly></td>
								<td align="right">制单人： </td>
							  <td>
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
                                <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly></td>
							  <td>&nbsp;</td>
							</tr>
							<tr class="table-back">
							  <td align="right">制单日期：</td>
							  <td>
							    <input name="BeginDate" type="text" value="${BeginDate}"
										onFocus="javascript:selectDate(this)" size="12" readonly>
-
<input name="EndDate" type="text" value="${EndDate}"
										onFocus="javascript:selectDate(this)" size="12" readonly>							 </td>
							  <td align="right">&nbsp;</td>
							  <td>&nbsp;</td>
							  <td align="right">&nbsp;</td>
							  <td>&nbsp;</td>
							  <td><span class="SeparatePage">
							    <input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
							  </span></td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="#" onClick="javascript:addNew();"><img
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
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
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
								<td style="text-align: right;">
									<pages:pager action="../sales/listSaleIndentAction.do" />
								</td>

							</tr>
						</table>
					</div>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="10%">
									订单编号
								</td>
								<td width="22%">
									会员名称
								</td>
								<td width="10%">
									对方订单号
								</td>
								<td width="15%">
									交货日期
								</td>
								<td width="8%">
									总金额
								</td>
								<td width="8%">
									是否复核
								</td>
								<td width="8%">
									是否结案
								</td>
								<td width="8%">
									制单人
								</td>
							
							</tr>
							<logic:iterate id="s" name="also">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
									<td>
										${s.id}
									</td>
									<td>
										${s.cname}
										<a href="../sales/listMemberAction.do?CID=${s.cid}"><img
												src="../images/CN/go.gif" width="10" height="10" border="0">
										</a>
									</td>
									<td>
										${s.customerbillid}
									</td>
									<td>
										${s.consignmentdate}
									</td>
									<td align="right">
										<windrp:format value="${s.totalsum}" p="###,##0.00"/>
									</td>
									<td>
										<windrp:getname key="YesOrNo" p="f" value="${s.isaudit}" />
									</td>
									<td>
										<windrp:getname key="YesOrNo" p="f" value="${s.isendcase}" />
									</td>
									<td>
										<windrp:getname key="users" p="d" value="${s.makeid}" />
									</td>

								</tr>
							</logic:iterate>
						</table>
						<br>
						
					<div style="width:100%">
					      	<div id="tabs1">
					            <ul>
					              <li><a href="javascript:OrderDetail();"><span>预订单详情</span></a></li>
					            </ul>
					          </div>
					          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
					
					frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
					      </div>
						
						
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
