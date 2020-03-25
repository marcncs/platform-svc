<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售区域分析</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr align="center"  class="title-top-lock">
            <td width="22%">客户名称</td> 
            <td width="22%" >省份</td>
            <td width="15%" >城市</td>
            <td width="24%" >区域</td>
            <td width="17%" >数量</td>
          </tr>
          <logic:iterate id="s" name="als" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.cid}');">
            <td>${s.cname}</td> 
            <td >${s.province}</td>
            <td>${s.city}</td>
            <td>${s.areas}</td>
            <td align="right"><windrp:format p="###,##0.00" value='${s.quantity}' /></td>
            </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="37%">&nbsp;</td>
          <td width="63%" align="right"></td>
        </tr>
      </table>      
    </td>
  </tr>
</table>
</body>
</html>
