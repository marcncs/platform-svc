<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
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
			popWin("../warehouse/toAddStockMoveAction.do",1000,650);
		}
	
		function Update(){
			if(checkid!=""){
				popWin("../warehouse/toUpdStockMoveAction.do?ID="+checkid,1000,650);
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function StockMoveDetail(){
			if(checkid!=""){
				document.all.submsg.src="stockMoveDetailAction.do?ID="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		
		function ToInput(){
		popWin("../warehouse/selectMoveApplyAction.do",1000,650);
		}
		function ExcelInput(){
			popWin("../warehouse/toStockMoveImportAction.do",500,250);
		}
		
		function excput(){
		search.target="";
		search.action="../warehouse/excPutStockMoveAction.do";
		search.submit();
		search.action="listStockMoveAction.do";
	}
	function print(){
		excputform.target="_blank";
		excputform.action="../warehouse/printListStockMoveAction.do";
		excputform.submit();
	}
		
		function Del(){
			if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+" 的转仓单吗？如果删除将永远不能恢复!")){
				popWin2("../warehouse/delStockMoveAction.do?ID="+checkid);
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
				document.search.InOrganID.value=p.id;
				document.search.oname.value=p.organname;
				//document.search.InWarehouseID.value=p.wid;
				//document.search.wname.value=p.wname;
		}
			
		function SelectOrgan2(){
			var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.MakeOrganID.value=p.id;
				document.search.outname.value=p.organname;
				//document.search.OutWarehouseID.value=p.wid;
				//document.search.mname.value=p.wname;
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
							<td width="772">
								${menusTrace }
							</td>
						</tr>
					</table>
					<form name="search" method="post" action="listStockMoveAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="12%" align="right">
								转入机构：
							</td>
							<td width="23%">
								<input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
								<input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img 
								src="../images/CN/find.gif" width="18" height="18" 
								border="0" align="absmiddle"></a>
							</td>
							<td align="right">
								转入仓库：
							</td>
							<td>
								<input type="hidden" name="InWarehouseID" id="InWarehouseID" value="${InWarehouseID}">
								<input type="text" name="wname" id="wname" 
								onClick="selectDUW(this,'InWarehouseID',$F('InOrganID'),'w')"
								 value="${wname}" readonly>

							</td>
							<td align="right">是否签收：</td>
							<td>
								 <windrp:select key="YesOrNo" name="IsComplete" p="y|f" value="${IsComplete}" />
							</td>
							<td></td>
						</tr>

						<tr class="table-back">
							<td align="right">
								转出机构：
						  </td>
							<td>
						      <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
                                <input name="outname" type="text" id="outname" size="30" value="${outname}" 
								readonly><a href="javascript:SelectOrgan2();"><img 
								src="../images/CN/find.gif" width="18" height="18" 
								border="0" align="absmiddle"></a></td>
							<td width="9%" align="right">转出仓库：</td>
							<td width="23%">
								<input type="hidden" name="OutWarehouseID" id="OutWarehouseID" value="${OutWarehouseID}">
								<input type="text" name="mname" id="mname" 
								onClick="selectDUW(this,'OutWarehouseID',$F('MakeOrganID'),'w')"
								 value="${mname}" readonly>
							</td>
							<td width="9%" align="right">是否发货：</td>
							<td width="24%"><windrp:select key="YesOrNo" name="IsShipment" p="y|f" value="${IsShipment}" />
								
							</td>
							<td></td>
						</tr>
						<tr class="table-back">
							<td align="right">是否复核：</td>
							<td><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
							<td align="right">
								是否无单：
							</td>
							<td>
								<windrp:select key="YesOrNo" name="isNoBill" p="y|f" value="${isNoBill}" />
							</td>
							<td width="9%" align="right">
								制单人：
							</td>
							<td width="23%">
								<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
								<input type="text" name="uname" id="uname" 
								onClick="selectDUW(this,'MakeID',$F(MakeOrganID),'ou')" 
								value="${uname}" readonly>

								
							</td>
							<td width="9%" align="right">
							</td>
							<td width="24%">
							</td>
							<td></td>
						</tr>
						<tr class="table-back">
							<td width="9%" align="right">
								关键字：
							</td>
							<td width="24%">
								<input type="text" name="KeyWord" value="${KeyWord}">
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询"></td>
						</tr>
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="title-func-back">
					   	<%-- <ws:hasAuth operationName="/warehouse/toAddStockMoveAction.do">
							<td width="50">
							<a href="javascript:addNew();"><img
									src="../images/CN/addnew.gif" width="16" height="16" border="0"
									align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
						</ws:hasAuth>
						<ws:hasAuth operationName="/warehouse/toUpdStockMoveAction.do">
							<td width="50">
								<a href="javascript:Update();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
						</ws:hasAuth> --%>
						<ws:hasAuth operationName="/warehouse/delStockMoveAction.do">
							<td width="50">
								<a href="javascript:Del()"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
						</ws:hasAuth>
						<ws:hasAuth operationName="/warehouse/excPutStockMoveAction.do">
							<td width="50">
								<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
							    </td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
						</ws:hasAuth>
						<ws:hasAuth operationName="/warehouse/printListStockMoveAction.do">
							<td width="50">
								<a href="javascript:print();"><img
										src="../images/CN/print.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;打印</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
						</ws:hasAuth>
							<td class="SeparatePage">
								<pages:pager action="../warehouse/listStockMoveAction.do" />
							</td>
						</tr>
					</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="140px">
								编号
							</td>
							<td width="100px">
								转仓日期
							</td>
							<td width="120px">
								转出机构
							</td>
							<td style="width: auto;">
								转出仓库
							</td>
							<td width="120px">
								转入机构
							</td>
							<td style="width: auto;">
								转入仓库
							</td>
							<td width="90px">
								制单人
							</td>
							<td width="30px">
								是否复核
							</td>
							<td width="30px">
								是否发货
							</td>
							<td width="30px">
								是否签收
							</td>
						</tr>
						<logic:iterate id="sa" name="als">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');">
								<td class="${sa.isblankout==1?'td-blankout':''}">
									${sa.id}
								</td>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd'/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
<%--									<windrp:getname key='organ' value='${sa.outorganid}' p='d'/>--%>
									${sa.outoname}
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d'/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
<%--									<windrp:getname key='organ' value='${sa.inorganid}' p='d'/>--%>
									${sa.inoname} 
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key='warehouse' value='${sa.inwarehouseid}' p='d'/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key='users' value='${sa.makeid}' p='d'/> 
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key='YesOrNo' value='${sa.isaudit}' p='f'/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									
									<windrp:getname key='YesOrNo' value='${sa.isshipment}' p='f'/>
								</td>
								<td class="${sa.isblankout==1?'td-blankout':''}">
									<windrp:getname key='YesOrNo' value='${sa.iscomplete}' p='f'/>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
					<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:StockMoveDetail();"><span>${menu}详情</span></a></li>
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
<input type="hidden" name="InOrganID" id ="InOrganID" value="${InOrganID}">
<input type="hidden" name="OutWarehouseID" id ="OutWarehouseID" value="${OutWarehouseID}">
<input type="hidden" name="InWarehouseID" id ="InWarehouseID" value="${InWarehouseID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="IsShipment" id ="IsShipment" value="${IsShipment}">
<input type="hidden" name="IsComplete" id ="IsComplete" value="${IsComplete}">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
</form>
	</body>
</html>
