<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script src="../js/function.js"></script>
</head>
<script language="javascript">

function formcheck(){
//	if ( $F('idcode').trim() ==""){
//		alert("条码不能为空!");
//		$('idcode').select();
//		return;
//	}
	showloading();
	addform.submit();
}

  function addRow(){ 
    var x = document.all("xq").insertRow(xq.rows.length);

	var a=x.insertCell(0);
	var b=x.insertCell(1);
	var c=x.insertCell(2);

	a.innerHTML="<input type='checkbox'  name='che'>";
	b.innerHTML="<input type='text' name='idcode' size='35' maxlength='50'>";
	c.innerHTML="${wbit}"; 
}

function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >=1){
			for(var i=2;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById('xq').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			if (chebox.checked){
			 document.all('xq').deleteRow(2);
			}
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
	var che=document.all("che");
	if(che!=null){	
		if (che.length){
			for(j=0; j<che.length; j++){
				che[j].checked=true;
			}
		}else{
			che.checked=true;
		}
	 }
}

function uncheckAll(){
	var che=document.all("che");
	if(che!=null){	
		if (che.length){
			for(j=0; j<che.length; j++){
				che[j].checked=false;
			}
		}else{
			che.checked=false;
		}
	 }
}
 
</script>

<body style="overflow:auto">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">录入条码</td>
      </tr>
    </table>
	<fieldset>
	<legend>基本信息</legend>
	<form name="addform" method="post" action="addStockCheckIdcodeiiAction.do" >
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
	<input type="hidden" name="billno" value="${sc.id}">
	<tr>
	  	<td width="8%"  align="right">盘点单号：</td>
          <td width="19%">${sc.id}</td>
          <td width="6%" align="right">盘点仓库：</td>
          <td width="12%"><windrp:getname key='warehouse' value='${sc.warehouseid}' p='d'/></td>
	      <td width="7%" align="right">&nbsp;</td>
	      <td width="9%"></td>		 
	  </tr>
	  <tr><td colspan="8"></tr>
      <tr>
      	<td></td>
        <td colspan="5">
        	<a href="javascript:addRow();"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="新增"></a>
			     <a href="javascript:deleteR();"><img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除"></a>
			     
				 <table width="521" border="0" id="xq" cellpadding="0" cellspacing="0">
                 <tr>
                   <td width="71"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                   <td width="267" >条码</td>
                   <td width="183">仓位</td>
                 </tr>
                 
                  <tr>
                   <td width="71"><input type="checkbox" name="che"></td>
                   <td width="267" ><input type="text" name="idcode" size="35" maxlength="50"></td>
                   <td width="183">${wbit}</td>
                 </tr>
               </table>
        </td>
      </tr>
	 </table>
	 
	    <table width="100%">
	 	<tr align="center">
            <td align="center"><input type="button" name="Submit" onClick="formcheck();" value="提交"> &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>

		
	  </table> 
	  
	  </form>    
	  </fieldset>  
 </td>
</tr>
</table>
</body>
</html>
