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
function Affirm(){
/*		if (listform.provideid.value==null || listform.provideid.value==""){
			alert("请选择供应商!");
			document.listform.provideid.focus();
			return false;
			}
			*/
		if(listform.consignmentdate.value==null || listform.consignmentdate.value==""){
			alert("请输入交货日期!")
			//document.listform.receivedate.focus();
			return;
		}
			
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
			for(var i=0;i<document.listform.pid.length;i++){
				if(document.listform.pid[i].checked){
					k++;
					flag=true;//只要选中一个就设为true
				}
			}
		}else{
				if(document.listform.pid.checked){
					//k++;
					flag=true;//只要选中一个就设为true
				}
		}
		
		if(flag){
			listform.action="makeSaleOrderAction.do";
			listform.submit();
		}else{
			alert("请选择产品并设定好数量和价格!");
		}
	}

/*
	function Affirm(){
		//if (listform.provideid.value==null || listform.provideid.value==""){
		//	alert("请选择供应商!");
		//	document.listform.provideid.focus();
		//	return false;
		//	}
			
		if(listform.incomedate.value==null || listform.incomedate.value==""){
			alert("请输入入库日期!")
			//document.listform.receivedate.focus();
			return false;
		}
			
		return ture;
		//listform.submit();
	}
	*/
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(i=0;i<pidleng;i++){
	
				if (!document.listform.pid[i].checked)
					if(listform.elements[i].name != "checkall"){
					document.listform.pid[i].checked=true;
					document.listform.operatorquantity[i].disabled=false;
					}
			}
		}else{
				if (!document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=true;
					document.listform.operatorquantity.disabled=false;
					}
		}
	}

	function uncheckAll(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(i=0;i<pidleng;i++){
				if (document.listform.pid[i].checked)
					if(listform.elements[i].name != "checkall"){
					document.listform.pid[i].checked=false;
					document.listform.operatorquantity[i].disabled=true;
					}
			}
		}else{
				if (document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=false;
					document.listform.operatorquantity.disabled=true;
					}
		}
	}
	
	function onlycheck(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(var i=0;i<pidleng;i++){
				if(document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=false;
					document.listform.operatorquantity[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=true;
					document.listform.operatorquantity[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					//document.listform.unitprice.disabled=false;
					document.listform.operatorquantity.disabled=false;
				}
				if(!document.listform.pid.checked){
					//document.listform.unitprice.disabled=true;
					document.listform.operatorquantity.disabled=true;
				}
		}
	}
	
	function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("operatorquantity").value);
		document.listform.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("operatorquantity")(m).value);
			document.listform.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.listform.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.listform.item("subsum")(i).value);
		}
	}
	document.listform.totalsum.value=totalsum;
}

	
</script>
</head>

<body>
<form name="listform" method="post" onSubmit="return Affirm();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 借出单产品列表</td>
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
	  <tr><input name="loid" type="hidden" id="loid" value="${sof.id}" >
	  	<td width="11%"  align="right"><input name="uid" type="hidden" id="uid" value="${sof.uid}" >
	  	  客户名称：</td>
          <td width="23%"><input name="uname" type="text" id="uname" value="${sof.uname}" readonly></td>
          <td width="10%" align="right">收货人：</td>
          <td width="22%"><input name="receiveman" type="text" id="receiveman" value="${sof.receiveman}" readonly>
            </td>
	      <td width="9%" align="right">联系电话：</td>
	      <td width="25%"><input name="tel" type="text" id="tel" value="${sof.tel}" readonly></td>
	  </tr>
	  <tr>
	    <td  align="right">借出部门：</td>
	    <td><select name="saledept" id="saledept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}" ${d.id==sof.saledept?"selected":""}>${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">借出人员：</td>
	    <td><select name="saleid" id="saleid">
          <logic:iterate id="d" name="userlist">
            <option value="${d.userid}" ${d.userid==sof.saleid?"selected":""}>${d.realname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">发运方式：</td>
	    <td>${sof.transportmodename}</td>
	    <td align="right">货运部：</td>
	    <td>${sof.transitname}</td>
	    <td align="right">交货日期：</td>
	    <td><input name="consignmentdate" type="text" id="consignmentdate" readonly onFocus="javascript:selectDate(this)" value="${sof.consignmentdate}">
          <span class="STYLE1">*</span></td>
	    </tr>
	  <tr>
	    <td align="right">收货地址：</td>
	    <td ><input name="transportaddr" type="text" id="transportaddr" size="50" value="${sof.transportaddr}"></td>
	    <td  align="right">&nbsp;</td>
	    <td  colspan="3">&nbsp;</td>
	    </tr>
	  
	 
	  </table>
	</fieldset>

      <table width="100%" border="0" cellpadding="0" cellspacing="1" id="dbtable">
          <tr align="center" class="title-top">
            <td width="6%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="6%">产品编号</td>
            <td width="17%" >产品名称</td>
            <td width="17%">规格</td>
            <td width="3%">单位</td>
			<td width="6%">出货仓库</td>
			<td width="5%">批次</td>
            <td width="5%">单价</td>			
            <td width="8%">数量</td>
			<td width="12%">完成数量</td>
			<td width="9%">本次完成数量</td>
			<td width="6%">金额</td>
          </tr>
          <c:set var="count" value="2"/>
		  <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${a.productid}" onClick="onlycheck();"></td>
            <td><input name="productid" type="text" id="productid" value="${a.productid}" size="12" readonly></td>
            <td ><input name="productname" type="text" id="productname" value="${a.productname}" size="35" readonly></td>
            <td><input name="specmode" type="text" id="specmode" value="${a.specmode}" size="35" readonly></td>
            <td><input name="unitid" type="hidden" value="${a.unitid}">
              <input name="unitidname" type="text" id="unitidname" value="${a.unitidname}" size="6" readonly></td>
			 <td><input name="warehouseid" type="hidden" value="${a.warehouseid}" size="12">
              <input name="warehouseidname" type="text" id="warehouseidname" value="${a.warehouseidname}" size="12" readonly></td>
			<td><input name="batch" type="text" id="batch" size="8" value="${a.batch}" readonly></td>
			<td><input name="unitprice" type="text" id="unitprice" size="4" value="${a.unitprice}" readonly></td>
			<td>${a.quantity}<input type="hidden" name="quantity" value="${a.quantity}"><input type="hidden" name="cost" value="${a.cost}"></td>
			<td>${a.backquantity}</td>
			<td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.quantity - a.backquantity}"   onchange="SubTotal(${count});TotalSum();" onFocus="SubTotal(${count});TotalSum();" disabled></td>
			<td><input name="subsum" type="text" id="subsum" size="10" value="${a.subsum}" readonly></td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="7%" >&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="64%" align="right">&nbsp;</td>
          <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${totalsum}" size="12" maxlength="10" readonly></td>
        </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="148"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="80"><input name="提交" type="button" value="生成零售单" onClick="Affirm();">
                </td>
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
