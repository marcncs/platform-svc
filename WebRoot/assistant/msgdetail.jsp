<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
function Audit(soid){
	showloading();
	popWin2("../assistant/auditMsgAction.do?id="+soid);
}
	
function CancelAudit(soid){
	showloading();
	popWin2("../assistant/cancelAuditMsgAction.do?id="+soid);
}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 详情</td>
		<td width="275" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
             <td width="60" align="center"><c:choose><c:when test="${rf.isaudit==0}"><a href="javascript:Audit('${rf.id}');">复核</a></c:when><c:otherwise>
<a href="javascript:CancelAudit('${rf.id}')">取消复核</a></c:otherwise>
</c:choose></td>  
			 </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="../sales/addMemberAction.do" >
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
		   
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="15%"  align="right">短信类型：</td>
          <td colspan="3"><windrp:getname key='MsgSort' value='${rf.msgsort}' p='f'/></td>
          </tr>
	  <tr>
	  	<td width="15%"  align="right">手机号码：</td>
          <td colspan="3" style="width: 500px; word-wrap: break-word">${rf.mobileno}</td>          
	  </tr>
	  <tr>
	  	<td width="15%"  align="right">短信内容：</td>
          <td colspan="3">${rf.msgcontent}</td>
          </tr>
	  </table>
</fieldset>


<fieldset align="center"> <legend>
      <table width="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>其它信息</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	    <td width="15%"  align="right">制单机构：</td>
	    <td width="21%"><windrp:getname key='organ' value='${rf.makeorganid}' p='d'/></td>
	    <td width="10%" align="right">制单部门：</td>
	    <td width="21%"><windrp:getname key='dept' value='${rf.makedeptid}' p='d'/></td>
	    <td width="10%" align="right">制单人：</td>
	    <td width="23%"><windrp:getname key='users' value='${rf.makeid}' p='d'/> </td>
	  </tr>
	   <tr>
	    <td width="15%"  align="right">是否复核：</td>
	    <td width="21%"><windrp:getname key='YesOrNo' value='${rf.isaudit}' p='f'/></td>
	    <td width="10%" align="right">复核人：</td>
	    <td width="21%"><windrp:getname key='users' value='${rf.auditid}' p='d'/></td>
	    <td width="10%" align="right">复核日期：</td>
	    <td width="23%">${rf.auditdate}</td>
	  </tr>
	  <tr>
	    <td  align="right">制单日期：</td>
	    <td>${rf.makedate}</td>
	    <td align="right">是否处理：</td>
	    <td colspan="3"><windrp:getname key='YesOrNo' value='${rf.isdeal}' p='f'/></td>
	    </tr>
	  </table>
</fieldset>

	
	
	
	

    </form>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
