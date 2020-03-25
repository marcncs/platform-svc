<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>盘库数据文件上传</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script language="JavaScript">
		function formcheck() {
			if (!Validator.Validate(document.refer, 2)) {
				return false;
			}
			if(document.getElementById("publishName").value.trim() == "") {
				alert("发布名称不能为空");
				return false;
			}
			var reg = /^[0-9]+([.]{1}[0-9]+)+?$/;
			if(!reg.test(document.getElementById("appVersion").value)) {
				alert("版本号只能包括小数点和数字，例如1.0");
				return false;
			}
			showloading();
			return true;
		}
		function SelectOrgan(){
			var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
				document.referForm.receiveorganid.value=p.id;
				document.referForm.oname.value=p.organname;
				document.referForm.wname.value=p.wname;
				document.referForm.inwarehouseid.value=p.wid;
				
		}
		function clearDeptAndUser(v_MakeDeptID,obj_deptname,obj_MakeID,obj_uName){
			document.getElementById(v_MakeDeptID).value="";
			document.getElementById(obj_deptname).value="";
			document.getElementById(obj_MakeID).value="";
			document.getElementById(obj_uName).value="";
		}
		function selectWIn(dom){
			var id = document.getElementById('receiveorganid').value;
			selectDUW(dom,'inwarehouseid',id,'w','')
		}
	</script>

	</head>

	<body>
		<form name="referForm" method="post" enctype="multipart/form-data"
			action="../warehouse/uploadInventoryAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						${menusTrace }
					</td>
				</tr>
			</table>
			<br />
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td align="right">
								机构：
							</td>
							<td>
								<input name="receiveorganid" type="hidden" id="receiveorganid">
								<input name="oname" type="text" id="oname" size="30"
									dataType="Require" msg="必须录入机构!" readonly>
								<a href="javascript:SelectOrgan(this);"><img
										src="../images/CN/find.gif" width="18" height="18" border="0"
										align="absmiddle">
								</a>
								<span class="style1">*</span>
							</td>
							<td align="right">
								仓库：
							</td>
							<td>
								<input type="hidden" name="inwarehouseid" id="inwarehouseid">
								<input type="text" name="wname" id="wname"
									onClick="selectWIn(this)" value="" readonly>
								<span class="style1">*</span>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
			<br>
			<fieldset style="text-align: center;">
				<legend>
					文件
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"></td>
						<td width="61%">
							选择盘库数据文件：
							<input name="idcodefile" type="file" id="appFile" size="40"
								dataType="Filter" accept="txt" msg="只能上传txt文件!">
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr align="center">
					<td>
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" onClick="window.close()"
							value="取消">
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
