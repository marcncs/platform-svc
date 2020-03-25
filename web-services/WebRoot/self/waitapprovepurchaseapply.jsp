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
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
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

	function Approve(){
		if(checkid>0){
			location.href("../self/toApprovePurchaseApplyAction.do?ID="+checkid);
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
          <td width="772"> 采购申请</td>
        </tr>
      </table>
       <form name="search" method="post" action="waitPurchaseApplyAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="9%"  align="right">是否审阅：</td>
            <td width="17%" >${approveselect}</td>
            <td width="10%"  align="right">申请时间段：</td>
            <td width="64%" > 
              <input type="text" name="BeginDate" value="${begindate}" onFocus="javascript:selectDate(this)">
-
<input type="text" name="EndDate" value="${enddate}" onFocus="javascript:selectDate(this)">
              <input type="submit" name="Submit" value="查询">
            </td>
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="8%" height="35">编号</td>
            <td width="10%">申请人</td>
            <td width="18%">金额</td>
            <td width="27%">申请日期</td>
            <td width="25%"> 预计到货日期 </td>
            <td width="9%">是否审阅</td>
          </tr>
          <logic:iterate id="pa" name="arpa" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${pa.id});"> 
            <td ><a href="../purchase/purchaseApplyDetailAction.do?ID=${pa.id}">${pa.id}</a></td>
            <td>${pa.seeto}</td>
            <td>${pa.totalsum}</td>
            <td>${pa.applydate}</td>
            <td>${pa.receivedate}</td>
            <td>${pa.approvestatus}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="42%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a></td>
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
              </tr>
            </table></td>
          <td width="58%" align="right"> <presentation:pagination target="/self/waitPurchaseApplyAction.do"/>          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
