<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
<script language="JavaScript">
	function newPhoneBookSort() {
		popWin("../self/toAddPhoneBookSortAction.do", 500, 250);
	}

	function updPhoneBookSort() {
		if(checkid == 0){
			alert("请选择你要修改的 组!");
		}else{

			popWin("../self/../self/listPhoneBookSortDetailAction.do?ID="+checkid, 500, 250);
		}
	
		
	}
	var pname="";
	var checkid =0;
	function detailphoneBookSort(id,name){
		pname=name;
		checkid=id;
		var subPhonebook = 	document.getElementById("phonebook");
		subPhonebook.src="listPhoneBookAction.do?PbsID="+id;
	}
	
	function Del(){
	
		if(checkid == 0){
			alert("请选择你要删除的 组!");
		}else{
			if(window.confirm("您确认要删除"+pname+" 组吗?")){
				popWin2("../self/delPhoneBokSortAction.do?ID="+checkid);
				checkid=0;
			}
		}
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
								我的办公桌>>通讯录
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="20%" valign="top" style="border-right: 1px solid #D2E6FF;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="title-back">
						<tr>
							<td style="text-align: left;">
								&nbsp;&nbsp;&nbsp;组
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="title-func-back">
									<tr>
										<td width="50">
											<a href="javascript:newPhoneBookSort();"><img
													src="../images/CN/addnew.gif" width="16" height="16"
													border="0" align="absmiddle">&nbsp;新增</a>
										</td>
										<td width="1">
											<img src="../images/CN/hline.gif" width="2" height="14">
										</td>
										<td width="50">
											<a href="javascript:updPhoneBookSort();"><img
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
										<td>
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<logic:iterate id="p" name="pbs">
							<tr>
								<td width="11%" height="16">
									<img src="../images/CN/t.gif" width="16" height="16">
								</td>
								<td width="89%" class="CC" title="点击查看详情">
									<a href="javascript:detailphoneBookSort(${p.id},'${p.sortname}');" 
										 class="BB"> ${p.sortname}</a>
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
				<td width="80%">
					<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 100%"
						name="phonebook" src="../self/listPhoneBookAction.do"
						frameBorder=0 scrolling=no></IFRAME>
				</td>
			</tr>
		</table>
	</body>
</html>
