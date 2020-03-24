<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
  function SelectRegie(){
	showModalDialog("toSelectRegieAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.addForm.rid.value=getCookie("regieid");
	document.addForm.regiename.value=getCookie("regiename");
}
</script>
</head>
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 添加专卖店活动</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
        <form action="addRegiePloyAction.do" method="post"  name="addForm" > 
        <table width="100%"  border="0" cellpadding="0" cellspacing="1">
           
              <tr class="table-back">
                <td width="11%"  align="right">专卖店：</td>
                <td width="36%">
                <input name="rid" type="hidden" id="rid" value="${rid}">
                <input name="regiename" type="text" id="regiename" value="${regiename}">
                <a href="javascript:SelectRegie();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
                <td width="13%" align="right" >活动类型：</td>
                <td width="40%" >${ploytypeselect}</td>
              </tr>
              <tr class="table-back">
                <td  align="right">活动日期：</td>
                <td><input name="ploydate" type="text" id="ploydate" onFocus="javascript:selectDate(this)" readonly></td>
                <td align="right">&nbsp;</td>
                <td><label></label></td>
              </tr>
              <tr class="table-back">
                <td  align="right">活动内容：</td>
                <td colspan="3"><textarea name="ploycontent" cols="150" rows="8" id="ploycontent"></textarea>
                  <label></label></td>
                </tr>
              <tr class="table-back">
                <td  align="right">&nbsp;</td>
                <td><input type="submit" name="Submit" value="确定">
                    <input type="reset" name="cancel" onClick="javascript:history.back()" value="取消"></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
           
           
        </table>
        
         </form></td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
