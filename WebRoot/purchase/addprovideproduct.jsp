<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<script src="../js/prototype.js"></script>
        <script src="../js/function.js"></script>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);		
        var f=x.insertCell(5);	

        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";

 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"16\" value='"+p.productid+"' readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"45\" value='"+p.productname+"' readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"6\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" value='"+p.unitidname+"' readonly>";
		f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0.00\" onfocus=\"this.select();\" size=\"8\" maxlength=\"10\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onKeyUp=\"clearNoNum(this)\">";

}

function SupperSelect(){
	var p = showModalDialog("toSelectLockProductAction.do",null,"dialogWidth:21cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if (p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
		
	}
}

function Check(){
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}
	
function formcheck(){
	var productid  = document.all("productid");
	if(productid==undefined){
		alert("请选择产品!");
		return false;
	}
	if ( !Validator.Validate(document.addform,2) ){
		return false;
	}
	showloading();
	return true;
}

</script>
	</head>

	<body style="overflow: scroll">
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
								新增相关产品
							</td>
						</tr>
					</table>
					<form name="addform" method="post"
						action="../purchase/addProviderProductAction.do" onSubmit="return formcheck();">
						<fieldset align="center">
							<legend>
								<table width="50" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											基本信息
										</td>
									</tr>
								</table>
							</legend>
							<table width="75" border="0" cellpadding="0" cellspacing="1">
								<tr align="center" class="back-blue-light2">
									<td width="73">
										<img src="../images/CN/selectp.gif" border="0"
											style="cursor: pointer"
											onClick="SupperSelect(dbtable.rows.length)">
									</td>
								</tr>
							</table>
							<table width="100%" id="dbtable" border="0" cellpadding="0"
								cellspacing="1">
								<tr align="center" class="title-top">
									<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();">
									</td>
									<td width="26%">
										产品编号
									</td>
									<td width="20%">
										产品名称
									</td>
									<td width="23%">
										规格型号
									</td>
									<td width="12%">
										单位
									</td>
									<td width="17%">
										单价
									</td>
								</tr>
							</table>
							<table width="100%" height="20"  border="0" cellpadding="0" cellspacing="0">
				              <tr align="left" class="table-back">
				                <td  height="20"><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif" width="19" height="20" border="0"></a></td>
				                
				              </tr>
				            </table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<div align="center">
										<input type="submit" name="Submit" value="确定">
										&nbsp;&nbsp;
										<input name="cancel" type="button" id="cancel" value="取消"
											onClick="javascript:window.close();">
									</div>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
