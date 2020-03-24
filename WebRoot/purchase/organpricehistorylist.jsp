<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="javascript">
	function ToSet(){
		window.open("../purchase/toSetOrganPriceAction.do","","height=650,width=600,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	

	
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 机构价格历史记录</td>
  </tr>
</table>

        <form action="listOrganPriceHistoryAction.do" method="post" name="validateProduct" onSubmit="return formChk();">
          <fieldset align="center">
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
		  <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="5%">序号</td>
              <td width="14%">机构名称</td>
              <td width="20%" >产品名称</td>
              <td width="8%">单位</td>
              <td width="14%">价格政策</td>
              <td width="10%">机构价格</td>
			  <td width="13%">修改人</td>
			  <td width="22%">修改日期</td>
			  
            </tr>
            <logic:iterate id="p" name="alpl" >
              <tr class="table-back">
                <td>${p.id}</td>
                <td title="点击查看详情">${p.organidname}</td>
                <td  title="点击查看详情">${p.productidname}</td>
                <td>${p.unitidname}</td>
                <td>${p.policyidname}</td>
                <td>${p.unitprice}</td>
				<td>${p.useridname}</td>
				<td>${p.modifydate}</td>
              </tr>
            </logic:iterate>
          </table>
           <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="18%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
              <!--  <td width="60"><a href="javascript:addNew(${pid});">新增</a></td> -->
              </tr>
            </table></td>
          <td width="82%"> <table  border="0" cellpadding="0" cellspacing="0" >
              <tr> 
                <td width="50%" align="right"> <presentation:pagination target="/purchase/listOrganPriceHistoryAction.do"/> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
          </fieldset>
          
          
        </form>

    </td>
  </tr>
</table>
</body>
</html>
