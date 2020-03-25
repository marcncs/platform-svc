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
	
	function addNew(){
	window.open("../warehouse/toAddStockAlterMoveAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdStockAlterMoveAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="stockAlterMoveDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../warehouse/toReferStockMoveAction.do?SMID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function ToShipment(){
		if(checkid!=""){
			window.open("../warehouse/completeStockMoveShipmentAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CompleteReceive(){
		if(checkid!=""){
			window.open("../warehouse/completeStockMoveReceiveAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
			window.open("../warehouse/delStockAlterMoveAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 库存变价调拨记录</td>
        </tr>
      </table>
      <form name="search" method="post" action="listStockAlterMoveAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    
        <tr class="table-back">
          <td width="12%"  align="right">调出仓库：</td>
          <td width="23%"><select name="OutWarehouseID" id="OutWarehouseID">
              <option value="" selected>所有仓库</option>
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
          </select></td>
          <td align="right">调入仓库：</td>
          <td><select name="InWarehouseID" id="InWarehouseID">
              <option value="" selected>所有仓库</option>
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
          </select></td>
          <td align="right">是否提交：</td>
          <td>${isreferselect}</td>
        </tr>
      
          <tr class="table-back">
            <td align="right">是否发货：</td>
            <td>${isshipmentselect}</td>
            <td width="9%" align="right">是否签收：</td>
            <td width="23%">${iscompleteselect}
            <input type="submit" name="Submit" value="查询"></td>
            <td width="9%" align="right">&nbsp;</td>
            <td width="24%">&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td >编号</td>
            <td >调拨日期</td>
            <td >调出仓库</td>
            <td >调入仓库</td>
            <td >是否复核</td>
            <td >是否发货</td>
            <td >是否签收</td>
			 
			<td >制单人</td>
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back" onClick="CheckedObj(this,'${sa.id}');"> 
            <td ><a href="../warehouse/stockMoveDetailAction.do?ID=${sa.id}">${sa.id}</a></td>
            <td>${sa.movedatename}</td>
            <td>${sa.outwarehouseidname}</td>
            <td>${sa.inwarehouseidname}</td>
            <td>${sa.isauditname}</td>
            <td>${sa.isshipmentname}</td>
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
              <td width="60" align="center"><a href="javascript:addNew();">新增</a></td>
              <td width="60" align="center"><a href="javascript:Update();">修改</a></td>
              <!--<td width="60" align="center"><a href="javascript:Refer();">提交</a></td>-->
             <!-- <td width="60" align="center">打印</td>-->
              <td width="60" align="center"><a href="javascript:Del();">删除</a></td>
              </tr>
          </table></td>
          <td width="52%" align="right"> <presentation:pagination target="/warehouse/listStockAlterMoveAction.do"/></td>
        </tr>
      </table>      
      <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:StockMoveDetail();">库存调拨详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
