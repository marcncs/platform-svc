<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>APP上传</title>
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
			action="../app/uploadAppUpdateAction.do"
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
			</table><br/>
			<fieldset style="text-align: center;">
			<legend>
					基本信息
				</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tbody>
			    <tr>
			      <td width="15%" height="25" align="right">发布名称： </td>
			      <td width="18%"><input id="publishName" name="publishName" type="text" value="" size="15" maxlength="30">
			      </td>
			      <td width="15%" align="right"> 版本： </td>
			      <td width="18%"><input id="appVersion" name="appVersion" type="text" value="" size="15" maxlength="30"></td>
			      <td width="12%" align="right"> </td>
			      <td width="18%"></td>
			      <td></td>
		        </tr>
		        <tr>
		        	<td align="right">
					 	更新日志：
					</td>
					<td colspan="5">
						<textarea name="updateLog" cols="100" style="width: 100%"
							rows="3" id="updateLog" dataType="Limit" max="512"
							msg="更新日志必须在512个字之内" require="false"></textarea>
						<br>
						<span class="td-blankout">(更新日志512个字符!)</span>
					</td>
		        </tr>
		      </tbody>
		    </table>
          </fieldset><br>
			<fieldset style="text-align: center;">
				<legend>
					文件
				</legend>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"></td>
						<td width="61%">
							选择APP文件：
							<input name="appFile" type="file" id="appFile"
								size="40" dataType="Filter" accept="apk" msg="只能上传apk文件!">
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
