<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NPS</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 拜访提醒</td>
      </tr>
    </table>
      <table width="100%" height="60" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="1%" height="60">&nbsp;</td>
          <td width="28%"><table width="99%" height="60" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>今天需联系：(<a href="../sales/listCustomerByVisitaAction.do?P=DN"><span class="text-red">${todaynCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本周需联系：(<a href="../sales/listCustomerByVisitaAction.do?P=WN"><span class="text-red">${weeknCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本月需联系：(<a href="../sales/listCustomerByVisitaAction.do?P=MN"><span class="text-red">${monthnCont}</span></a>)</td>
            </tr>

          </table></td>
          <td width="28%"><table width="99%" height="60" border="0" cellpadding="0" cellspacing="0">

            <tr>
              <td>今天已联系：(<a href="../sales/listCustomerByVisitaAction.do?P=DA"><span class="text-red">${todayaCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本周已联系：<a href="../sales/listCustomerByVisitaAction.do?P=WA">(<span class="text-red">${weekaCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本月已联系：(<a href="../sales/listCustomerByVisitaAction.do?P=MA"><span class="text-red">${monthaCont}</span></a>)</td>
            </tr>

          </table></td>
          <td width="43%"><table width="99%" height="60" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>今天登记：(<a href="../sales/listCustomerByVisitaAction.do?P=DR"><span class="text-red">${todayrCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本周登记：(<a href="../sales/listCustomerByVisitaAction.do?P=WR"><span class="text-red">${weekrCont}</span></a>)</td>
            </tr>
            <tr>
              <td>本月登记：(<a href="../sales/listCustomerByVisitaAction.do?P=MR"><span class="text-red">${monthrCont}</span></a>)</td>
            </tr>

          </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
