<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>拜耳渠道库存管理系统</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="../js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script src="../js/jquery-1.11.1.min.js"></script>
		<script src="../js/ztree/jquery.ztree.all.min.js"></script>

		<script type="text/javascript">
			var ztree_setting = {
				view: {
					showIcon: false,
					selectedMulti: false,
					expandSpeed: ""
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: function(event, treeId, treeNode, clickFlag) {
						//console.log(treeNode.name, treeNode.isParent);
						$('iframe').attr('src', '../sys/regionDetailAction.do?pid='+treeNode.id);
					}
				}
			};
			
			var tree_nodes = [
				//{ id:-1, pId:-2, name:"全国", rank:-2, xid:-1, open:true}
			];
			function loadTreeData() {
				$.ajax({
					type: "POST",
					url: "regionManageAction.do?op=regionTreeData",
					dataType: 'json',
					data: "",
					success: function(msg){
						for (var i=0; i<msg.length; i++){
							msg[i]['xid'] = msg[i].id;
							msg[i].id = msg[i].areaId;
						}
						$.fn.zTree.init($("#tree"), ztree_setting, tree_nodes.concat(msg));
					}
				});
			}
			$(document).ready(function(){
				loadTreeData();
				
			});

			function addRegion() {
				var node = isCheck();
				if (node == null) {
					return;
				}
				var xid = node.xid;
				var rank = node.rank;
				if (rank == 2) {
					alert('不能为此类型区域添加子区域!');
					return;
				}
				window.open("../sys/addRegionItemAction.do?op=addRegionPage&xid="+xid+"&rank="+rank+"&areaId="+node.areaId,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
			function updateRegion() {
				var node = isCheck();
				if (node == null) {
					return;
				}
				var xid = node.xid;
				if (node.rank == -2){
					alert('该结点不能修改!');
					return;
				}
				window.open("../sys/updateRegionItemAction.do?op=toPage&xid="+xid,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
			function deleteRegion() {
				var node = isCheck();
				if (node == null) {
					return;
				}
				if (node.id == -1) {
					alert('不能删除顶级结点！');
					return;
				}
				if(window.confirm("您确认要删除该区域及其所有子区域吗?")){
					window.open("../sys/deleteRegionItemAction.do?id="+node.xid+"&rank="+node.rank+"&areaId="+node.id,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
				}
			}
			function isCheck() {
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var nodes = treeObj.getSelectedNodes();
				if (nodes && nodes.length>0) {
					return nodes[0];
				} else {
					alert("请选择一个结点!");
					return null;
				}
			}
			function importFile() {
				popWin("../sys/importPageAction.do",500,300);
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
								系统管理>>区域管理
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="25%" valign="top"
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
								<a href="javascript:addRegion();"><img
										src="../images/CN/addnew.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;新增</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/sys/toUpdRegionAction.do">
							<td>
								<a href="javascript:updateRegion();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							</ws:hasAuth>
							<ws:hasAuth operationName="/sys/delRegionAction.do">
							<td>
								<a href="javascript:deleteRegion();"><img
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
							<td>
								<a href="javascript:importFile()"><img
										src="../images/CN/import.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导入</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td>
								<a href="regionManageAction.do?op=exportXls"><img
										src="../images/CN/outputExcel.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导出</a>
							</td>
						</tr>
					</table>
					<table height="100%"  width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td vAlign="top">
							<!-- --> 
								<div style="height: 100%; overflow: auto">
								
									<table height="98%" border="0">
										<tr>
											<td valign="top">
												<ul id="tree" class="ztree"></ul>
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
				<td width="100%">
					<IFRAME id="product" style="WIDTH: 100%; HEIGHT: 100%"
						name="product" src="../sys/regionDetailAction.do?pid=-1" frameBorder=0
						scrolling=no></IFRAME>
				</td>
			</tr>
		</table>
	</body>
</html>
