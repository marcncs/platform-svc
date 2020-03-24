<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT type=text/javascript src="../js/validate.js"></SCRIPT>
<script language=javascript>
	function CheckInput(){	
		var flowname =referform.approveid.value;
		var approveorder=referform.approveorder.value;
		if(flowname.trim()==""){
			alert("流程名不能为空!");
			return false;
		}
		if(approveorder.trim()==""){
			alert("审阅顺序不能为空!");
			return false;
		}
		if( !IsInteger(approveorder) ){
			alert("审阅顺序必须是数字!");
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
 <form name="referform" method="post" action="../sys/updApproveFlowDetailAction.do" onSubmit="return CheckInput();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增流程详情</td>
        </tr>
      </table>
      <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr > 
            <td width="11%" height="20" align="right"><input name="id" type="hidden" id="id" value="${afdf.id}">
            <input name="afid" type="hidden" id="afid" value="${afdf.afid}">
            审阅者              ：</td>
            <td width="19%"><select name="approveid" id="approveid">
              <logic:iterate id="u" name="als">
                <option value="${u.userid}" <c:if test="${u.userid==afdf.approveid}"> selected </c:if>>${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="10%" align="right">动作：</td>
            <td width="24%">${afdf.actidname}</td>
			<td width="10%" align="right">审阅顺序：</td>
            <td width="26%"><input type="text" name="approveorder" value="${afdf.approveorder}" size="8" onKeyPress="KeyPress(this)" maxlength="5"><span class="style1">*</span></td>
          </tr>
         
 
	  </table>
	  </fieldset>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
                </div></td>
              </tr>
        </table>
    </td>
  </tr>
</table>
       </form>
</body>
</html>
