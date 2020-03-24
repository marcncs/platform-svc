<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function formChk(){
if ( !Validator.Validate(document.updateform,2) ){
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
          <td width="772"> 修改积分规则</td>
        </tr>
      </table>
	  <form name="updateform" method="post" action="../sys/updIntegralRuleAction.do" onSubmit="return formChk()">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="18%" height="20" align="right"><input name="id" type="hidden" id="id" value="${wf.id}">
方式名称：</td>
          <td width="20%">
          <input name="rkeyid" type="hidden" value="${wf.rkey}">
          <input name="rkey" type="text" id="rkey" value="<windrp:getname key='RKey' value='${wf.rkey}' p='d'/>" maxlength="50">
          </td>        
          <td width="29%" align="right">积分比例：</td>
	      <td width="33%"><input name="irate" type="text" id="irate" dataType="Double" msg="积分比例必须录入数值类型!"  value="<windrp:format value='${wf.irate}'/>" size="10"  maxlength="8" >
	      <span class="style1">*</span>
	      </td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td width="28%" height="20"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
	  </table></form>
    </td>
  </tr>
</table>
</body>
</html>
