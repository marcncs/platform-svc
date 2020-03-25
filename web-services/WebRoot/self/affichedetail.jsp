<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>公告详细信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td> 公告详细信息</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0" class="table-detail">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	  <td style="width: 80px;" align="right">公告标题：</td>
          <td colspan="5">${af.affichetitle}</td>
          
	  </tr>
	  <tr>
	    <td style="width: 80px;" align="right">发布日期：</td>
	    <td>${af.makedate}</td><%--
	    <td align="right">公告类型：</td>
	      <td>
	      <windrp:getname key='AfficheType' value='${af.affichetype}' p='f'/>
	      </td>
		  <td align="right">重要性：</td>
	      <td><windrp:getname key='Ponderance' value='${af.ponderance}' p='f'/></td>
	    </tr>
	  --%><tr>   
	      <td style="width: 80px;" align="right">发布机构：</td>
	      <td><windrp:getname key='organ' value='${af.makeorganid}' p='d'/></td><%--
	      <td align="right">发布部门：</td>
	      <td><windrp:getname key='dept' value='${af.makedeptid}' p='d'/></td>
	    --%><td align="right">发布人：</td>
	    <td colspan="2"><windrp:getname key='users' value='${af.makeid}' p='d'/> </td>
	    </tr>
	    
	  <tr>
	    <td style="width: 80px;" align="right">公告内容：</td>
	    <td >${content}</td>
	    <td align="right">是否发布：</td>
	    <td ><windrp:getname key='YesOrNo' value='${af.isPublish }' p='f' /></td>
	    </tr>
	  </table>
</fieldset>

    </td>
  </tr>
</table>
</body>
</html>
