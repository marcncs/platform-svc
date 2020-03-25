<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script src="../js/validator.js"></script>
<script language=javascript>
function CheckInput(){
	if (!Validator.Validate(document.referform,2)){
		return false;
	}
	
	return true;
	}
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">修改采集条码规则</td>
        </tr>
      </table>
	  <form name="referform" method="post" action="../sys/updCodeRuleUploadAction.do" onSubmit="return CheckInput();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="18%">属性</td>
          <td width="13%" > 起始位</td>
          <td width="14%">长度</td>
		  <td width="55%">&nbsp;</td>
         </tr>
		 <c:forEach var="cu" items="${culist}">
		 <tr align="center" >
          <td width="18%" align="right"><windrp:getname key='CRUProperty' value='${cu.cruproperty}' p='f'/>：</td>
          <td width="13%"><input type="text" name="startno" id="startno" value="${cu.startno}" size="6" onKeyUp="clearNoInt(this)" dataType="Integer" msg="<windrp:getname key='CRUProperty' value='${cu.cruproperty}' p='f'/>起始位必须录入数字"></td>
          <td width="14%"><input type="text" name="lno" id="lno" value="${cu.lno}" size="6" onKeyUp="clearNoInt(this)" dataType="Integer" msg="<windrp:getname key='CRUProperty' value='${cu.cruproperty}' p='f'/>长度必须录入数字"></td>
		  <td width="55%">&nbsp;</td>
         </tr>	
		 </c:forEach>	 
      </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"> 
            <td width="28%" > <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();"></td>
          </tr>
        
		 </table>
		 </form>
    </td>
  </tr>
</table>
</body>
</html>
