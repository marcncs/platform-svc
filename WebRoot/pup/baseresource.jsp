<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title></title>

</head>
<script language="JavaScript">
this.onload =function onLoad(){
	document.getElementById("basic").src=document.getElementById("basicUrl").href;
}
</script>

<body>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" colspan="2" valign="top">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >${menusTrace }</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="130" valign="top" style="border-right: 1px solid #D2E6FF;"> 
    <table width="130" border="0" cellspacing="0" cellpadding="0" class="title-back">
		<tr>
			<td style="text-align: left;">&nbsp;&nbsp;&nbsp;资源设置</td>
		</tr>
	</table>   
    <table height="100%" border="0" cellpadding="0" cellspacing="0" >
        <tr>								
            <td width="130" vAlign="top">    
	<div style="height:100%; overflow-y:auto">
	<table  border="0" cellpadding="0" >
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="../sys/listBaseResourceAction.do?TagName=OnlineShopUrl" target="basic">网上商城地址</a></td>
	    </tr> 
      </table> 
      </div>
	</td>
        </tr>
        <tr><td height="27">&nbsp;</td></tr>
    </table>
     
      
	  </td>
    <td width="100%" valign="top" height="100%">
      <IFRAME id="basic" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="basic" src="" frameBorder=0 
      scrolling=no></IFRAME></td>
  </tr>
</table>
</body>
</html>
