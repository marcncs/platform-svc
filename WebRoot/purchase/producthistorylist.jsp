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
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 产品历史信息 </td>
        </tr>
      </table>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="6%"  >编号</td>
            <td width="20%" >标准采购价</td>
            <td width="18%" >标准销售价</td>
            <td width="13%" >标准库存</td>
            <td width="10%" >是否可用</td>
            <td width="15%" >变更人</td>
            <td width="18%" >变更日期</td>
          </tr>
          <logic:iterate id="p" name="alpl" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td >${p.id}</td>
            <td>${p.standardpurchase}</td>
            <td>${p.standardsale}</td>
            <td>${p.standardstock}</td>
            <td>${p.useflagname}</td>
            <td>${p.updateidname}</td>
            <td>${p.lastdate}</td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="25%">&nbsp;</td>
          <td width="75%" align="right"> <presentation:pagination target="/purchase/listProductHistoryAction.do"/>          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
