<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<script language="javascript">
 function Check(){
		if(document.listRole.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listRole.length;i++){
			if (!document.listRole.elements[i].checked)
				if(listRole.elements[i].name != "checkall"){
				document.listRole.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listRole.length;i++){
			if (document.listRole.elements[i].checked)
				if(listRole.elements[i].name != "checkall"){
				document.listRole.elements[i].checked=false;
				}
		}
	} 


function doModifyAR(){
/*	if(!isSelected()){
		alert("请至少选择一个选项!");
		return;
	} */
	showloading();
	listRole.submit();
}


function isSelected(){
	var i=eval("listRole.role");
	if(i.length>1){
		for(var j=0;j<i.length;j++){
			if(i[j].checked){
			return true;
			}
		}
	}else{
		if(i.checked){
			return true;
		}
	}
	
	
	return false;
}

</script>
		<title>用户列表</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<body style="overflow: auto;">

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								设置用户呼叫事件查询权限
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr class="title-func-back">
							<td align="left">
								<a href="javascript:doModifyAR();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
						</tr>
					</table>
					<form name="listRole" method="post"
							action="updUserCallEventAction.do">
					<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
						
							<input type="hidden" name="muid" value="${muid}">
						<tr class="title-top">
							<td width="178">
								<input type="checkbox" name="checkall" value="on"
									onClick="Check();">
								用户编号
							</td>
							<td width="598">
								用户名
							</td>
						</tr>

						<c:forEach items="${als}" var="iItem" varStatus="loopStatus">
							<tr class="table-back-colorbar">
								<td>
									<input type="checkbox" name="role" value="${iItem.userid}"
										${iItem.id!=0?'checked':''} >
									${iItem.userid}
								</td>
								<td>
									${iItem.useridname}
								</td>
							</tr>
						</c:forEach>
						
						
					</table>
</form>
				</td>
			</tr>
		</table>
	</body>
</html>

