<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/Currency.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
  function addRow(){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);       
        var d=x.insertCell(3);
        var e=x.insertCell(4);
 
       	a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
		d.className = "table-back";
		e.className = "table-back";
        
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"oid\" type=\"text\" size=\"20\" readonly>";
		c.innerHTML="<input name=\"oname\" type=\"text\" size=\"40\" readonly>";
		d.innerHTML="<input name=\"integral\" type=\"text\" size=\"20\" readonly>";
        e.innerHTML="<input name=\"adjustintegral\" type=\"text\"  size=\"20\" >";
       
		i++;
    chebox=document.all("che");  //計算total值
 
}
	

function SupperSelect(rowx){
		window.open("../assistant/selectMultiOrganAction.do",null,"height=550,width=700,top=200,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	
}

//--------------------------------start -----------------------	
function addItemValue(obj){
	var vcid = document.validateProvide.oid;
	if ( vcid != undefined ){
		if ( vcid.length ){
			for ( k=0; k<vcid.length; k++){
				if ( vcid[k].value == obj.oid ){
					return;
				}
			}
		}else{
			if ( vcid.value == obj.oid ){
					return;
			}
		}		
	}
	
	addRow();
	var l = dbtable.rows.length;
	if ( l < 3){
		document.validateProvide.item("oid").value =obj.oid;
		document.validateProvide.item("oname").value =obj.oname;
		document.validateProvide.item("integral").value=formatCurrency(obj.integral);
	}else{
		document.validateProvide.item("oid")[l-2].value =obj.oid;
		document.validateProvide.item("oname")[l-2].value =obj.oname;
		document.validateProvide.item("integral")[l-2].value=formatCurrency(obj.integral);
	}
}
//--------------------------------end -----------------------	

function adjust(){
	var vobj = document.validateProvide.adjustintegral;
	var dealintegral = document.validateProvide.dealintegral.value;
	if ( vobj != undefined ){
		if ( vobj.length ){
			for ( k=0; k<vobj.length; k++){
				vobj[k].value=dealintegral;
			}
		}else{
			vobj.value=dealintegral;
		}		
	}
}

 
function deleteR(){
 chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById('dbtable').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			document.all('dbtable').deleteRow(1)
		}
 	 }
}


	function Check(){
		if(document.validateProvide.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.validateProvide.length;i++){

			if (!document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.validateProvide.length;i++){
			if (document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=false;
				}
		}
	}

	function ChkValue(){
		var cid = document.validateProvide.oid;
		if( cid == undefined ){
			alert("请选择机构！");
			return false;
		}
				
		return true;
	}
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>

<SCRIPT language=javascript>
//
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改机构积分调整单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../assistant/updAdjustOIntegralAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		<input type="hidden" name="id" value="${r.id}">
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
		<tr>
		  <td width="9%"  align="right">备注：</td>
		  <td ><textarea name="remark" cols="120" id="remark">${r.remark}</textarea></td>
		  </tr>
      </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
				<td width="51%"><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
               
                
                <td id="elect" >选择机构： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>                
              </tr>
            </table></td>
                  
                </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
                <tr align="center" class="title-top"> 
                  <td width="3%"> 
<input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                  <td width="14%" >机构编号</td>
				  <td width="35%" >机构名称</td>
				  <td width="22%" >当前积分</td>
                  <td width="26%">调整积分</td>
                </tr>
				 <c:set var="count" value="2"/>
            <logic:iterate id="p" name="rflist" > 
              <tr class="table-back">
                <td ><input type="checkbox" value="${count}" name="che"></td>
                <td ><input name="oid" type="text" id="oid" size="20" value="${p.oid}" readonly></td>
                <td><input name="oname" type="text" id="oname" size="40" value="${p.oname}" readonly></td>
				<td><input name="integral" type="text" id="integral" size="20" value="<fmt:formatNumber value="${p.integral}" pattern="0.00"/>" readonly></td>
                <td><input name="adjustintegral" type="text" id="adjustintegral" size="20" value="<fmt:formatNumber value="${p.adjustintegral}" pattern="0.00"/>"></td>
              </tr>
			  <c:set var="count" value="${count+1}"/>
			</logic:iterate> 
              </table>  
              <table width="100%"  border="0" cellpadding="0" cellspacing="0">
                <tr class="table-back"> 
				  <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>	
                  <td width="60%">&nbsp;</td>
                  <td width="17%" align="right">统一调整积分：</td>
                  <td width="20%">
                    <input name="dealintegral" type="text" id="dealintegral" size="12">
					<input type="button" value="调整" onClick="adjust()">
				  </td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center">&nbsp;</td>
          </tr>
          <tr>
            <td  align="center"><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="history.back();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
