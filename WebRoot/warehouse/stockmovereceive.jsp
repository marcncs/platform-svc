<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function frmCheck(){
	showloading();
	return true;
}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">转仓签收</td>
        </tr>
      </table>      
        <FORM METHOD="POST" name="listform" ACTION="../warehouse/completeStockMoveReceiveAction.do" onSubmit="return frmCheck()">
          <fieldset align="center"> 		  
		 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  			
			  <tr>
				<td width="9%"  align="right"><input name="smid" type="hidden" id="smid" value="${smid}">
			    选择入货仓库：</td>
			    <td width="26%"><windrp:warehouse name="inwarehouseid" value="${inwarehouseid}"/></td>
			  </tr>			  
			  </table>
		</fieldset>
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">			 
			   <tr>
				<td align="center"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
			     <input type="button" name="cancel" value="取消" onClick="window.close();"></td>
				 
			  </tr>
			  </table>
        </form>
     
      
    </td>
  </tr>
</table>
</body>
</html>
