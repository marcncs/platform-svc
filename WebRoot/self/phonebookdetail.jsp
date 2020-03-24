<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
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
          <td> 通讯记录详情</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0"  class="table-detail">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <form name="search" method="post" action="addTaskAction.do" onSubmit="return Chk();">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  
	  <tr>
	  	<td width="9%"  align="right">姓名：</td>
          <td width="26%">${pb.name}</td>
          <td width="9%" align="right">性别：</td>
          <td width="22%"><windrp:getname key='Sex' value='${pb.sex}' p='f'/></td>
	      <td width="9%" align="right">电话：</td>
	      <td width="25%">${pb.phone}</td>
	  </tr>
	  <tr>
	    <td  align="right">手机：</td>
	    <td>${pb.mobile}</td>
	    <td align="right">Email：</td>
	    <td>${pb.email}</td>
	    <td align="right">QQ：</td>
	    <td>${pb.qq}<a  href=tencent://message/?uin=${pb.qq}&Site=xrun.vicp.net:8001/ss&Menu=yes><img border="0" src=http://wpa.qq.com/pa?p=1:${pb.qq}:5 alt="点这里发信息"></a></td>
	    </tr>
	  <tr>
	    <td  align="right">MSN：</td>
	    <td><a href="msnim:chat?contact=${pb.msn}">${pb.msn}</a></td>
	    <td align="right">生日：</td>
	    <td>${pb.birthday}</td>
	    <td align="right">地址：</td>
	    <td>${pb.addr}</td>
	    </tr>
	  <tr>
	    <td  align="right">分类：</td>
	    <td>${sort}</td>
	    <td align="right">备注：</td>
	    <td colspan="3">${pb.remark}</td>
	    </tr>
		
	  </table>
	  </form>
</fieldset>
	  
    </td>
  </tr>
</table>
</body>
</html>
