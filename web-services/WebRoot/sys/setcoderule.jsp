<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="ws" uri="ws" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script language="JavaScript">
this.onload =function onLoad(){
	document.getElementById("phonebook").src=document.getElementById("ruleUrl").href;
}
</script>

<title></title>

</head>

<body style="overflow-y:auto">

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" colspan="2" valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">${menusTrace }</td>
      </tr>
    </table></td>
  </tr>
  <tr>
   <td width="130" valign="top" style="border-right: 1px solid #D2E6FF;"> 
    <table width="130" border="0" cellspacing="0" cellpadding="0"
							class="title-back">
							<tr>
								<td style="text-align: left;">
									&nbsp;&nbsp;&nbsp;条码规则设置
								</td>
								<td>&nbsp;
									
								</td>
							</tr>
						</table> 
<table width="100%"  border="0" cellpadding="0" cellspacing="2"> 
	<ws:hasAuth operationName="/sys/listCodeUnitAction.do">       
        <tr>
          <td >&nbsp;</td>
          <td><a id="ruleUrl" href="listCodeUnitAction.do" target="basic">标识位规则</a></td>
        </tr>
     </ws:hasAuth>
     <ws:hasAuth operationName="/sys/listCodeRuleAction.do">  
		<tr>
          <td >&nbsp;</td>
          <td><a id="ruleUrl" href="listCodeRuleAction.do" target="basic">条形码规则</a></td>
        </tr>
     </ws:hasAuth>
     <ws:hasAuth operationName="/sys/detailCodeRuleUploadAction.do">
		 <tr>
          <td >&nbsp;</td>
          <td><a id="ruleUrl" href="detailCodeRuleUploadAction.do" target="basic">采集条码规则</a></td>
        </tr>
     </ws:hasAuth>	
        <tr> 
          <td >&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td width="100%" valign="top">
      <IFRAME id="phonebook" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="basic" src="" frameBorder=0 
      scrolling=no></IFRAME></td>
  </tr>
</table>
</body>
</html>
