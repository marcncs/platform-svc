<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
	var checkid=0;
	var submenu=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 submenu = getCookie("CookieProvide");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":Linkman(); break;
		case "2":ProviderProduct(); break;
		case "3":PurchaseInquire(); break;
		case "4":PurchaseRecord(); break;
		case "5":Document(); break;
		case "6":PurchaseInvoice(); break;
		default:ProviderDetail();
	 }
	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
	
	function addNew(){
	popWin("../purchase/toAddProviderAction.do",900,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdProviderAction.do?PID="+checkid,900,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProviderDetail(){
	setCookie("CookieProvide","0");
		if(checkid!=""){
			document.all.submsg.src="../purchase/providerDetailAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Linkman(){
	setCookie("CookieProvide","1");
		if(checkid!=""){
			document.all.submsg.src="../purchase/listPlinkmanAction.do?pid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProviderProduct(){
	setCookie("CookieProvide","2");
		if(checkid!=""){
			document.all.submsg.src="listProviderProductAction.do?pid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function PurchaseRecord(){
	setCookie("CookieProvide","4");
		if(checkid!=""){
			document.all.submsg.src="listPurchaseBillAction.do?pid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseInquire(){
	setCookie("CookieProvide","3");
		if(checkid!=""){
			document.all.submsg.src="listPurchaseInquireAction.do?pid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Document(){
	setCookie("CookieProvide","5");
		if(checkid!=""){
			document.all.submsg.src="listDocumentAction.do?Cid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseInvoice(){
		setCookie("CookieProvide","6");
		if(checkid!=""){
			document.all.submsg.src="listPurchaseInvoiceAction.do?pid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function Import(){
		popWin("../purchase/toImportProviderAction.do",500,300);
	}
	
	function OutPut(){
		search.target="_blank";
		search.action="../purchase/excPutProviderAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listProviderAction.do";
	}
	
	function print(){
		search.target="_blank";
		search.action="../purchase/printProviderAction.do";
		search.submit();
		search.target="";
		search.action="../purchase/listProviderAction.do";
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 的供应商吗？如果删除将永远不能恢复!")){
			popWin("../purchase/delProviderAction.do?PID="+checkid,500,250);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
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
								<td width="772">
									产品采购&gt;&gt;供应商资料
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listProviderAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
							<td width="8%" align="right">
									制单机构：
								</td>
								<td width="31%">
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}"><input name="oname" type="text" id="oname" size="30" value="${oname}" 
              							readonly><a href="javascript:SelectOrgan();"><img 
              							src="../images/CN/find.gif" width="18" height="18" 
              							border="0" align="absmiddle"></a>
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
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly>
								</td>
								<td >&nbsp;
									
								</td>
							</tr>
							<tr class="table-back">
								<td width="10%" align="right">
									供应商类型：
								</td>
								<td width="18%">
									<windrp:select key="Genre" name="Genre" value="${Genre}" p="y|d" />
								</td>
								<td width="8%" align="right">
									ABC分类：
								</td>
								<td width="25%">
									<windrp:select key="AbcSort" name="AbcSort" value="${AbcSort}" p="y|f" />
								</td>
								
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">
								</td>
								
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
								</td>
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
									<a href="javascript:Import()"><img 
									src="../images/CN/import.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导入
									</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:OutPut();"><img 
									src="../images/CN/outputExcel.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导出
									</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>								
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../purchase/listProviderAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							
							<tr align="center" class="title-top">
								<td width="13%">
									编号								</td>
								<td width="27%">
									供应商名								</td>
								<td width="12%">
									电话								</td>
								<td width="14%">
									供应商类型								</td>
								<td width="8%">
									ABC分类
								</td>
								<td width="13%">
									供应商行业
								</td>
								<td width="13%">
									制单机构								</td>
								<!--<td width="8%">是否可用</td> -->
							</tr>
							<logic:iterate id="p" name="alls">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${p.pid}');">
									<td>
										${p.pid}
									</td>
									<td>
										${p.pname}
									</td>
									<td>
										${p.tel}
									</td>
									<td>
										<windrp:getname key="Genre" value="${p.genre}" p="d" />
									</td>
									<td>
										<windrp:getname key="AbcSort" value="${p.abcsort}" p="f" />
									</td>
									<td>
										<windrp:getname key="Vocation" value="${p.vocation}" p="d" />
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
								</tr>
							</logic:iterate>
							
						</table>
						</form>
						<br />
                        <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:ProviderDetail();"><span>供应商详情</span></a></li>
                                <li><a href="javascript:Linkman();"><span>联系人</span></a></li>
                                <li><a href="javascript:ProviderProduct();"><span>相关产品</span></a></li>
                                <li><a href="javascript:PurchaseInquire();"><span>询价记录</span></a></li>
                                <li><a href="javascript:PurchaseRecord();"><span>采购订单</span></a></li>
                                <li><a href="javascript:Document();"><span>相关文档</span></a></li>
                                <li><a href="javascript:PurchaseInvoice();"><span>采购发票</span></a></li>
                              </ul>
                            </div>
                            <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
                        </div>								
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="excPutProviderAction.do">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID }">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID }">
<input type="hidden" name="Genre" id ="Genre" value="${Genre }">
<input type="hidden" name="AbcSort" id ="AbcSort" value="${AbcSort }">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord }">
</form>
	</body>
</html>
