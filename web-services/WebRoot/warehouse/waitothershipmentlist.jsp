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

	function Complete(){
		if(checkid!=""){
			window.open("../warehouse/completeOtherShipmentBillAction.do?OSID="+checkid,"newwindow","height=8.5cm,width=16cm,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SendGoods(){
		if(checkid>0){
			window.open("../warehouse/sendGoodsBillAction.do?id="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
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
          <td width="772"> 其它出库单</td>
        </tr>
      </table>
      <form name="search" method="post" action="waitConsignOtherShipmentBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="14%"  align="right">发货仓库：</td>
            <td width="34%" ><select name="WarehouseID" id="WarehouseID">
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
            <td width="11%"  align="right">需求日期：</td>
            <td width="41%" ><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">是否发货：</td>
            <td >${isshipmentselect}
            <input type="submit" name="Submit" value="查询"></td>
            <td  align="right">&nbsp;</td>
            <td >&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td  >编号</td>
            <td >发货仓库 </td>
            <td >出库类别</td>
            <td >出库部门</td>
            <td >需求日期</td>
            <td >是否发货 </td>
          </tr>
          <logic:iterate id="s" name="alsb" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td  ><a href="../warehouse/otherShipmentBillDetailAction.do?ID=${s.id}">${s.id}</a></td>
            <td>${s.warehouseidname}</td>
            <td>${s.shipmentsortname}</td>
            <td>${s.shipmentdeptname}</td>
            <td>${s.requiredate}</td>
            <td>${s.isshipmentname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table width="172"  border="0" cellpadding="0" cellspacing="0">
<tr align="center">
  <td width="82"><a href="javascript:Complete();">确认发货</a></td>
                <td width="90"><a href="javascript:SendGoods();">打印送货单</a></td>
</tr>
            </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/warehouse/waitConsignOtherShipmentBillAction.do"/>          </td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
