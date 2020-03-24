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
				popWin("toAddEquipAction.do",800,600);
			}
			
			function Update(){
				if(checkid!=""){
				popWin("toUpdEquipAction.do?ID="+checkid,800,600);
				}else{
				alert("请选择你要操作的记录!");
				}
			}
				
				
		function excput(){
			search.target="";
			search.action="../equip/excPutEquipAction.do";
			search.submit();
		}
		function oncheck(){
			search.target="";
			search.action="../equip/listEquipAction.do";
			search.submit();
		}
		function print(){
			search.target="_blank";
			search.action="../equip/printListEquipAction.do";
			search.submit();
		}
				
				
	
		
			function Del(){
				if(checkid!=""){
				if(window.confirm("您确认要删除编号为："+checkid+"的记录吗？如果删除将永远不能恢复!")){
					window.open("../equip/delEquipAction.do?SOID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
					}
				}else{
				alert("请选择你要操作的记录!");
				}
			}
			
			function OrderDetail(){
				if(checkid!=""){
				document.all.submsg.src="../equip/equipDetailAction.do?ID="+checkid;
				}else{
				alert("请选择你要操作的记录!");
				}
			}
			
			function SelectCustomer(){
			var c=showModalDialog("../sales/toSelectSaleOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( c == undefined ){
				return;
			}
			document.search.CID.value=c.cid;
			document.search.cname.value=c.cname;
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
			this.onload = function abc(){
				document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
			}
		</script>

	</head>

	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 配送中心&gt;&gt;配送单</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="listEquipAction.do" onSubmit="return oncheck();">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td align="right">
								制单机构：							</td>
							<td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
						    <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>							</td>
							<td align="right">
								制单部门：							</td>
							<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname"
									onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
									value="${deptname}" readonly>							</td>
							<td align="right">
								制单人：							</td>
							<td>
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
								<input type="text" name="uname" id="uname"
									onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
									value="${uname}" readonly>							</td>
							<td></td>
						</tr>
						<tr class="table-back">
							<td width="9%" align="right">
								对象类型：							</td>
							<td width="20%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/></td>
							<td align="right">选择对象：</td>
							<td><input name="CID" type="hidden" id="CID" value="${CID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" border="0" align="absmiddle"></a>	</td>
							<td align="right">配送日期：</td>
							<td><input type="text" name="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly="readonly">
-
  <input type="text" name="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly="readonly"></td>
						  <td class="SeparatePage">&nbsp;</td>
						</tr>
						<tr class="table-back">
							<td width="9%" align="right">关键字：</td>
							<td width="20%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
							 <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
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
							<td class="SeparatePage">
								<pages:pager action="../equip/listEquipAction.do" />
							</td>
						</tr>
					</table>
	  </div>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="13%">
								编号							</td>
							<td width="11%">
								对象类型							</td>
							<td width="19%">
								对象名称							</td>
							<td width="11%">
								联系人							</td>
							<td width="12%">
								配送日期							</td>
							<!--<td width="8%">
								代收金额
							</td>-->
							<td width="9%">
								件数							</td>
							<td width="9%">
								发运方式							</td>
							<td width="16%">
								司机							</td>
							<!--td width="8%" >订单状态</td-->
						</tr>
						<logic:iterate id="s" name="also">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
								<td>
									${s.id}
								</td>
								<td>
									<windrp:getname key='ObjectSort' value='${s.objectsort}' p='f'/>
								</td>
								<td>
									${s.cname}
									<!--<a href="../sales/listMemberAction.do?CID=${s.cid}"><img
											src="../images/CN/go.gif" width="10" height="10" border="0">
									</a>-->
								</td>
								<td>
									${s.clinkman}
								</td>
								<td><windrp:dateformat value='${s.equipdate}' p='yyyy-MM-dd'/>
								</td>
								<!--<td>
									${s.eratotalsum}
								</td>-->
								<td align="right"><windrp:format value='${s.piece}' p="###,##0.00"/>
								</td>
								<td><windrp:getname key='TransportMode' value='${s.transportmode}' p='d'/>
								</td>
								<td><windrp:getname key='users' value='${s.motorman}' p='d'/> 
								</td>

							</tr>
						</logic:iterate>
					</table>
					<br>
                    <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:OrderDetail();"><span>配送单详情</span></a></li>
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
