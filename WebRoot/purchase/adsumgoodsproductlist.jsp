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
	function CreateQuality(){
/*		if (listform.provideid.value==null || listform.provideid.value==""){
			alert("请选择供应商!");
			document.listform.provideid.focus();
			return false;
			}
			
		if(listform.receivedate.value==null || listform.receivedate.value==""){
			alert("请输入预计到货日期!")
			//document.listform.receivedate.focus();
			return;
		}*/
			
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
			listform.action="makeQualityPassAction.do";
			listform.submit();
		}else{
			alert("请选择产品并设定好数量和价格!");
		}
	}
	
	function CreateWithdraw(){
		
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
			listform.action="makePurchaseWithdrawAction.do";
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
					//document.listform.unitprice[i].disabled=false;
					document.listform.operatorquantity[i].disabled=false;
					document.listform.unitid[i].disabled=false;
					}
			}
		}else{
				if (!document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=true;
					//document.listform.unitprice.disabled=false;
					document.listform.operatorquantity.disabled=false;
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
					//document.listform.unitprice[i].disabled=true;
					document.listform.operatorquantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
					}
			}
		}else{
				if (document.listform.pid.checked)
					if(listform.elements.name != "checkall"){
					document.listform.pid.checked=false;
					//document.listform.unitprice.disabled=true;
					document.listform.operatorquantity.disabled=true;
					document.listform.unitid.disabled=true;
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
					document.listform.unitid[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					//document.listform.unitprice[i].disabled=true;
					document.listform.operatorquantity[i].disabled=true;
					document.listform.unitid[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					//document.listform.unitprice.disabled=false;
					document.listform.operatorquantity.disabled=false;
					document.listform.unitid.disabled=false;
				}
				if(!document.listform.pid.checked){
					//document.listform.unitprice.disabled=true;
					document.listform.operatorquantity.disabled=true;
					document.listform.unitid.disabled=true;
				}
		}
	}
	
	
	function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.listform.provideid.value=p.pid;
	document.listform.providename.value=p.pname;
	}
	

</script>
</head>

<body>
<form name="listform" method="post" action="">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 到货通知产品列表</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back">
            <td width="12%"  align="right"><input name="agid" type="hidden" id="agid" value="${agid}">
            质检部门：</td>
            <td width="32%"><select name="qualitydept" id="qualitydept">
              <logic:iterate id="d" name="aldept">
                <option value="${d.id}">${d.deptname}</option>
              </logic:iterate>
            </select></td>
            <td width="13%" align="right">转化后是否隐藏此单：</td>
            <td width="43%">${iscomplete}</td>
          </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="27%" >产品</td>
            <td width="12%">规格</td>
            <td width="8%">单位</td>
           <!-- <td width="7%">参考单价</td>-->
			<td width="6%">到货数量</td>
            <td width="9%">质检完成数量</td>
            <td width="9%">本次完成数量</td>
          </tr>
          <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${a.productid}" onClick="onlycheck();"></td> 
            <td ><input name="productname" type="hidden" id="productname" value="${a.productname}">
              ${a.productname}</td>
            <td><input name="specmode" type="hidden" id="specmode" value="${a.specmode}">
              ${a.specmode}</td>
            <td><input name="unitid" type="hidden" value="${a.unitid}" disabled>${a.unitname}
              <input name="unitprice" type="hidden" id="unitprice" value="${a.unitprice}"></td>
			<td>${a.quantity}</td>
            <td>${a.changequantity}</td>
            <td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.quantity - a.changequantity}" disabled></td>
          </tr>
          </logic:iterate> 
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table width="240"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="93"><a href="javascript:CreateQuality();">生成质检合格单</a></td>
                <td width="88"><a href="javascript:CreateWithdraw();">生成采购退货</a></td>
                <td width="59"><a href="javascript:history.back();">返回</a></td>
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
