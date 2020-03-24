<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function Affirm(){
		//if (listform.provideid.value==null || listform.provideid.value==""){
		//	alert("请选择供应商!");
		//	document.listform.provideid.focus();
		//	return false;
		//	}
			
		if(listform.receivedate.value==null || listform.receivedate.value==""){
			alert("请输入预计到货日期!")
			//document.listform.receivedate.focus();
			return false;
		}
			
		return ture;
		//listform.submit();
	}
	
</script>
</head>

<body>
<form name="listform" method="post" action="makePurchaseBillAction.do" onSubmit="return Affirm();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 采购询价产品列表</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right"><input name="piid" type="hidden" id="piid" value="${piid}">
供应商：</td>
          <td width="22%"><input name="pid" type="hidden" id="pid" value="${pif.pid}">
            <input name="providename" type="text" id="providename" value="${pif.providename}" size="40"></td>
          <td width="12%" align="right">供应商联系人：</td>
          <td width="24%"><input name="plinkman" type="text" id="plinkman" value="${pif.plinkman}"></td>
	      <td width="9%" align="right">采购类型：</td>
	      <td width="23%">${purchasesortname}</td>
	  </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td><select name="purchasedept" id="purchasedept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}">${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">采购人员：</td>
	    <td><select name="purchaseid" id="purchaseid">
          <logic:iterate id="u" name="auls">
            <option value="${u.userid}">${u.realname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">结算方式：</td>
	    <td>${paymodename}</td>
	    </tr>
	  <tr>
	    <td  align="right">预计到货日期：</td>
	    <td><input type="text" name="receivedate" id="receivedate" onFocus="selectDate(this);"></td>
	    <td align="right">收货地址：</td>
	    <td colspan="3"><input name="receiveaddr" type="text" id="receiveaddr" size="50">
	      <input name="remark" type="hidden" id="remark" value="询价转采购单"></td>
	    </tr>
	  </table>
	</fieldset>

      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">

            <td width="12%">产品编号</td>
            <td width="20%" >产品名称</td>
            <td width="16%">规格</td>
            <td width="8%">单位</td>
            <td width="14%">单价</td>
            <td width="15%">数量</td>
			<td width="15%">金额</td>
          </tr>
          <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input name="productid" type="text" id="productid" value="${a.productid}" size="12" readonly></td>
            <td ><input name="productname" type="text" id="productname" value="${a.productname}" size="35" readonly></td>
            <td><input name="specmode" type="text" id="specmode" value="${a.specmode}" size="35" readonly></td>
            <td><input name="unitid" type="hidden" value="${a.unitid}" size="12">
              <input name="unitidname" type="text" id="unitidname" value="${a.unitname}" size="12" readonly></td>
			<td><input name="unitprice" type="text" id="unitprice" size="8" value="${a.unitprice}" readonly></td>
			<td><input name="quantity" type="text" id="quantity" size="8" value="${a.quantity}" readonly></td>
			<td><input name="subsum" type="text" id="subsum" size="12" value="${a.subsum}" readonly></td>
          </tr>
          </logic:iterate> 
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="7%" >&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="64%" align="right">&nbsp;</td>
          <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${totalsum}" size="12" maxlength="10"></td>
        </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="148"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="80"><a href="javascript:Affirm();"><input name="提交" type="submit" value="生成采购单">
                </a></td>
                <td width="80"><input name="返回" type="button" onClick="history.back();" value="返回"></td>
              </tr>
            </table></td>
          <td width="70%" align="right">
          </td>
        </tr>
      </table>
      
    </td>
  </tr>

</table>
  </form>
</body>
</html>
