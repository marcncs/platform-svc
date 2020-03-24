<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
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
	PurchaseBillDetail();
	}
	
	function addNew(){
		popWin("../sales/toAddSaleForecastAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("../sales/toUpdSaleForecastAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseBillDetail(){
		if(checkid!=""){
			document.all.submsg.src="detailSaleForecastAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的采购订单吗？如果删除将永远不能恢复!")){
			popWin2("../sales/delSaleForecastAction.do?ID="+checkid);
		}
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
  
	
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body >
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
								<td width="772">
									渠道管理>>销售预测
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listSaleForecastAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									客户名称：
								</td>
								<td>
									<input name="objsort" type="hidden" id="objsort" value="${objsort }">
									<input name="CID" type="hidden" id="CID" value="${CID}">
									<input name="cname" type="text" id="cname" value="${cname}"><a href="javascript:SelectName();"><img
											src="../images/CN/find.gif" width="18" height="18" align="absmiddle" border="0">
									</a>

								</td>
								<td align="right">
									预测开始日期：
								</td>
								<td>
									<input name="ForeStartDate" type="text" id="ForeStartDate"
										value="${ForeStartDate}" size="12" readonly
										onFocus="javascript:selectDate(this)">
								</td>
								<td align="right">
									预测结束日期：
								</td>
								<td>
									<input name="ForeEndDate" type="text" id="ForeEndDate"
										value="${ForeEndDate}" size="12" readonly
										onFocus="javascript:selectDate(this)">
								</td>
								<td></td>
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
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../sales/listSaleForecastAction.do" />
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
								<td width="70">
									编号
								</td>
								<td>
									对象类型
								</td>
								<td>
									对象名称
								</td>
								<td>
									预测开始日期
								</td>
								<td >
									预测结束日期
								</td>
								<td width="70">
									总金额
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单部门
								</td>
								<td>
									制单人
								</td>
								<td>
									制单日期
								</td>
							</tr>
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.id}');">
									<td>
										${p.id}
									</td>
									<td>
										<windrp:getname key="ObjSort" p="f" value="${p.objsort}"/>
									</td>
									<td>
										${p.cname}
									</td>

									<td>
										<windrp:dateformat value='${p.forestartdate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:dateformat value='${p.foreenddate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:format value="${p.totalsum}"/>
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='dept' value='${p.makedeptid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${p.makeid}' p='d' />
									</td>
									<td>
										<windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
						
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:PurchaseBillDetail();"><span>销售预测详情</span></a></li>
                
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
