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

	function Approve(){
		if(checkid>0){	
			location.href("../self/toApproveShipmentBillAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:15.5cm;dialogHeight:7.5cm;center:yes;status:no;scrolling:no;");
	document.search.ReceiveID.value=getCookie("cid");
	document.search.ReceiveName.value=getCookie("cname");
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
          <td width="772"> 出库单</td>
        </tr>
      </table>
      <form name="search" method="post" action="../self/waitApproveShipmentBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="14%"  align="right">是否审阅 ：</td>
            <td width="34%">${approveselect}</td>
            <td width="11%" align="right">发货日期：</td>
            <td width="41%"><input name="BeginDate" type="text" id="BeginDate" value="${begindate}" size="12" onFocus="javascript:selectDate(this)">
              - 
              <input name="EndDate" type="text" id="EndDate" value="${enddate}" size="12" onFocus="javascript:selectDate(this)"> 
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="50" >编号</td>
            <td width="145">收货方</td>
            <td width="193"> 发货日期</td>
            <td width="81"> 送货地址</td>
            <td width="110">是否审阅 </td>
          </tr>
          <logic:iterate id="s" name="alsb" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${s.id});"> 
            <td ><a href="../warehouse/shipmentBillDetailAction.do?ID=${s.id}">${s.id}</a></td>
            <td>${s.receiveuser}</td>
            <td>${s.shipmentdate}</td>
            <td>${s.receiveaddr}</td>
            <td>${s.approvename}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="41%"> 
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a></td>
              </tr>
            </table> </td>
          <td width="59%" align="right"> <presentation:pagination target="/self/waitApproveShipmentBillAction.do"/>          </td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
