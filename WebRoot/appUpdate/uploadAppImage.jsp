<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>APP图片上传</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/sorttable.js"> </SCRIPT>
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
			var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.refer.productId.value=p.id;
			document.refer.materialCode.value=p.nccode;
			document.refer.materialDescription.value=p.productname;
			document.refer.pname.value=p.productname;

		}
		function clearDeptAndUser(v_MakeDeptID,obj_deptname,obj_MakeID,obj_uName){
			document.getElementById(v_MakeDeptID).value="";
			document.getElementById(obj_deptname).value="";
			document.getElementById(obj_MakeID).value="";
			document.getElementById(obj_uName).value="";
		}
	</script>

	</head>

	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../app/uploadAppImageAction.do"
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
			<fieldset style="text-align: center;">
				<legend>
					文件
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"></td>
						<td width="61%">
							选择图片(png,jpg,gif,jpeg)文件：
							<input name="appFile" type="file" id="appFile"
								size="40" dataType="Filter" accept="png,jpg,gif,jpeg" msg="只能上传png文件!">
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
                </tr>
        </table> 
			
		</form>
	</body>
</html>
