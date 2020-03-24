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
	function CompleteIncome(){
		var value = CheckboxOnlyOne();
			if( value ==0 ){
				return;
			}
			window.open("completeIncomeAction.do?ID="+value,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	}

	function CheckboxOnlyOne(){
		var onlyOneObj = document.listform.ID;
		var count = 0;
		if(onlyOneObj.length){
            for (i=0; i<onlyOneObj.length; i++) {
				if (onlyOneObj[i].checked) {
                    count += 1;
				}
			}
			if(count !=1 )
			{
        		alert("您必须且只能选择一条记录！");
				return 0;
			}
			else{
				for (i=0; i<onlyOneObj.length; i++) {
					if (onlyOneObj[i].checked) {
                    return onlyOneObj[i].value;
					}
				}
			}
		}else{
            if (!onlyOneObj.checked) {
				alert("请选择记录!");
				return 0;
			}else{
				return  onlyOneObj.value;
			}
		}
	}
	
	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.listform.checkall.checked){
			//alert(document.activeform.checkall.values);
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
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 总帐</td>
        </tr>
      </table>
      <form name="totalform" method="post" action="totalIncomePaymentAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
	  
        <tr class="table-back"> 
          <td width="13%" align="right">时间段：</td>
          <td width="42%"><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
            - 
            <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"> 
            <input type="submit" name="Submit" value="查询"></td>
          <td width="23%">&nbsp;</td>
          <td width="22%">&nbsp;</td>
        </tr>
		
      </table>
      </form>
      <table width="100%" height="143" border="0" cellpadding="0" cellspacing="1">
        <tr class="table-back"> 
          <td width="14%"  align="right">销售应收总额：</td>
          <td width="34%">${totalincome}</td>
          <td width="13%" align="right">销售已收总额：</td>
          <td width="39%">${alreadyincome}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">销售应收余款：</td>
          <td>${balanceincome}</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">采购应付总额：</td>
          <td>${totalpayment}</td>
          <td align="right">采购已付总额：</td>
          <td>${alreadypayment}</td>
        </tr>
        <tr class="table-back"> 
          <td  align="right">采购应收余款：</td>
          <td>${balancepayment}</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr class="table-back">
          <td  align="right">员工佣金总额：</td>
          <td>&nbsp;</td>
          <td align="right">相关费用总额：</td>
          <td>${totaloutlay}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">毛利：</td>
          <td>&nbsp;</td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="6%">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
