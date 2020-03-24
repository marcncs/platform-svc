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

	function Update(){
		if(checkid>0){
		location.href("../warehouse/toUpdShipmentBillAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Refer(){
		if(checkid>0){
			window.open("../warehouse/toReferShipmentBillAction.do?ShipmentID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772"> 库存调整记录</td>
        </tr>
      </table>
      <form name="search" method="post" action="listStockAdjustAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="11%"  align="right">仓库：</td>
            <td width="22%" ><select name="WarehouseID" id="WarehouseID">
<option value="" selected>所有仓库</option>
                <logic:iterate id="w" name="alw" > 
                <option value="${w.id}">${w.warehousename}</option>
                </logic:iterate> 
            </select></td>
            <td width="10%"  align="right">调整日期：</td>
            <td width="20%" ><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
            <td width="10%" align="right">是否提交：</td>
            <td width="27%">${isreferselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否审阅：</td>
            <td >${approvestatusselect}</td>
            <td  align="right">是否完成：</td>
            <td >${iscompleteselect}
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="94" >编号</td>
            <td width="274">调整日期</td>
            <td width="296">仓库</td>
            <td width="157">是否提交</td>
            <td width="142">是否审阅</td>
            <td width="123">是否完成</td>
            <td width="147">制单人</td>
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${sa.id});"> 
            <td ><a href="stockAdjustDetailAction.do?ID=${sa.id}">${sa.id}</a></td>
            <td>${sa.adjustdate}</td>
            <td>${sa.warehouseidname}</td>
            <td>${sa.isrefername}</td>
            <td>${sa.approvestatusname}</td>
            <td>${sa.iscompletename}</td>
            <td>${sa.makeidname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="toAddStockAdjustAction.do">新增</a></td>
              <td width="60" align="center">修改</td>
              <td width="60" align="center">提交</td>
              <td width="60" align="center">删除</td>
              <td width="60" align="center">确认完成</td>
            </tr>
          </table></td>
          <td width="52%" align="right"> <presentation:pagination target="/warehouse/listStockAdjustAction.do"/> 
          </td>
        </tr>
      </table>      </td>
  </tr>
</table>
</body>
</html>
