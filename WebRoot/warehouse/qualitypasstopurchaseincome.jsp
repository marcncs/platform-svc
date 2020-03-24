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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}

	function Affirm(){
		if(checkid!=""){
		window.open("../warehouse/toQualityPassToPurchaseIncomeAction.do?QPID="+checkid,"newwindow","height=200,width=400,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择质检合格单</td>
        </tr>
      </table>
      <form name="search" method="post" action="fromPurchaseBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="13%"  align="right">制单日期：</td>
            <td width="87%"><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"> 
<input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="7%" >质检单编号</td>
            <td width="27%">供应商名称</td>
            <td width="16%">采购类型</td>
            <td width="11%">采购部门</td>
            <td width="11%">质检部门</td>
            <td width="11%">总金额</td>
            <td width="11%">制单日期</td>
          </tr>
          <logic:iterate id="p" name="alpb" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td ><a href="../purchase/qualityPassDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.pidname}</td>
            <td>${p.purchasesortname}</td>
            <td>${p.purchasedeptname}</td>
            <td>${p.purchasedeptname}</td>
            <td>${p.totalsum}</td>
            <td>${p.makedate}</td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定导入</a></td>
                <td width="60"><a href="javascript:history.back();">取消</a></td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/purchase/purchaseBillToPurchaseIncomeAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
