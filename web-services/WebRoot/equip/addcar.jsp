<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>



</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>

</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增车辆资料</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="addCarAction.do" onSubmit="return Validator.Validate(document.addusers,2);">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">
	  	  车牌：</td>
          <td width="21%"><input name="carbrand" type="text" id="carbrand" value="" dataType="Require" msg="必须入录车牌!" maxlength="12">
            <span class="STYLE1">*</span>            </td>
          <td align="right">车型：</td>
          <td>${carsortselect}</td>
          <td  align="right">价值：</td>
          <td><input name="worth" type="text" id="worth" value="0" require="false"  dataType="Double" msg="价值必须为数值型!" onKeyUp="clearNoNum(this)" maxlength="8"/>
            元</td>
	  </tr>
	
	  <tr>
	    <td align="right">购买日期：</td>
	    <td><input name="purchasedate" type="text" id="purchasedate" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly onFocus="javascript:selectDate(this)"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right"></td>
	    <td><br></td>
	  </tr>
	  </table>
</fieldset>
			
			</td>
          </tr>
          <tr>
            <td  align="center"><input type="submit" name="Submit"  value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
