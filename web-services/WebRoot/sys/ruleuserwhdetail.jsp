<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>

	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script language="javascript">
var parray=new Array();  

function Del(){
	$("input:checkbox").each(function(i){
		if(this.name == "uid" && this.checked == true){
 			parray.push(this.value);
 		}
	}); 
		if(parray.length > 0){
			if(confirm("你确定要删除仓库权限吗？")){
				popWin("../sys/delRuleUserWHAction.do?WID=${WID}&uids="+parray,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
}
function toSet(){
	//popWin("../sys/selectRuleUserWHIframeAction.do?uid="+${uid},800,500);
	popWin("../sys/toAddRuleUserWHAction.do?WID="+${WID},800,500);
}
function toSetWH(){
	popWin("../users/selectUserVisitOrganWHIframeAction.do",800,500);
} 
	//全选框点击事件
	function check(){
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
		 
	}
 	
 	function changeCheckAll(){
 		setCheckAll();
 	} 
 	function Visit(){
		
			popWin("../sys/toVisitUsersWarehouseAction.do",900,500);
		
	}
</script>
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td width="772">
											用户管辖权限
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="title-func-back">
										<!-- yzj 20100407 start -->
										<td width="70" align="center">
											<a href="javascript:toSet();"><img
													src="../images/CN/addnew.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;增加用户</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										<td width="50" align="center">
											<a href="javascript:Del();"><img
													src="../images/CN/delete.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;删除</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										<td class="SeparatePage">
											<pages:pager action="../sys/listRuleUserWHAction.do" />
										</td>


									</tr>
								</table>

								<table class="sortable" width="100%" border="0" cellpadding="0"
									cellspacing="1">
									<tr class="title-top">
										<td width="1%" class="sorttable_nosort">
											<input type="checkbox" id="checkAll" name="checkall"
												onClick="check();">
										</td>
										<td align="center">
											用户编号
										</td>
										<td align="center">
											用户名
										</td>
										<td align="center">
											登录名
										</td>
										<td align="center">
											所属机构
										</td>
									</tr>
									<logic:iterate id="u" name="rwlist">
										<tr class="table-back-colorbar">
											<td>
												<input type="checkbox" id="chk_${u.uid}" name="uid"
													value="${u.uid}" />
											</td>
											<td>
												${u.uid}
											</td>
											<td>
												${u.loginname}
											</td>
											<td>
											 	${u.realname }
											</td>
											<td>
											 	${u.organname }
											</td>
										</tr>
									</logic:iterate>
								</table>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
