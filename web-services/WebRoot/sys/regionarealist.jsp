<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>拜耳渠道库存管理系统</title>

		<script src="../js/prototype.js"></script>
		<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
		<link rel="stylesheet" href="../css/xtree.css" type="text/css">

		<script type="text/javascript">
var checkid="";
function show(psid){
	checkid=psid;
	document.all.product.src="../sys/listRegionAreaAction.do?OtherKey="+psid;
}

function AddNew(){
	window.open("../sys/toAddRegionAction.do?acode=1","newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");

}

function Update(){
	if(checkid!=""){
		if (checkid==1){
			alert("不能修改顶级区域!");
			return;
		}
		window.open("../sys/toUpdRegionAction.do?acode="+checkid,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}else{
		alert("请选择区域!");
	}
}		


function Del(){
	if(checkid!=""){
		if (checkid==1){
			alert("不能删除顶级区域!");
			return;
		}
		if(window.confirm("您确认要删除所选的区域吗？如果删除将永远不能恢复!")){
		window.open("../sys/delRegionAction.do?acode="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}else{
	alert("请选择你要操作的记录!");
	}
}

function relateUser(){
      if(checkid!=""){
		if (checkid==1){
			alert("不能关联顶级区域!");
			return;
		}
		window.open("../sys/toRelateUserAction.do?acode="+checkid,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}else{
		alert("请选择大区!");
	}
       
}

	function Import() {
	   popWin("../sys/toImportRegionAction.do", 500, 300); 
		//window.open("../sys/toImportRegionAction.do", "newwindow","height=300,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no")
	}

</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" bordercolor="#6893CF">
			<tr>
				<td height="27" colspan="2">
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								系统设置>>区域信息
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="10%" valign="top"
					style="border-right: 1px solid #D2E6FF;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="title-back">
						<tr>
							<td style="text-align: left;">
								&nbsp;&nbsp;&nbsp;区域
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="title-func-back">
						<tr>
							<ws:hasAuth operationName="/sys/toAddRegionAction.do">
							<td>
								<a href="javascript:AddNew();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/sys/toUpdRegionAction.do">
							<td>
								<a href="javascript:Update();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/sys/delRegionAction.do">
							<td>
								<a href="javascript:Del();"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<%--							
							<td>
								<a href="javascript:relateUser();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;关联经理</a>
							</td>
							
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td>
								<a href="javascript:Import();"><img
										src="../images/CN/import.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导入区域</a>
							</td>
							--%>
						</tr>
					</table>
					<table height="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="160" vAlign="top">
							<!-- --> 
								<div style="width: 160px; height: 100%; overflow: auto">
								
									<table height="98%" border="0">
										<tr>
											<td valign="top">
												<script>
                                    var pstree = new WebFXLoadTree("All","../regionAction.do?id=1","javascript:show('1')");
                                    document.write(pstree);
                                    </script>
											</td>
										</tr>
									</table>
								<!--  
								</div>
								-->
							</td>
						</tr>
						<tr>
							<td height="55">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
				<td width="70%">
					<IFRAME id="product" style="WIDTH: 100%; HEIGHT: 100%"
						name="product" src="../sys/listRegionAreaAction.do" frameBorder=0
						scrolling=no></IFRAME>
				</td>
			</tr>
		</table>



	</body>
</html>
