<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>	
<html>
	<head>
<title>用户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
	//showloading();
	popWin4("about:blank",500,200, "winform");   
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
								用户组类型
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="59">
								<a href="javascript:doModifyAR();"><img
										src="../images/CN/update.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;修改</a>
							</td>
							<td width="1135"></td>		
						</tr>
					</table>
					<table width="242" border="0" cellpadding="0" cellspacing="0">
					  <tr>
							<td width="60">
								所选用户组：
							</td>
							<td width="179">
<%--								${role.rolename}--%>
							</td>
						</tr>
					</table>
					<form name="listRole" method="post" action="updUserGroupAppAction.do" target="winform">
					<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
						
						<tr class="title-top">
                        	<td width="2%" >
								<input type="checkbox" name="checkall" value="on"
									onClick="Check();">
							</td>
							<td width="10%" >								
								类型编号
							</td>
							<td width="88%">
								类型名称
							</td>
						</tr>
						<input type="hidden" name="groupId" value="${groupId}">

						<c:forEach items="${appList}" var="iItem">
							<tr class="table-back-colorbar">
								<td>
									<input type="checkbox" name="appId" value="${iItem.APPID}"
										${iItem.ISCHECKED==1?'checked':''} >
								</td>
                                <td>									
									${iItem.APPID}
								</td>
								<td >
									${iItem.APPNAME}
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

