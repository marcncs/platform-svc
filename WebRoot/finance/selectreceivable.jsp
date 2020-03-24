<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	var arrid=new Array();
	var arrpaymentmode=new Array();
	var arrpaymentmodename = new Array();
	var arrbillno=new Array();
	var arrreceivablesum=new Array();
	
	function Affirm(){
		
		
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
		for(var i=0;i<document.listform.pid.length;i++){
			if(document.listform.pid[i].checked){
				arrid[k]=document.listform.pid[i].value;//k保证只有选中的才放到数组里
				arrpaymentmode[k]=document.listform.paymentmode[i].value;
				arrpaymentmodename[k]=document.listform.paymentmodename[i].value;
				arrbillno[k]=document.listform.billno[i].value;
				arrreceivablesum[k]=document.listform.receivablesum[i].value;
				k++;
				flag=true;//只要选中一个就设为true
			}
		}
		}else{
				arrid[0]=document.listform.pid.value;//k保证只有选中的才放到数组里
				arrpaymentmode[0]=document.listform.paymentmode.value;
				arrpaymentmodename[0]=document.listform.paymentmodename.value;
				arrbillno[0]=document.listform.billno.value;
				arrreceivablesum[0]=document.listform.receivablesum.value;
				flag=true;//只要选中一个就设为true
		}
		if(flag){
			
			var p={rid:arrid.slice(0),paymentmode:arrpaymentmode.slice(0),paymentmodename:arrpaymentmodename.slice(0),billno:arrbillno.slice(0),receivablesum:arrreceivablesum.slice(0)};
			window.returnValue=p;

			window.close();
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	

</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择应收款结算</td>
	    
        </tr>
      </table>
       <form name="search" method="post" action="../sales/selectProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
		  	<td width="14%"  align="right">结算方式：</td>
            <td width="18%">${paymentmode}
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
	  	    <td width="3%" ><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="13%" >结算单编号</td>
            <td width="26%">结算方式</td>
            <td width="28%">相关单据号</td>
            <td width="13%">应收款金额</td>
          </tr>
          <logic:iterate id="p" name="alpl" > 
		  <tr class="table-back" > 
		  	<td> <input type="checkbox" name="pid" value="${p.id}" ></td>
            <td >${p.id}</td>
            <td><input type="hidden" name="paymentmode" value="${p.paymentmode}"><input type="hidden" name="paymentmodename" value="${p.paymentmodename}">${p.paymentmodename}</td>
            <td><input type="hidden" name="billno" value="${p.billno}">${p.billno}</td>
            <td><input type="hidden" name="receivablesum" value="${p.receivablesum}">
              ${p.receivablesum}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60"><a href="javascript:window.close();">取消</a></td>
              </tr>
            </table></td>
          <td width="70%"> <presentation:pagination target="/sales/selectProductAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
