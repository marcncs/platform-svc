<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/calendarawake.js"></SCRIPT>
		<script type="text/javascript">
	newCalendar();
	ShowLayer(27, 0);

</script>
	</head>

	<body>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" colspan="2" valign="top">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >我的办公桌>>日程</td>
      </tr>
    </table></td>
  </tr>
  <tr>
   <td width="130" valign="top" style="border-right: 1px solid #D2E6FF;"> 
    <table width="205" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table> 
    </td>
    <td width="100%" valign="top">
      <IFRAME id="calend" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="calend" src="../self/listAllCalendarAction.do" frameBorder=0 
      scrolling=no></IFRAME></td>
   </tr>
</table>
		
	</body>

</html>
