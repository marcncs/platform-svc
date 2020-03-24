<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>积分目标</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/showPE.js"> </SCRIPT>
		<script type="text/javascript">

			function Add(){
				popWin("../keyretailer/toAddSBonusTargetAction.do",1000,650);
			}
			function Update(){
				if(checkid!=""){
					popWin("../keyretailer/toUpdSBonusTargetAction.do?id="+checkid,1000,450);		
				}else{
					alert("请选择你要修改的记录!");
				}
			}

			this.onload = function abc(){
				document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
			}

			var checkid="";
			function CheckedObj(obj,objid){
				for(i=0; i<obj.parentNode.childNodes.length; i++)
				 {
					   obj.parentNode.childNodes[i].className="table-back-colorbar";
				 }
				obj.className="event";
			 	checkid=objid;
			}
		
		function oncheck(){
			search.target="";
			search.action="../keyretailer/listSBonusTargetAction.do";
			search.submit();
		}
	
		function SelectOrgan1(){
			var p=showModalDialog("../keyretailer/selectOrganAction.do?type=targetFrom",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.fromOrganId.value=p.id;
				document.search.fromorganname.value=p.organname;
		}
	
		function SelectOrgan2(){
			var p=showModalDialog("../keyretailer/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.search.toOrganId.value=p.id;
				document.search.toorganname.value=p.organname;
		}
	
		function SelectProduct(){
				var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
				if ( p==undefined){return;}
				document.search.productid.value=p.id;
				document.search.productName.value=p.productname;
			}
		
		function Del(){
			if(checkid!=""){
				if ( confirm("你确认要删除编号为:"+checkid+"的数据吗?") ){
					popWin2("../keyretailer/delSBonusTargetAction.do?id="+checkid);
				}
			}else{
				alert("请选择你要操作的记录!");
			}
	}
	
	function OutPut(){
		search.target="";
		search.action="../keyretailer/excPutSBonusTargetAction.do";
		search.submit();
		search.action="../keyretailer/listSBonusTargetAction.do";
	}
	
	function Import(){ 
	  	popWin("../keyretailer/toImportSBonusTargetAction.do",500,300);
	}
	function ImportAgreement(){ 
	  	popWin("../keyretailer/toUploadSBonusFileAction.do",500,300);
	}
</script>
  </head>
  
  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="../keyretailer/listSBonusTargetAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									出货客户：
								</td>
								<td>
									<input name="fromOrganId" type="hidden" id="fromOrganId" value="${fromOrganId}">
									<input name="fromorganname" type="text" id="fromorganname" size="30"  value="${fromorganname}"
									readonly><a href="javascript:SelectOrgan1();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td align="right">
									进货机构：
								</td>
								<td>
									<input name="toOrganId" type="hidden" id="toOrganId" value="${toOrganId}">
									<input name="toorganname" type="text" id="toorganname" size="30"  value="${toorganname}"
									readonly><a href="javascript:SelectOrgan2();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td align="right">
									产品：
								</td>
								<td>
									<input name="productid" type="hidden"  id="productid" value="${productid}">
									<input name="productName" type="text"  id="productName" size="30"  value="${productName}"
									readonly><a href="javascript:SelectProduct();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									创建日期：
								</td>
								<td>
								<input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}"
									onFocus="javascript:selectDate(this)" readonly>
								-
								<input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}"
									onFocus="javascript:selectDate(this)" readonly>
								</td>
								<td width="9%" align="right">
									年度： 
								</td>
								<td width="20%">
									<input type="text" name="year" value="${year}"
										maxlength="30" />
								</td>
								<td class="SeparatePage">
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<ws:hasAuth operationName="/keyretailer/toAddSBonusTargetAction.do">
									<td width="50">
										<a href="javascript:Add();"><img src="../images/CN/addnew.gif"
												width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a>
									</td>
									<td width="1">
										<img src="../images/CN/hline.gif" width="2" height="14">
									</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/toUpdSBonusTargetAction.do">
								<td width="50" align="center">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/keyretailer/delSBonusTargetAction.do">
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">删除 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<td width="50">
								<ws:hasAuth operationName="/keyretailer/excPutSBonusTargetAction.do">
									<a href="javascript:OutPut()"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
									</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="50">
								<ws:hasAuth operationName="/keyretailer/toImportSBonusTargetAction.do">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">导入 </a>
								</ws:hasAuth>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="120">
								<a href="../common/downloadfile.jsp?filename=templates/<windrp:encode key='积分计划协议.doc' />">
								<img src="../images/CN/beizheng.gif" border="0">积分计划协议下载</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<ws:hasAuth operationName="/keyretailer/toImportSBonusTargetAction.do">
								<td width="120">
								<a href="javascript:ImportAgreement()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">积分计划协议上传 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<td class="SeparatePage">
									<pages:pager action="../keyretailer/listSBonusTargetAction.do" />
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							
							<tr align="center" class="title-top">
								<td >
									编号
								</td>
								<td >
									出货客户
								</td>
								<td >
									进货机构
								</td>
								<td >
									年度
								</td>
								<td >
									产品名
								</td>
								<td >
									规格
								</td>
								<td >
									计量单位
								</td>
								<td >
									目标数量
								</td>
								<td >
									积分点
								</td>
								<td >
									最后修改日期
								</td>
							</tr>
							<logic:iterate id="pj" name="datas">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.id}');">
									<td>
										${pj.id}
									</td>
									<td>
										${pj.fromorganname}
									</td>
									<td>
										${pj.toorganname}
									</td>
									<td>
										${pj.year}
									</td>
									<td>
										${pj.productname}
									</td>
									<td>
										${pj.spec}
									</td>
									<td>
										${pj.countunitname}
									</td>
									<td>
										${pj.targetamount }
									</td>
									<td>
										<fmt:formatNumber value="${pj.bonuspoint}" pattern="###,###"></fmt:formatNumber>
									</td>
									<td>
										<windrp:dateformat value='${pj.modifieddate}'/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
					</div>
				</td>
			</tr>
	</table>
  </body>
</html>
