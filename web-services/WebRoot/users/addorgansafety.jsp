<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
 var chebox=null;
  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);		
        var f=x.insertCell(5);
		var g=x.insertCell(6);	

        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";

        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"16\" readonly value='"+p.productid+"'>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"45\" readonly value='"+p.productname+"'>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"15\" readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"4\" readonly value='"+p.unitidname+"'>";
		f.innerHTML="<input name=\"safetyl\" type=\"text\" id=\"safetyl\" value=\"0\" onfocus=\"this.select();\" size=\"8\" maxlength=\"10\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"库存只能是数字!\" require=\"true\" >";
        g.innerHTML="<input name=\"safetyh\" type=\"text\" id=\"safetyh\" value=\"0\" onfocus=\"this.select();\" size=\"8\" maxlength=\"10\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"库存只能是数字!\" require=\"true\" >";		
    chebox=document.all("che");  //計算total值

}


	//--------------------------------start -----------------------	
function addItemValue(obj){
	if (obj==undefined){return;}
	if ( isready('productid', obj.productid) ){
		return;
	}		
	addRow(obj);
}
//--------------------------------end -----------------------	

function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.all('dbtable').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			document.all('dbtable').deleteRow(1)
		}
 	 }
}
function Check(){
		if(document.addform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.addform.length;i++){

			if (!document.addform.elements[i].checked)
				if(addform.elements[i].name != "checkall"){
				document.addform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.addform.length;i++){
			if (document.addform.elements[i].checked)
				if(addform.elements[i].name != "checkall"){
				document.addform.elements[i].checked=false;
				}
		}
	}
	
function SupperSelect(){

		var p=showModalDialog("../users/toSelectSafetyProductAction.do?oid=${oid}",null,"dialogWidth:22cm;dialogHeight:17cm;center:yes;status:no;scrolling:auto;");
		if (p==undefined){return;}
		for(var i=0;i<p.length;i++){	
			if ( isready('productid', p[i].productid) ){
				continue;
			}
			addRow(p[i]);	
		}
}

	
function CheckInput(){
	var productid = document.addform.productid;
	if ( productid==undefined){
		alert("请选择产品！");
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

<body style="overflow-y: auto;">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增仓库报警设置</td>
        </tr>
      </table>
	  <form name="addform" method="post" action="../users/addOrganSafetyAction.do" onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息<input name="OID" type="hidden" id="OID" value="${OID}"></td>
        </tr>
      </table>
	  
	  </legend>
	  
	  <table width="75"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-blue-light2">
          <td width="73"><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect()" ></td>
          </tr>
      </table>
	  <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="8%">产品编号</td>
          <td width="11%" > 产品名称</td>
          <td width="14%">规格型号</td>
          <td width="3%">单位</td>
          <td width="8%">最低安全库存</td>
		  <td width="8%">最高安全库存</td>
          </tr>
      </table>
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
	  <tr align="center" class="table-back">
		<td width="2%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
		<td width="8%" align="center">&nbsp;</td>
		<td width="11%" align="center">&nbsp;</td>
		<td width="14%" align="right"></td>
		<td width="3%" align="center"></td>
		<td width="8%" align="center"></td>
		<td width="8%" align="center"></td>
	  </tr>
	</table>	
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="28%"> <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; 
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
	  </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
