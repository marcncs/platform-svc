<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript">
function ToAdd(){
	document.all.submsg.src="../sales/toAddPeddleOrderAction.do";
	}

	function ToList(){
	document.all.submsg.src="../sales/listPeddleOrderAction.do";
	}
	
</script>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="925"> 零售单</td>
    <td width="292" align="right" valign="bottom"><table width="120"  border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="back-gray"><a href="javascript:ToAdd();">新增</a></td>
        <td align="center" class="back-gray"><a href="javascript:ToList();">查询</a></td>
      </tr>
    </table></td>
  </tr>
</table>

    </td>
  </tr>
</table>
		<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sales/toAddPeddleOrderAction.do" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
