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
	function SelectCustomer(){
		var os = document.search.objectsort.value;
		if(os==0){
		var s=showModalDialog("../finance/toSelectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:auto;");
		document.search.ROID.value=s.oid;
		document.search.payername.value=s.oname;
		}
		if(os==1){
		var s=showModalDialog("../finance/toSelectCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:auto;");
		document.search.ROID.value=s.cid;
		document.search.payername.value=s.cname;
		}
		if(os==2){
		var s=showModalDialog("../finance/toSelectProvideAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:auto;");
		document.search.ROID.value=s.pid;
		document.search.payername.value=s.pname;
		}

	}

</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">报表分析>>账务>>收入明细</td>
        </tr>
      </table>
       <form name="search" method="post" action="revenueWasteAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
		 <tr class="table-back">
            <td width="10%"  align="right">对象类型：</td>
            <td width="24%"><select name="objectsort" onChange="Clear();">
            <option value="1" selected>客户</option>
            <option value="2">供应商</option>
            <option value="0">机构</option>
          </select></td>
            <td width="9%" align="right">付款方：</td>
            <td width="29%"><input name="ROID" type="hidden" id="ROID">
                <input name="payername" type="text" size="35" value="${payername}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="8%" align="right">制单人：</td>
            <td width="20%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="24%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" >${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">制单日期：</td>
            <td width="29%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询" ></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="20%">&nbsp;</td>
          </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
           <tr align="center" class="title-top-lock"> 
            <td width="6%" >制单日期</td>
			<td width="13%">单据编号</td>
			<td width="18%">付款方</td>                       
            <td width="10%">制单人</td>
            <td width="10%">结算方式</td>
            <td width="21%">摘要</td>
            <td width="11%">应收款结算金额</td>           
			<td width="11%">收款金额</td> 
          </tr>
       
      </table>
       </form>
      
  </td>
  </tr>
</table>

</html>
