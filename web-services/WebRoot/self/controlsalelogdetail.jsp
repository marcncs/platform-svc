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
		//var receivedate = document.listform.receivedate;
		//var iscompletecontrol = document.listform.IsCompleteControl;
		if(listform.receivedate.value ==null || listform.receivedate.value==""){
			alert("预计到货日期不能为空！");
			document.listform.receivedate.focus();
			return;
		}

		if(listform.IsCompleteControl.value =="all"){
			alert("请确认流程是否转化完成！");
			document.listform.IsCompleteControl.focus();
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
			listform.submit();
		}else{
			alert("请选择产品并设定好数量和价格!");
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
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(i=0;i<pidleng;i++){
	
				if (!document.listform.pid[i].checked)
					if(listform.elements[i].name != "checkall"){
					document.listform.pid[i].checked=true;
					document.listform.unitprice[i].disabled=false;
					document.listform.controlquantity[i].disabled=false;
					document.listform.unitid[i].disabled=false;
					}
			}
		}else{
				if (!document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=true;
					document.listform.unitprice.disabled=false;
					document.listform.controlquantity.disabled=false;
					document.listform.unitid.disabled=false;
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
					document.listform.unitprice[i].disabled=true;
					document.listform.controlquantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
					}
			}
		}else{
				if (document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=false;
					document.listform.unitprice.disabled=true;
					document.listform.controlquantity.disabled=true;
					document.listform.unitid.disabled=true;
					}
		}
	}
	
	function onlycheck(){
	var pidleng=document.listform.pid.length;

		if(pidleng>1){
			for(var i=0;i<pidleng;i++){
				if(document.listform.pid[i].checked){
					document.listform.unitprice[i].disabled=false;
					document.listform.controlquantity[i].disabled=false;
					document.listform.unitid[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					document.listform.unitprice[i].disabled=true;
					document.listform.controlquantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					document.listform.unitprice.disabled=false;
					document.listform.controlquantity.disabled=false;
					document.listform.unitid.disabled=false;
				}
				if(!document.listform.pid.checked){
					document.listform.unitprice.disabled=true;
					document.listform.controlquantity.disabled=true;
					document.listform.unitid.disabled=true;
				}
		}
	}
	
/*	
	function SelectProvide(){
	showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.listform.provideid.value=getCookie("pid");
	document.listform.providename.value=getCookie("pname");
	}
	*/

</script>
</head>

<body>
<form name="listform" method="post" action="flowToPurchasePlanAction.do">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 销售订单产品列表</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back">
            <td width="12%"  align="right"><input name="slid" type="hidden" id="slid" value="${slid}">
            预计到货日期：</td>
            <td width="32%" ><input type="text" name="receivedate" id="receivedate" onFocus="selectDate(this);"></td>
            <td width="13%" align="right">下次不要出现此单：</td>
            <td width="43%">${iscompletecontrol}</td>
          </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="23%" >产品</td>
            <td width="10%">单位</td>
            <td width="12%">单价</td>
            <td width="15%">销售订单数量</td>
            <td width="11%">已生成计划数量</td>
            <td width="27%">计划采购数量</td>
          </tr>
          <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${a.productid}" onClick="onlycheck();"></td> 
            <td >${a.productname}</td>
            <td><input name="unitid" type="hidden" value="${a.unitid}" disabled>${a.unitname}</td>
            <td><input name="unitprice" type="text" disabled id="unitprice" value="0" size="8"></td>
            <td>${a.quantity}</td>
            <td>${a.controlquantity}</td>
            <td><input name="controlquantity" type="text" id="controlquantity" size="8" value="${a.quantity - a.controlquantity}" disabled></td>
          </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="90"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="90"><a href="javascript:Affirm();">生成采购计划</a></td> 
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
