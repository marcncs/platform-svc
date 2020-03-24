<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<script language="JavaScript">
function formcheck(){
	var count = document.getElementById("count").value;
	if(!isNaN(count)){
	}else{
		alert("请输入数字,谢谢!");
		return false;	
	}
	return true;
}

function changePrint(){
	var select = document.getElementById("selectprint").value;
	if(select ==1){
		document.getElementById("printbox").style["display"]="block";
		document.getElementById("compleprintbox").style["display"]="none";
	}else{
		document.getElementById("printbox").style["display"]="none";
		document.getElementById("compleprintbox").style["display"]="block";
	}
}


</script>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 打印装箱单</td>
            </tr>
        </table></td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
      <tr>
      	<td>打印选项：
      		<select onchange="changePrint();" id="selectprint">
      			<option value="1">打印装箱单</option>
      			<option value="2">补打装箱单</option>
      		</select>
      	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
      <tr>
        <td>
        <div id="printbox" style="display: block;">
			<form name="refer" method="post" action="../warehouse/printPackingBillAction.do" onSubmit="return formcheck()">
				<input type="hidden" id="type" value="2" name="type"/>
				<input type="hidden" id="ttid" value="${ttid}" name="ttid"/>
			<fieldset align="center"> <legend>
		      <table width="60" border="0" cellpadding="0" cellspacing="0">
		        <tr>
		          <td>打印装箱单</td>
		        </tr>
		      </table>
			  </legend>
			  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
			  	<td width="9%"  align="right"><input name="productid" type="hidden" id="productid" value="${pid}"></td>
		          <td width="21%">打印次数：
		            <input name="count" type="text" size="40" id="count" ></td>
		          <td width="13%" align="right">&nbsp;</td>
		          <td width="23%">&nbsp;</td>
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
		</div>
		
		 <div id="compleprintbox" style="display: none;">
			<form name="refer" method="post" action="../warehouse/printPackingBillAction.do" onSubmit="return formcheck()">
				<input type="hidden" id="type" value="3" name="type"/>
				<input type="hidden" id="ttid" value="${ttid}" name="ttid"/>
			<fieldset align="center"> <legend>
		      <table width="60" border="0" cellpadding="0" cellspacing="0">
		        <tr>
		          <td>补打装箱单</td>
		        </tr>
		      </table>
			  </legend>
			  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
			  	<td width="9%"  align="right"><input name="productid" type="hidden" id="productid" value="${pid}"></td>
		          <td width="21%">当前打印数-总数：
		            <input name="index" type="text" size="5" id="index" >-<input name="count" type="text" size="5" id="count" >
		          </td>
		          <td width="13%" align="right">&nbsp;</td>
		          <td width="23%">&nbsp;</td>
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
		</div>
		</td>
      </tr>
      
      
    </table></td>
  </tr>
</table>

</body>
</html>
