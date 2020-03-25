<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ page import="com.winsafe.hbm.util.Internation,com.winsafe.drp.dao.Outlay,java.util.*"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid=0;
var actid=0;
	function CheckedObj(obj,objid,oactid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 actid = oactid;
	
	}


	function Approve(){
		if(checkid!=""){
			window.open("../warehouse/toApprovePurchaseIncomeAction.do?ID="+checkid+"&actid="+actid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CancelApprove(){
		if(checkid!=""){
			window.open("../warehouse/cancelApprovePurchaseIncomeAction.do?PIID="+checkid+"&actid="+actid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>


<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 采购入库单 </td>
  </tr>
</table>

  <form name="search" method="post" action="../warehouse/waitPurchaseIncomeAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
   <tr class="table-back"> 
            <td width="11%"  align="right">是否审阅：</td>
            <td width="28%">${approveselect}</td>      
			<td width="24%" align="right">申请时间：</td>
            <td width="37%"><input type="text" name="BeginDate" value="" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" value="" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
    </tr>
  
</table>
</form>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="5%">编号</td>
            <td width="13%" >入货仓库</td>
            <td width="12%">采购单</td>
            <td width="31%">供应商</td>
            <td width="13%">入库日期</td>
            <td width="12%">审阅动作</td>
            <td width="10%">是否审阅</td>
          </tr>
		   <logic:iterate id="p" name="arpb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}','${p.actid}');"> 
            <td ><a href="../warehouse/purchaseIncomeDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.warehouseidname}</td>
            <td>${p.agid}</td>
            <td>${p.providename}</td>
            <td>${p.incomedate}</td>
            <td>${p.actidname}</td>
            <td>${p.approvestatusname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
          <td width="33%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a> 
                </td>
                <td width="60"><a href="javascript:CancelApprove();">取消审阅</a></td>
                <td width="60">&nbsp;</td>
              </tr>
            </table></td>
          <td width="67%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/warehouse/waitPurchaseIncomeAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

	</td>
  </tr>
</table>

</body>
</html>
