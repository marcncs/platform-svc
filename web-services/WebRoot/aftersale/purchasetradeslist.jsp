<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
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
		var checkid="";
		function CheckedObj(obj,objid){
	
			for(i=0; i<obj.parentNode.childNodes.length; i++)
			{
				obj.parentNode.childNodes[i].className="table-back-colorbar";
			}
			 
			obj.className="event";
			checkid=objid;
			Detail();
		}
		
	function excput(){
		search.target="";
		search.action="../aftersale/excPutPurchaseTradesAction.do";
		search.submit();
		search.target="";
		search.action="../aftersale/listPurchaseTradesAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../aftersale/printPurchaseTradesAction.do";
		search.submit();
		search.target="";
		search.action="../aftersale/listPurchaseTradesAction.do";
		
	}
	function DownTxt(){
		excputform.action="txtPutPurchaseTradesAction.do";
		excputform.submit();
	}
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=13", 500, 250);
	}
		function addNew(){
			popWin("toAddPurchaseTradesAction.do",1000,650);
		}

		function Update(){
			if(checkid!=""){
				popWin("toUpdPurchaseTradesAction.do?ID="+checkid,1000,650);
			}else{
				alert("请选择你要操作的记录!");
			}
		}


		function Del(){
			if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+"的采购换货记录吗？如果删除将永远不能恢复!")){
					popWin2("../aftersale/delPurchaseTradesAction.do?id="+checkid);
				}
			}else{
			alert("请选择你要操作的记录!");
			}
		}
	
		function Detail(){
			if(checkid!=""){
			document.all.submsg.src="../aftersale/purchaseTradesDetailAction.do?ID="+checkid;
			}else{
			alert("请选择你要操作的记录!");
			}
		}
	
		function SelectCustomer(){
			var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){
				return;
			}
			document.search.ProvideID.value=p.pid;
			document.search.ProvideName.value=p.pname;
		}
		this.onload = function abc(){
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
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td >
									产品采购>>采购换货
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="../aftersale/listPurchaseTradesAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="8%" align="right">
									供应商：
								</td>
								<td width="22%">
									<input name="ProvideID" type="hidden" id="ProvideID" value="${ProvideID}">
									<input name="ProvideName" type="text" id="ProvideName" 
									value="${ProvideName}"><a href="javascript:SelectCustomer();"><img
											src="../images/CN/find.gif" width="18" height="18"
											align="absmiddle" border="0">

									</a>
								</td>
								<td width="8%" align="right">
									是否复核：
								</td>
								<td width="23%">
									<windrp:select key="YesOrNo" name="IsAudit" value="${IsAudit}" p="y|f"/>
								</td>
								<td width="8%" align="right">
									制单日期：
								</td>
								<td width="31%">
									<input type="text" name="BeginDate" size="10"
										onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
									-
									<input type="text" name="EndDate" size="10"
										onFocus="javascript:selectDate(this)" value="${EndDate}" readonly>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									是否回收：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsReceive" value="${IsReceive}" p="y|f"/>
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">
									
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
									<a href="javascript:Del();"><img
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
                                <td width="50"><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>	
						<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
						<td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
						<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>

								<td width="50">
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../aftersale/listPurchaseTradesAction.do" />
								</td>

							</tr>
						</table>
					</div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
							<tr align="center" class="title-top">
								<td width="12%">
									编号
								</td>
								<td width="20%">
									供应商
								</td>
								<td width="13%">
									联系人
								</td>								
								<td width="11%">
									制单人
								</td>
								<td width="12%">
									制单日期
								</td>
                                <td width="7%">
									是否复核
								</td>
								<td width="7%">
									是否回收
								</td>
                                <td width="7%">
									是否作废
								</td>
							</tr>
							<logic:iterate id="s" name="also">
								<tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
									<td>
										${s.id}
									</td>
									<td>
										${s.providename}
										
									</td>
									<td>
										${s.plinkman}
									</td>									
									<td>
										<windrp:getname key="users" p="d" value="${s.makeid}"/>
									</td>
									<td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/>
									</td>
                                    <td>
										<windrp:getname key="YesOrNo" p="f" value="${s.isaudit}"/>
									</td>
									<td>										
										<windrp:getname key="YesOrNo" p="f" value="${s.isreceive}"/>
									</td>
                                    <td>										
										<windrp:getname key="YesOrNo" p="f" value="${s.isblankout}"/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
                        <div style="width:100%">
                        	<div id="tabs1">
                              <ul>
                                <li><a href="javascript:Detail();"><span>换货详情</span></a></li>
                              </ul>
                            </div>
                            <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
                        </div>						
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="PID" id ="PID" value="${PID}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="IsReceive" id ="PID" value="${IsReceive}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
		
	</body>
</html>
