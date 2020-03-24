<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script language="JavaScript">
 
    var rids="";
 
	function Affirm(){		
		var flag=false;		 
		 var bids="";
         var iscid="";
		 var bid = document.all("bid");
		 var objcid=document.all("cid");
		if(bid.length>1){
			for(var i=0;i<bid.length;i++){
				if(bid[i].checked){					 
				 bids="'"+bid[i].value+"',"+bids;
				 flag=true;
				 var cid = objcid[i].value;
				 if ( iscid == "" ){
				 	iscid=cid;
				 }else{
				 	if ( iscid != cid ){
						alert("只能选择同一客户的送货单!");
						return;
					}
				 }				 
				}
			}
		}else{
		     iscid=objcid.value;			 
			 bids="'"+bid.value+"'";
			 flag=true;//只要选中一个就设为true
		}
		
		if(flag){
			window.open("../warehouse/toAddEquipAction.do?bids="+bids,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		
		}else{
			alert("请选择你要操作的记录!");
		}			
	}



var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function addNew(){
 
	window.open("toAddSaleShipmentBillAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function ToInput(){
	window.open("toSaleOrderControlAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdSaleShipmentAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="shipmentBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
			window.open("../warehouse/delShipmentBillAction.do?SBID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function ShipmentBill(){
		if(checkid!=""){
			window.open("../warehouse/shipmentBillAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
function SelectCustomer(){
		var os = document.search.ObjectSort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( o==undefined){return;}
		document.search.CID.value=o.id;
		document.search.cname.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( m==undefined){return;}
		document.search.CID.value=m.cid;
		document.search.cname.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.CID.value=p.pid;
		document.search.cname.value=p.pname;
		}
	}
function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}
function Check(){
		if(document.all("checkall").checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		var pid = document.all("bid");
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=true;
			}
		}else{
			pid.checked=true;
		}		
	}
	function uncheckAll(){
		var pid = document.all("bid");
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=false;
			}
		}else{
			pid.checked=false;
		}		
	}
	

	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}


	function excput(){
		search.target="";
		search.action="../warehouse/excPutShipmentBillAction.do";
		search.submit();
	}
	function oncheck(){
		search.target="";
		search.action="../warehouse/listShipmentBillAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListShipmentBillAction.do";
		search.submit();
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
			<tr>
				<td>
				<div id="div1">
					<table width="100%"  border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								配送中心&gt;&gt;送货清单
							</td>
						</tr>
					</table>

<form name="search" method="post"
							action="listShipmentBillAction.do" onSubmit="return oncheck();">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="10%" align="right">
								对象类型：							</td>
							<td width="26%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/>							</td>
							<td width="12%" align="right">
								选择对象：							</td>
							<td width="21%">
								<input name="CID" type="hidden" id="CID" value="${CID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>							</td>
							<td width="9%" align="right">是否转配送：</td>
							<td width="22%"><windrp:select key="YesOrNo" name="IsTrans" p="y|f" value="${IsTrans}"/></td>
						</tr>
						<tr class="table-back">
							<td width="10%" align="right">需求日期：</td>
							<td width="26%"><input name="BeginDate" type="text" value="${BeginDate}"
							 onFocus="javascript:selectDate(this)" size="12" readonly>
							-
							  <input name="EndDate" type="text" value="${EndDate}"
							   onFocus="javascript:selectDate(this)" size="12" readonly></td>
							<td width="12%" align="right">
								是否完成配送：							</td>
							<td width="21%">
								<windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" />							</td>
							<td width="9%" align="right">
								是否作废：							</td>
							<td width="22%">
								<windrp:select key="YesOrNo" name="IsBlankOut" p="y|f" value="${IsBlankOut}" />							</td>
								
						</tr>
						<tr class="table-back">
							<td align="right">
								制单机构：							</td>
							<td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>							</td>
							<td align="right">
								制单部门：							</td>
							<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>							</td>
							<td align="right">
								制单用户：							</td>
							<td>
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
								<input type="text" name="uname" id="uname"
									onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
									value="${uname}" readonly>							</td>
						</tr>
						<tr class="table-back">
							<td align="right">关键字：</td>
							<td><input type="text" name="KeyWord" value="${KeyWord}"></td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
							<td align="right">&nbsp;</td>
							<td class="SeparatePage">
							  <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
							</td>
						
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="80"><a href="javascript:Affirm();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;转配送单</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
							<td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>					
							<td class="SeparatePage"><pages:pager action="../warehouse/listShipmentBillAction.do" /></td>
						</tr>
					</table>
				  </div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="2%" class="sorttable_nosort">
								<input type="checkbox" name="checkall" id="checkall" onClick="Check();">
							</td>
							<td width="16%">
								编号
							</td>
							<td width="15%">
								对象名称
							</td>
							<td width="11%">
								收货人
							</td>
							<td width="12%">
								联系电话
							</td>
							<td width="8%">
								发运方式
							</td>
							<td width="10%">
								需求日期
							</td>
							<td width="8%">
								是否转配送
							</td>
							<td width="9%">
								是否完成配送
							</td>
							<td width="9%">
								是否作废
							</td>
						</tr>
						<logic:iterate id="s" name="alsb">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
								<td>
									<input type="checkbox" name="bid" value="${s.id}">
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.id}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									<input type="hidden" name="cid" id="cid" value="${s.cid}">
									${s.cname}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.linkman}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.tel}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='TransportMode' value='${s.transportmode}' p='d'/>
								</td>
								<td class="${s.isblankout==1?'text2-red':''}"><windrp:dateformat value='${s.requiredate}' p='yyyy-MM-dd'/></td>
								<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.istrans}' p='f'/></td>
								<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
								<td class="${s.isblankout==1?'text2-red':''}"><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>
							</tr>
						</logic:iterate>
					</table>
					<br>
					<div style="width:100%">
			        <div id="tabs1">
			          <ul>
			            <li><a href="javascript:Detail();"><span>送货清单详情</span></a></li>
			          </ul>
			        </div>
			        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
			      </div>	
				</div>
				</td>
			</tr>
		</table>
	</body>
</html>
