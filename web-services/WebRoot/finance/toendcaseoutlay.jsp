<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	function CheckedObj(obj,objid,objname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	}
	
	function formChk(){
		var waitbacksum = parseFloat($F('waitbacksum'));
		var totaloutlay = parseFloat($F('totaloutlay'));
		var thisresist = parseFloat($F('thisresist'));
		var factpay = parseFloat($F('factpay'));
		if ( thisresist>waitbacksum){
			alert("本次冲借不能大于应还款金额！");
			$('thisresist').select();
			return false;
		}
		if ( factpay>(totaloutlay-thisresist)){
			alert("实付费用不能大于总费用减去本次冲借金额！");
			$('factpay').select();
			return false;
		}
		showloading();
		return true;
	}


</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 结款</td>
        </tr>
      </table>      
        <FORM METHOD="POST" name="listform" ACTION="endcaseOutlayAction.do" onSubmit="return formChk()">
		<input type="hidden" name="id" value="${id}">
        <input type="hidden" name="waitbacksum" id="waitbacksum" value="${waitbacksum}">
        <input type="hidden" name="totaloutlay" id="totaloutlay" value="${totaloutlay}">
      <fieldset align="center"> 		  
		 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
				<td width="17%"  align="right">付款来源：</td>
				  <td width="32%"><select name="fundsrc">
					 <logic:iterate id="d" name="cblist">
					  <option value="${d.id}">${d.cbname}</option>
					</logic:iterate>
				  </select>				  </td>
				  <td width="22%" align="right">应还款金额：</td>
				  <td width="29%"><windrp:format value='${waitbacksum}'/></td>
			  </tr>			
			  <tr>
				<td width="17%"  align="right">本次冲借：</td>
				  <td width="32%"><input type="text" name="thisresist" id="thisresist" value="0" onKeyPress="KeyPress(this)" maxlength="10"></td>
				  <td width="22%" align="right">实付费用：</td>
				  <td width="29%"><input type="text" name="factpay" id="factpay" onKeyPress="KeyPress(this)" maxlength="10"></td>
			  </tr>			  
			  </table>
		</fieldset>
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">			 
			   <tr>
				<td align="center"><input type="submit" value="提交">&nbsp;&nbsp;<input type="button" value="取消" onClick="window.close();"></td>
				 
			  </tr>
			  </table>
        </form>
     
      
    </td>
  </tr>
</table>
</body>
</html>
