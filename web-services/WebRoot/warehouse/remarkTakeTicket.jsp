<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<title>WINDRP-分销系统</title>
<script type="text/javascript">
function ChkValue(){

	if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
	}

	showloading();
	return true;
}
</script>
</head>

<body>
<form name="validateProvide" method="post" action="../warehouse/remarkTakeTicketAction.do" onSubmit="return ChkValue();">
 
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
      <table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>编辑备注</td>
        </tr>
      </table>
      <table width="100%" height="102" border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td width="30%">出库单号:</td> 
          <td align="left">${tt.ttid}
          <input id="id" name="id" type="hidden" value="${tt.id}"></td>
        </tr>
        <tr> 
        <td width="30%">产品编号:</td> 
          <td align="left">${tt.productid}
        </tr>
        <tr> 
          <td width="30%">备注:</td> 
          <td>
          <textarea name="remark" cols="100" rows="4" id="remark" dataType="Limit" max="512"  msg="备注必须在512个字之内" require="false">${tt.remark}</textarea>          
 
          </td>
        </tr>
       
        <tr > 
            <td colspan=2 align="center"><input type="submit" name="Submit" value="提交">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
