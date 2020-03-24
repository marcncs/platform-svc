<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>	
<html>
	<head>
<title>城市列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
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
		return false;
	} */


	if($("#tbody :checked").length < 1){
		alert("请选择城市");
		return;
	}
	var ids = new Array();	
	var name=new Array();		
	$("#tbody :checked").each(function(){
		ids.push($(this).val());
		name.push($(this).attr("name"));
    });	
	window.opener.addCity(ids,name);
	window.close(); 
	
	//showloading();
	//listRole.submit();
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
	
	//alert("请至少选择一个选项!");
	return false;
}

</script>
	</head>
	<body style="overflow:auto">

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
								选择城市
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr class="title-func-back">
							<td align="left">
								<a href="javascript:doModifyAR();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确认</a>
							</td>
						</tr>
					</table>
					<form name="listRole" method="post" action="updUserRoleAction.do">
					<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
						
						<tr class="title-top">
							<td width="2%">
								<input type="checkbox" name="checkall" value="on"
									onClick="Check();">
							</td>
							<td width="12%">城市编号</td>
							<td width="15%">
								城市名称</td>
						</tr>
						<tbody id="tbody">
						<c:forEach items="${countryareaList}" var="iItem" varStatus="loopStatus">
							<tr class="table-back-colorbar">
								<td align="left">
									<input type="checkbox" name="${iItem.areaname}" value="${iItem.id}"
										<c:forEach items="${list}" var="s"><c:if test="${s==iItem.id}">checked="checked"</c:if></c:forEach> >
										
								</td>
								<td>${iItem.id}</td>
								<td>
									${iItem.areaname}
								</td>
							</tr>
						</c:forEach>						
						</tbody>
					</table>
</form>
				</td>
			</tr>
		</table>
	</body>
</html>

