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
	if ( $F('idcode').trim() ==""){
		alert("条码不能为空!");
		$('idcode').select();
		return;
	}
	addIdcode();
}
 
function addIdcode(){
	var idcode=$F('idcode');	
	var billid=$F('billid');	
	var prid=$F('prid');	
	var warehousebit=$F('warehousebit');	
   var url = "../warehouse/addSupplySaleMoveIdcodeiiAction.do?idcode="+idcode+"&billid="+billid+"&prid="+prid+"&warehousebit="+warehousebit;
   document.getElementById("result").style.display="";
   document.getElementById("result").innerHTML="数据正在处理中...";
   var pars = '';
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showResponse2}
				);
}
function showResponse2(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');	
		if ( data != undefined ){
			buildText(data.result);	
			document.submsg.window.location.reload();		
		}
}

function buildText(str) {//赋值给消息提示框
	document.getElementById("result").style.display="";
	document.getElementById("result").innerHTML=str;
	setTimeout("document.getElementById('result').style.display='none'",1500);
}

</script>

<body>
<style>
#result {font-weight:bold; position:absolute;left:753px;top:20px; text-align:center; background-color:#ff0000;color:#fff;LEFT:expression(Math.abs(Math.round((document.body.clientWidth - result.offsetWidth)/2))); TOP:expression(Math.abs(Math.round((document.body.clientHeight)/2+document.body.scrollTop-180)))}
#result h1 {font-size:12px; margin:0px; padding:0px 5px 0px 5px};
</style>
<div id="result"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">录入条码</td>
      </tr>
    </table>
	<form name="addform" method="post" action="" >
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
	
	<input type="hidden" name="billid" value="${billid}">
	<input type="hidden" name="bdid" value="${bbd.bdid}">
	<input type="hidden" name="wid" value="${wid}">
	<tr>
	  	<td width="8%"  align="right">产品名称：</td>
          <td width="19%"><input type="hidden" name="prid" value="${bbd.productid}">${bbd.productname}</td>
          <td width="6%" align="right">数量：</td>
          <td width="12%">${bbd.quantity}</td>
	      <td width="7%" align="right">仓库：</td>
	      <td width="9%"><windrp:getname key='warehouse' value='${wid}' p='d'/></td>		 
	  </tr>
	  <tr><td colspan="8"></tr>
	  <tr>
	  	<td width="8%"  align="right">条码：</td>
          <td width="19%"><input type="text" name="idcode" size="35" maxlength="50"></td>
          <td width="6%" align="right">仓位：</td>
          <td width="12%"><select name="warehousebit">
            <logic:iterate id="w" name="bitlist">
              <option value="${w.wbid}">${w.wbid}</option>
            </logic:iterate>
          </select></td>
	      <td width="7%" align="right">&nbsp;</td>
	      <td width="9%">&nbsp;</td>
	      <td width="15%" ><input type="button" value="新增" onClick="formcheck()" ${isaudit==1?"disabled":""}>&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();"></td>          
	  </tr>
	 

		
	  </table>  
	  </form>     
	  <br>
         
			<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../warehouse/listSupplySaleMoveIdcode.do?billid=${billid}&prid=${bbd.productid}&isaudit=${isaudit}" frameBorder="0" scrolling="no" onload="setIframeHeight(this)"></IFRAME>  
          
       
 </td>
</tr>
</table>
`
</body>
</html>
