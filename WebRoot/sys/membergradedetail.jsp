<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	

<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 会员级别详情</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%" height="20" align="right">会员级别编号：</td>
          <td width="20%">${w.id}</td>
          <td width="14%" align="right">会员级别名称：</td>
          <td width="19%">${w.gradename}</td>
	      <td width="9%" align="right">享受价格：</td>
	      <td width="25%"><windrp:getname key='PricePolicy' value='${w.policyid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td height="20" align="right">积分比例：</td>
	    <td><windrp:format value='${w.integralrate}'/></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>

	<fieldset align="center"> <legend>
      <table width="80" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>许可到用户</td>
        </tr>
      </table>
	  </legend>
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="title-top">
    <td width="11%" >序号</td>
    <td width="89%">用户名称</td>
  </tr>
  <logic:iterate id="r" name="rvls" >
  <tr class="table-back-colorbar">
          <td height="20">${r.userid}</td>
    <td>${r.useridname}</td>
  </tr>
  </logic:iterate>
</table>
</fieldset>

		
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
