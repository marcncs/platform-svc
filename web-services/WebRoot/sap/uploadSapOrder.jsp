<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head><title>SAP订单导入
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
			var plantType = document.refer.plantType.value;
			if(plantType=="") {
			alert("请选择工厂");
			return false;
			}
			showloading();
			return true;
		}
	function SelectOrgan(){
		var p=showModalDialog("selectPlantAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.refer.plantCode.value=p.oecode;
			document.refer.plantName.value=p.organname;
			document.refer.plantType.value=p.plantType;
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
									打印标签&gt;&gt;数据导入
								</td>
							</tr>
	</table>
    <form name="refer" method="post" enctype="multipart/form-data"
			action="../sap/importSapPlantPoAction.do"
			onSubmit="return formcheck()">
	<input name="plantType" type="hidden" value="">
    <div id="bodydiv">
   		
	<table border="0" cellspacing="0" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr class="table-back" width="100%">
			<td width="9%" align="right" height="50"></td>
			<td>SAP订单数据，包括杭州工厂和分装厂的手工数据。
			 </td>
			 <td></td>
			</tr>
			<tr class="table-back" width="100%">
			<td width="9%" align="right">工厂：</td>
            <td width="25%">
            	<input name="plantCode" type="hidden" id="plantCode" value="${plantCode}">
									<input name="plantName" type="text" id="plantName" size="30"  value="${plantName}"
									readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>
			</td>
			<td></td>
			</tr>
			<tr>
			<td><br/></td>
			</tr>
			<tr class="table-back" width="100%">
			<td width="9%" align="right"></td>
			<td>
			<input name="sapFile" type="file" id="sapFile"
								size="40" dataType="Filter" accept="xml" msg="只能上传xml文件!">
			<td>
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