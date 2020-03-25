<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head><title>箱码托码转换
</title><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<SCRIPT  src="js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<link href="../css/billlayout.css" rel="stylesheet" type="text/css" />
<link href="../css/ss.css" rel="stylesheet" type="text/css">
 <script language="javascript">
 	function formcheck() {
			if (!Validator.Validate(document.refer, 2)) {
				return false;
			}
			return true;
		}

 </script>     

</head>
<body style="background:#e9f3fc;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									分装厂&gt;&gt;箱码托码转换
								</td>
							</tr>
	</table>
    <form name="refer" method="post" enctype="multipart/form-data"
			action="../erp/doTollerChangeAction.do"
			onSubmit="return formcheck()">
	<input name="plantType" type="hidden" value="">
    <div id="bodydiv">
   		
	<table border="0" cellspacing="0" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr class="table-back" width="100%">
			<td width="9%" align="right" height="50"></td>
			<td>
			 </td>
			 <td></td>
			</tr>

			<tr>
			<td><br/></td>
			</tr>
			<tr class="table-back" width="100%">
			<td width="9%" align="right">
			  原文件：
			</td>
			<td>
			<input name="sapFile" type="file" id="sapFile"
								size="40" dataType="Filter" accept="txt" msg="请上传.txt文本文件!">
			</tr>
			<tr>
			<td><br/></td>
			</tr>
			<tr>
			<tr class="table-back" width="40%">
			<td width="9%" align="right"></td>
			    <td width="60%">
			       <input type="submit" name="Submit" value="上传">&nbsp;&nbsp;</input>
			    </td>
			    <td></td>
			</tr>
		</tbody>
	</table>
 
    </div>
    </form>
</body>
</html>