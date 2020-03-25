<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
				SampleBillDetail();
			}
		
			function addNew(){
				popWin("toAddSampleBillAction.do?objsort="+${objsort},1000,600);
			}
		
			function Update(){
				if(checkid!=""){
					popWin("toUpdSampleBillAction.do?ID="+checkid,1000,600);
				}else{
					alert("请选择你要操作的记录!");
				}
			}
			
		
			
		
			function Del(){
				if(checkid!=""){
				if(window.confirm("您确认要删除编号为："+ checkid +" 的样品记录吗？如果删除将永远不能恢复!")){
					popWin2("delSampleBillAction.do?SBID="+checkid);
					}
				}else{
				alert("请选择你要操作的记录!");
				}
			}
			
			function SampleBillDetail(){
				if(checkid!=""){
				document.all.submsg.src="sampleBillDetailAction.do?ID="+checkid;
				}else{
				alert("请选择你要操作的记录!");
				}
			}
			
		function SelectName(){
			var objsort = document.search.objsort;
			if(objsort.value ==1){
				SelectCustomer();
			}else{
				SelectOrgan2();
			}
		}
	
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( c == undefined ){
				return;
			}
			document.search.CID.value=c.cid;
			document.search.cname.value=c.cname;
		}
		
		function SelectOrgan2(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.search.CID.value=o.id;
			document.search.cname.value=o.organname;
		} 
		//this.onload = function abc(){
		//	document.getElementById("abc").style.height = (document.body.offsetHeight - document.getElementById("div1").offsetHeight)+"px" ;
		//}
		
		function SelectOrgan(){
			var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.MakeOrganID.value=p.id;
				document.search.oname.value=p.organname;
				clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
		}

	</script>

	</head>

	<body>

<div>
				<div id="div1">
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								<c:if test="${objsort==1 }">
									会员管理>>样品
								</c:if>
									<c:if test="${objsort==0 }">
									渠道管理>>样品
								</c:if>
							</td>
						</tr>
					</table>
<form name="search" method="post" action="listSampleBillAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="10%" height="20" align="right">
								客户名称：
							</td>
							<td width="22%">
							<input type="hidden" id="objsort" name="objsort" value="${objsort }">
								<input name="CID" type="hidden" id="CID" value="${CID}">
									<input name="cname" type="text" id="cname" value="${cname}" readonly="readonly" ><a href="javascript:SelectName();"><img
										src="../images/CN/find.gif" width="18" height="18" border="0"
										align="absmiddle"> </a>
							</td>
							<td width="9%" align="right">
								是否复核：
							</td>
							<td width="22%">
								<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />
							</td>
							<td width="8%" align="right">
								制单日期：
							</td>
							<td width="29%">
								<input type="text" name="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly="readonly">
								-
								<input type="text" name="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly="readonly">
							</td>
						</tr>
						<tr class="table-back">
							<td align="right">
								制单机构：
							</td>
							<td>
								<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
<input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

							</td>
							<td align="right">
								制单部门：
							</td>
							<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname"
									onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
									value="${deptname}" readonly>
							</td>
							<td align="right">
								制单人：
							</td>
							<td>
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">

									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
										value="${uname}" readonly>
							</td>
							<td></td>
						</tr>
						<tr class="table-back">

							<td align="right">
								关键字：
							</td>
							<td>
								<input type="text" size="18" name="KeysContent" value="${KeysContent}">

							</td>
							<td align="right">&nbsp;
								
							</td>
							<td align="right">&nbsp;
								
							</td>
							<td align="right">&nbsp;
								
							</td>
							<td class="SeparatePage">
								<input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
							</td>
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
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
							<td class="SeparatePage">
								<pages:pager action="../sales/listSampleBillAction.do" />
							</td>
						</tr>
					</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="12%" height="25">
								订单编号
							</td>
							<td width="22%">
								客户名称
							</td>
							<td width="10%">
								联系人
							</td>
							<td width="15%">
								联系电话
							</td>
							<td width="12%">
								发货日期
							</td>
							<td width="8%">
								是否复核
							</td>
							<td width="8%">
								制单人
							</td>
							<td width="8%">
								制单日期
							</td>
						</tr>
						<logic:iterate id="s" name="also">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
								<td height="20">
									${s.id}
								</td>
								<td>
									${s.cname}
								</td>
								<td>
									${s.linkman}
								</td>
								<td>
									${s.tel}
								</td>
								<td>
									
									<windrp:dateformat value="${s.shipmentdate}" p="yyyy-MM-dd"/>
								</td>
								<td>
									<windrp:getname key="YesOrNo" p="f" value="${s.isaudit}"/>
								</td>
								<td>
									<windrp:getname key="users" p="d" value="${s.makeid}"/>
								</td>
								<td>
									<windrp:dateformat value="${s.makedate}" p="yyyy-MM-dd"/>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
					
<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:SampleBillDetail();"><span>样品单详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
					
					</div>
				</div>
	</body>
</html>
